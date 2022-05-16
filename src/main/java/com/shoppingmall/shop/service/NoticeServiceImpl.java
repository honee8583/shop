package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.Repository.NoticeRepository;
import com.shoppingmall.shop.dto.NoticeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public void register(NoticeDTO noticeDTO) {

        log.info("Notice Service -> register");

        Notice notice = DtoToEntity(noticeDTO);

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

    @Override
    public List<NoticeDTO> getList(Pageable pageable) {

        log.info("Notice Service -> getList");

        Page<Notice> list = noticeRepository.findAll(pageable);

        List<NoticeDTO> noticeList = list.stream().map(notice -> {
            return entityToDto(notice);
        }).collect(Collectors.toList());

        return noticeList;
    }


    @Override
    public void modify(NoticeDTO noticeDTO) {

        Optional<Notice> result = noticeRepository.findById(noticeDTO.getNno());

        Notice notice = result.get();

        notice.changeTitle(noticeDTO.getTitle());
        notice.changeContent(noticeDTO.getContent());

        noticeRepository.save(notice);
    }

    @Override
    public void remove(Long nno) {
        noticeRepository.deleteById(nno);
    }
}
