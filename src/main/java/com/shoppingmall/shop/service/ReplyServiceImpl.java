package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Entity.Reply;
import com.shoppingmall.shop.Repository.ReplyRepository;
import com.shoppingmall.shop.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {

        log.info("ReplyService -> register");

        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);

        return reply.getRno();
    }

    @Transactional
    @Override
    public List<ReplyDTO> getList(Long qno) {

        log.info("ReplyService -> getList");

        Question question = Question.builder().qno(qno).build();

        List<Reply> result = replyRepository.getRepliesByQuestionOrderByRno(question);

        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        log.info("ReplyService -> modify");

        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());

        Reply reply = result.get();

        reply.changeText(replyDTO.getText());

        replyRepository.save(reply);

    }

    @Override
    public void remove(Long rno) {

        log.info("ReplyService -> remove");

        replyRepository.deleteById(rno);

    }
}
