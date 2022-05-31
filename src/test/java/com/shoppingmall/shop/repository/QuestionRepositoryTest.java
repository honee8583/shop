package com.shoppingmall.shop.repository;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("문의사항 더미데이터 추가")
    public void insertDummies(){
        Member member = Member.builder().email("user95@gmail.com").build();

        IntStream.rangeClosed(1, 50).forEach(i -> {
            Question question = Question.builder()
                    .member(member)
                    .title("Question" + i)
                    .content("Question Content" + i)
                    .count(0)
                    .build();

            questionRepository.save(question);
        });
    }

    @Test
    @DisplayName("문의사항 등록 테스트")
    public void testInsert(){

    }
}
