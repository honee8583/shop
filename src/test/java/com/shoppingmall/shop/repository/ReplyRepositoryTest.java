package com.shoppingmall.shop.repository;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Entity.Reply;
import com.shoppingmall.shop.Repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    @DisplayName("댓글 더미데이터 추가")
    public void insertDummies(){
        IntStream.rangeClosed(1, 50).forEach(i -> {

            long qno = (long)(Math.random() * 48) + 1;

            Question question = Question.builder().qno(qno).build();

            Member member = Member.builder().email("user" + i + "@gmail.com").build();

            Reply reply = Reply.builder()
                    .text("reply....."+ i)
                    .member(member)
                    .question(question)
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    @DisplayName("해당 게시글의 댓글 조회")
    public void testGetReplies(){
        Question question = Question.builder().qno(48L).build();

        List<Reply> replyList = replyRepository.getRepliesByQuestionOrderByRno(question);

        replyList.forEach(reply -> System.out.println(reply));
    }
}
