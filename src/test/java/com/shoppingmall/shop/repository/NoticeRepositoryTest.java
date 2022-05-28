package com.shoppingmall.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.Entity.QNotice;
import com.shoppingmall.shop.Repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void insertDummies(){
        Member member = Member.builder().email("honee8583@naver.com").build();

        IntStream.rangeClosed(1, 20).forEach(i -> {
            Notice notice = Notice.builder()
                    .member(member)
                    .title("Notice" + i)
                    .content("Notice Content" + i)
                    .count(0)
                    .build();

            noticeRepository.save(notice);
        });
    }

    //공지사항 등록
    @Test
    public void insertNotice(){
        Notice notice = Notice.builder()
                .title("test title")
                .content("test content")
                .member(Member.builder().email("user1@gmail.com").build())
                .count(0)
                .build();

        noticeRepository.save(notice);
    }

    //id로 공지사항 검색
    @Transactional
    @Test
    public void viewNotice(){
        Optional<Notice> result = noticeRepository.findById(1L);

        Notice notice = result.get();

        System.out.println(notice);
        System.out.println(notice.getMember());
    }

    /*@Test
    public void testReadWithMember(){
        Object result = noticeRepository.getNoticeWithMember(1L);

        Object[] arr = (Object[])result;

        System.out.println("------------------");
        System.out.println(Arrays.toString(arr));
    }*/

    //공지사항 페이징 검색
    @Transactional
    @Test
    public void viewNoticeList(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nno").descending());

        Page<Notice> noticeList = noticeRepository.findAll(pageable);

        for(Notice notice : noticeList){
            System.out.println(notice);
        }
    }

    //공지사항 수정
    @Test
    public void updateNotice(){
        Optional<Notice> result = noticeRepository.findById(1L);

        Notice notice = result.get();

        notice.changeTitle("modified Title");
        notice.changeContent("modified Content");

        noticeRepository.save(notice);

        System.out.println(notice);
    }

    //공지사항 삭제
    @Test
    public void deleteNotice(){
        noticeRepository.deleteById(20L);
    }

    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nno").descending());

        QNotice qNotice = QNotice.notice;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //BooleanExpression 클래스는 Predicate 클래스를 구현
        BooleanExpression expression = qNotice.title.contains("2");

        booleanBuilder.and(expression);

        Page<Notice> result = noticeRepository.findAll(booleanBuilder, pageable);

        result.stream().forEach(notice -> {
            System.out.println(notice);
        });
    }

    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nno").descending());

        QNotice qNotice = QNotice.notice;

        String keyword = "2";

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression exTitle = qNotice.title.contains(keyword);    //title 포함여부 조건

        BooleanExpression exContent = qNotice.content.contains(keyword);    //content 포함여부 조건

        BooleanExpression exAll = exTitle.or(exContent); //title 검색부분과 content 검색부분 합체한 최종 조건 or -> or 문으로 생성 = 제목 또는 내용에 포함될시 검색

        booleanBuilder.and(exAll);  //where 문제 추가(추가할때는 and()메소드 사용)

        booleanBuilder.and(qNotice.nno.gt(0L));

        Page<Notice> result = noticeRepository.findAll(booleanBuilder, pageable);

        result.stream().forEach(notice -> {
            System.out.println(notice);
        });
    }
}
