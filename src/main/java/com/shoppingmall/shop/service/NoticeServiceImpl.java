package com.shoppingmall.shop.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.Entity.QNotice;
import com.shoppingmall.shop.Repository.NoticeRepository;
import com.shoppingmall.shop.dto.NoticeDTO;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public void register(NoticeDTO noticeDTO) {

        log.info("Notice Service -> register");

        Notice notice = dtoToEntity(noticeDTO);

        noticeRepository.save(notice);

    }

    @Override
    public NoticeDTO get(Long nno) {

        Optional<Notice> result = noticeRepository.findById(nno);

        Notice notice = result.get();

        //notice.addCount();  //조회수 증가

        //noticeRepository.save(notice);

        NoticeDTO noticeDTO = entityToDto(notice);

        return noticeDTO;
    }

    @Transactional
    @Override
    public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {

        log.info("Notice Service -> getList");

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Pageable pageable = requestDTO.getPageable(Sort.by("nno").descending());

        Page<Notice> result = noticeRepository.findAll(booleanBuilder, pageable);

        Function<Notice, NoticeDTO> fn = notice -> entityToDto(notice);

        return new PageResultDTO<>(result, fn);
    }


    @Override
    public void modify(NoticeDTO noticeDTO) {

        Optional<Notice> result = noticeRepository.findById(noticeDTO.getNno());

        if(result.isPresent()) {
            Notice notice = result.get();

            notice.changeTitle(noticeDTO.getTitle());
            notice.changeContent(noticeDTO.getContent());

            noticeRepository.save(notice);
        }
    }

    @Override
    public void remove(Long nno) {
        noticeRepository.deleteById(nno);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        QNotice qNotice = QNotice.notice;

        String keyword = requestDTO.getKeyword();
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression expression = qNotice.nno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qNotice.title.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qNotice.member.name.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qNotice.content.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
