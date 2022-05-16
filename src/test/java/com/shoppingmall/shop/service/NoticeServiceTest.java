package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.dto.NoticeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    @DisplayName("NoticeService:공지사항등록")
    public void testRegister(){
        NoticeDTO dto = NoticeDTO.builder()
                .title("NoticeServiceTestTitle")
                .content("NoticeServiceTestContent")
                .writerEmail("honee8583@naver.com")
                .build();

        noticeService.register(dto);
    }

    @Transactional
    @Test
    @DisplayName("NoticeService:공지사항페이징")
    public void testGetList(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nno").descending());

        List<NoticeDTO> dtoList = noticeService.getList(pageable);

        for(NoticeDTO dto : dtoList){
            System.out.println(dto);
        }
    }

    @Test
    @DisplayName("NoticeService:공지사항수정")
    public void testModify(){
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .nno(21L)
                .title("modifiedTitle")
                .content("modifiedContent")
                .build();

        noticeService.modify(noticeDTO);
    }

    @Test
    @DisplayName("NoticeService:공지사항삭제")
    public void deleteModify(){
        Long nno = 21L;

        noticeService.remove(nno);
    }
}
