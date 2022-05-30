package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.dto.NoticeDTO;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        PageRequestDTO requestDTO = new PageRequestDTO();

        Pageable pageable = requestDTO.getPageable(Sort.by("nno").descending());

        PageResultDTO<NoticeDTO, Notice> resultDTO = noticeService.getList(requestDTO);

        List<NoticeDTO> result = resultDTO.getDtoList();

        for(NoticeDTO dto : result){
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

    @Test
    @DisplayName("NoticeService:검색쿼리 구현한 getList")
    public void testSearchGetList(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("w")
                .keyword("honee8583")
                .build();

        PageResultDTO<NoticeDTO, Notice> result = noticeService.getList(requestDTO);

        System.out.println("PREV : " + result.isPrev());
        System.out.println("NEXT: " + result.isNext());
        System.out.println("TOTAL : " + result.getTotalPage());

        System.out.println("--------------------------------------");
        for(NoticeDTO noticeDTO : result.getDtoList()){
            System.out.println(noticeDTO);
        }

        System.out.println("=======================================");
        result.getPageList().forEach(i -> System.out.println(i));
    }
}
