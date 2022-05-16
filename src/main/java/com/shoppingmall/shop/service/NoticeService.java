package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.dto.NoticeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {

    void register(NoticeDTO noticeDTO);  //공지사항 등록
    List<NoticeDTO> getList(Pageable pageable);   //공지사항 목록 조회
    NoticeDTO get(Long nno);    //특정 공지사항 조회
    void modify(NoticeDTO noticeDTO); //공지사항 수정
    void remove(Long nno);            //공지사항 삭제

    default Notice DtoToEntity(NoticeDTO noticeDTO){
        Notice notice = Notice.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .member(Member.builder().email(noticeDTO.getWriterEmail()).build())
                .count(0)
                .build();

        return notice;
    }

    default NoticeDTO entityToDto(Notice notice){
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writerEmail(notice.getMember().getEmail())
                .writerName(notice.getMember().getName())
                .count(notice.getCount())
                .regDate(notice.getRegDate())
                .modDate(notice.getModDate())
                .build();

        return noticeDTO;
    }
}
