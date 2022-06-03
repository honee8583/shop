package com.shoppingmall.shop.Repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.shoppingmall.shop.Entity.QMember;
import com.shoppingmall.shop.Entity.QQuestion;
import com.shoppingmall.shop.Entity.QReply;
import com.shoppingmall.shop.Entity.Question;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchQuestionRepositoryImpl extends QuerydslRepositorySupport implements SearchQuestionRepository{

    public SearchQuestionRepositoryImpl() {
        super(Question.class);
    }


    @Override
    public Question search1() {

        log.info("search1..........");

        QQuestion question = QQuestion.question;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Question> jpqlQuery = from(question); //from절
        jpqlQuery.leftJoin(member).on(question.member.eq(member));  //join절
        jpqlQuery.leftJoin(reply).on(reply.question.eq(question));  //join절

        JPQLQuery<Tuple> tuple = jpqlQuery.select(question, member.email, reply.count());

        tuple.groupBy(question);

        log.info("----------------------------");
        log.info(tuple);
        log.info("----------------------------");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage...." + type + ", " + keyword + ", ");

        QQuestion question = QQuestion.question;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Question> jpqlQuery = from(question); //from절
        jpqlQuery.leftJoin(member).on(question.member.eq(member));  //join절
        jpqlQuery.leftJoin(reply).on(reply.question.eq(question));  //join절

        JPQLQuery<Tuple> tuple = jpqlQuery.select(question, member, reply.count()); //select절

        BooleanBuilder booleanBuilder = new BooleanBuilder();   //where절에 들어갈 BooleanBuilder생성
        BooleanExpression expression = question.qno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null){
            String[] typeArr = type.split("");  //tcw를 한글자씩 나눠서 배열에 저장
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr){
                switch(t){
                    case "t":
                        conditionBuilder.or(question.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(question.member.name.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(question.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);    //where 절에 booleanBuilder 연결

        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Question.class, "question");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(question);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();

        log.info("COUNT : " + count);

        return new PageImpl<Object[]>(  //pageable과 long값을 이용하는 생성자
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }


}
