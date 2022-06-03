package com.shoppingmall.shop.controller;

import com.shoppingmall.shop.dto.ReplyDTO;
import com.shoppingmall.shop.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/question/{qno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByQuestion(@PathVariable("qno") Long qno){
        log.info("qno : " + qno);

        return new ResponseEntity<>(replyService.getList(qno), HttpStatus.OK);
    }
}
