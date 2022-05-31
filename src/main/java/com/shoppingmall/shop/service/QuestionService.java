package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import com.shoppingmall.shop.dto.QuestionDTO;

public interface QuestionService {

    PageResultDTO<QuestionDTO, Question> getList(PageRequestDTO requestDTO);
    QuestionDTO get(Long qno);
    void register(QuestionDTO dto);
    void modify(QuestionDTO dto);
    void remove(Long qno);

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

    default QuestionDTO entityToDTO(Question question){
        QuestionDTO dto = QuestionDTO.builder()
                .qno(question.getQno())
                .title(question.getTitle())
                .content(question.getContent())
                .writerEmail(question.getMember().getEmail())
                .writerName(question.getMember().getName())
                .count(question.getCount())
                .regDate(question.getRegDate())
                .modDate(question.getModDate())
                .build();

        return dto;
    }
}
