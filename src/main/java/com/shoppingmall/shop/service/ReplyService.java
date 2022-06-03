package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Entity.Reply;
import com.shoppingmall.shop.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);
    List<ReplyDTO> getList(Long qno);
    void modify(ReplyDTO replyDTO);
    void remove(Long rno);

    default Reply dtoToEntity(ReplyDTO replyDTO){

        Member member = Member.builder().email(replyDTO.getReplyerEmail()).build();

        Question question = Question.builder().qno(replyDTO.getQno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .member(member)
                .question(question)
                .build();

        return reply;
    }

    default ReplyDTO entityToDTO(Reply reply){

        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyerEmail(reply.getMember().getEmail())
                .replyerName(reply.getMember().getName())
                .qno(reply.getQuestion().getQno())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;
    }

}
