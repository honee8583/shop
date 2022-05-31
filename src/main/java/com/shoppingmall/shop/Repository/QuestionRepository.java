package com.shoppingmall.shop.Repository;

import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuerydslPredicateExecutor<Question> {

}
