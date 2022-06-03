package com.shoppingmall.shop.repository;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
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
    @DisplayName("문의사항 목록화면에 필요한 데이터 추출 테스트")
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("qno").descending());

        Page<Object[]> result = questionRepository.getQuestionWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[])row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    @DisplayName("문의사항 조회화면에 필요한 데이터 추출 테스트")
    public void testReadWithReplyCount(){
        Object result = questionRepository.getQuestionByQno(47L);

        Object[] arr = (Object[])result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    @DisplayName("repository 확장 테스트")
    public void testSearch1(){
        questionRepository.search1();
    }

    @Test
    @DisplayName("문의사항 검색구문 테스트")
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("qno").descending().and(Sort.by("title").ascending()));

        Page<Object[]> result = questionRepository.searchPage("t", "1", pageable);

        result.get().forEach(arr -> System.out.println(Arrays.toString(arr)));
    }
}
