package com.shoppingmall.shop.controller;

import com.shoppingmall.shop.Entity.Notice;
import com.shoppingmall.shop.dto.NoticeDTO;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import com.shoppingmall.shop.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@Log4j2
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public void list(PageRequestDTO requestDTO, Model model){
        //Pageable pageable = PageRequest.of(0, 10, Sort.by("nno").descending());

        PageResultDTO<NoticeDTO, Notice> result = noticeService.getList(requestDTO);

        model.addAttribute("result", result);
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long nno, Model model){
        log.info("NoticeController -> read nno : " + nno);

        NoticeDTO noticeDTO = noticeService.get(nno);

        log.info(noticeDTO);

        model.addAttribute("dto", noticeDTO);
    }

    @PostMapping("/modify")
    public String modify(NoticeDTO noticeDTO, RedirectAttributes redirectAttributes){
        log.info("NoticeController -> modify nno : " + noticeDTO.getNno());

        noticeService.modify(noticeDTO);

        log.info(noticeDTO);

        redirectAttributes.addAttribute("nno", noticeDTO.getNno());

        return "redirect:/notice/read";
    }

}
