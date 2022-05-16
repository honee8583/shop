package com.shoppingmall.shop.controller;

import com.shoppingmall.shop.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll(){
        log.info("exAll.......");

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin.......");

    }

    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal AuthMemberDTO memberDTO){ //컨트롤러에서 사용자 정보 확인
        log.info("exMember.......");

        log.info("--------------------");
        log.info(memberDTO);
    }
}
