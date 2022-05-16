package com.shoppingmall.shop.controller;

import com.shoppingmall.shop.dto.NoticeDTO;
import com.shoppingmall.shop.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@Log4j2
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public void list(Model model){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nno").descending());

        List<NoticeDTO> dtoList = noticeService.getList(pageable);

        model.addAttribute("result", dtoList);
    }

    @GetMapping("/read")
    public void read(Long nno, Model model){
        log.info("NoticeController -> read nno : " + nno);

        NoticeDTO noticeDTO = noticeService.get(nno);

        log.info(noticeDTO);

        model.addAttribute("dto", noticeDTO);
    }
}
