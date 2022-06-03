package com.shoppingmall.shop.controller;

import com.shoppingmall.shop.dto.NoticeDTO;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public void list(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        model.addAttribute("result",  noticeService.getList(requestDTO));

    }

    @GetMapping({"/read", "/modify"})
    public void read(Long nno, Model model, @ModelAttribute("requestDTO") PageRequestDTO requestDTO){
        log.info("NoticeController -> read nno : " + nno);

        NoticeDTO noticeDTO = noticeService.get(nno);

        log.info(noticeDTO);

        model.addAttribute("dto", noticeDTO);
    }

    @PostMapping("/modify")
    public String modify(NoticeDTO noticeDTO,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){
        log.info("NoticeController -> modify nno : " + noticeDTO.getNno());

        noticeService.modify(noticeDTO);

        log.info(noticeDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("nno", noticeDTO.getNno());

        return "redirect:/notice/read";
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(NoticeDTO noticeDTO, RedirectAttributes redirectAttributes){

        log.info("register..");

        log.info("noticeDTO : " + noticeDTO);

        noticeService.register(noticeDTO);

        redirectAttributes.addFlashAttribute("msg", noticeDTO.getNno());

        return "redirect:/notice/list";
    }

    @PostMapping("/remove")
    public String remove(Long nno, RedirectAttributes redirectAttributes){
        log.info("remove....");

        noticeService.remove(nno);

        //redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/notice/list";
    }
}
