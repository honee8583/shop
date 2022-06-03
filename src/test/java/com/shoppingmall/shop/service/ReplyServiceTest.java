package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.dto.ReplyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    @DisplayName("댓글 목록 조회 테스트")
    public void testGetList(){

        List<ReplyDTO> dtoList = replyService.getList(48L);

        dtoList.forEach(dto -> System.out.println(dto));
    }
}
