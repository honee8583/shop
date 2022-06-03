package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import com.shoppingmall.shop.dto.QuestionDTO;

public interface QuestionService {

    PageResultDTO<QuestionDTO, Object[]> getList(PageRequestDTO requestDTO);
    QuestionDTO get(Long qno);
    Long register(QuestionDTO dto);
    void modify(QuestionDTO dto);
    void removeWithReplies(Long qno);

    default Question dtoToEntity(QuestionDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Question question = Question.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .count(0)
                .build();

        return question;
    }

    default QuestionDTO entityToDTO(Question question, Member member, Long replyCount){
        QuestionDTO dto = QuestionDTO.builder()
                .qno(question.getQno())
                .title(question.getTitle())
                .content(question.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .count(question.getCount())
                .replyCount(replyCount.intValue())
                .regDate(question.getRegDate())
                .modDate(question.getModDate())
                .build();

        return dto;
    }
}
