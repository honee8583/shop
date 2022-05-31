package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Repository.QuestionRepository;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import com.shoppingmall.shop.dto.QuestionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("문의사항 목록(검색) 테스트")
    public void testGetList(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("0")
                .build();

        PageResultDTO<QuestionDTO, Question> result = questionService.getList(requestDTO);

        for(QuestionDTO dto : result.getDtoList()){
            System.out.println(dto);
        }
    }

    @Test
    @DisplayName("문의사항 조회 테스트")
    public void testGet(){
        Long qno = 50L;

        QuestionDTO dto = questionService.get(qno);

        System.out.println(dto);
    }

    @Test
    @DisplayName("문의사항 등록 테스트")
    public void testRegister(){
        QuestionDTO dto = QuestionDTO.builder()
                .title("Question51")
                .content("Question content51")
                .writerEmail("user95@gmail.com")
                .build();

        questionService.register(dto);
    }

    @Test
    @DisplayName("문의사항 수정 테스트")
    public void testModify(){
        QuestionDTO dto = QuestionDTO.builder()
                .qno(51L)
                .title("Question51(modified)")
                .content("Question Content51(modified)")
                .writerEmail("user95@gmail.com")
                .build();

        questionService.modify(dto);
    }

    @Test
    @DisplayName("문의사항 삭제 테스트")
    public void testRemove(){
        Long qno = 51L;

        questionService.remove(qno);
    }
}
