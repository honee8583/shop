package com.shoppingmall.shop.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shoppingmall.shop.Entity.QQuestion;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Repository.QuestionRepository;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import com.shoppingmall.shop.dto.QuestionDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    @Override
    public PageResultDTO<QuestionDTO, Question> getList(PageRequestDTO requestDTO) {
        log.info("QuestionService -> getList");

        BooleanBuilder builder = getSearch(requestDTO);

        Pageable pageable = requestDTO.getPageable(Sort.by("qno").descending());

        Page<Question> result = questionRepository.findAll(builder, pageable);

        Function<Question, QuestionDTO> fn = (question -> entityToDTO(question));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public QuestionDTO get(Long qno) {

        log.info("QuestionService -> get");
        log.info("qno : " + qno);

        Optional<Question> result = questionRepository.findById(qno);

        Question question = result.get();

        QuestionDTO questionDTO = entityToDTO(question);

        return questionDTO;
    }

    @Override
    public void register(QuestionDTO dto) {

        log.info("QuestionService -> register");

        Question question = dtoToEntity(dto);

        questionRepository.save(question);
    }

    @Override
    public void modify(QuestionDTO dto) {

        log.info("QuestionService -> modify");

        Optional<Question> result = questionRepository.findById(dto.getQno());

        if(result.isPresent()){
            Question question = result.get();

            question.changeTitle(dto.getTitle());
            question.changeContent(dto.getContent());

            questionRepository.save(question);
        }
    }

    @Override
    public void remove(Long qno) {

        log.info("QuestionService -> remove");

        questionRepository.deleteById(qno);
    }

    public BooleanBuilder getSearch(PageRequestDTO requestDTO){

        QQuestion qQuestion = QQuestion.question;

        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression expression = qQuestion.qno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qQuestion.title.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qQuestion.member.email.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qQuestion.content.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
