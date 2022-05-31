package com.shoppingmall.shop.controller;

import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.QuestionDTO;
import com.shoppingmall.shop.service.QuestionService;
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
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, QuestionDTO questionDTO){

        log.info("QuestionController -> register dto : " + questionDTO);

        questionService.register(questionDTO);

        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public void list(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        model.addAttribute("result", questionService.getList(requestDTO));

    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long qno, Model model){

        log.info("QuestionController -> read qno : " + qno);

        model.addAttribute("dto", questionService.get(qno));
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, QuestionDTO dto, RedirectAttributes redirectAttributes){

        log.info("QuestionController -> modify questionDTO : " + dto);

        questionService.modify(dto);

        redirectAttributes.addAttribute("qno", dto.getQno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/question/read";
    }

    @PostMapping("/remove")
    public String remove(Long qno, RedirectAttributes redirectAttributes){

        log.info("QuestionController -> remove qno : " + qno);

        questionService.remove(qno);

        redirectAttributes.addFlashAttribute("msg", qno);

        return "redirect:/question/list";
    }
}
