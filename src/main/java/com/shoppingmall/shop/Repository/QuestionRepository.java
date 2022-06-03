package com.shoppingmall.shop.Repository;

import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Repository.search.SearchQuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, SearchQuestionRepository {

    @Query("SELECT q, m, count(r) FROM Question q LEFT JOIN q.member m LEFT JOIN Reply r ON r.question = q GROUP BY q")
    Page<Object[]> getQuestionWithReplyCount(Pageable pageable);

    @Query("SELECT q, m, count(r) FROM Question q LEFT JOIN q.member m LEFT JOIN Reply r ON r.question = q WHERE q.qno = :qno")
    Object getQuestionByQno(@Param("qno") Long qno);
}
