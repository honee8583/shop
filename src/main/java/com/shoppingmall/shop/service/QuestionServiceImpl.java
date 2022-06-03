package com.shoppingmall.shop.service;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Repository.QuestionRepository;
import com.shoppingmall.shop.Repository.ReplyRepository;
import com.shoppingmall.shop.dto.PageRequestDTO;
import com.shoppingmall.shop.dto.PageResultDTO;
import com.shoppingmall.shop.dto.QuestionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public PageResultDTO<QuestionDTO, Object[]> getList(PageRequestDTO requestDTO) {
        log.info("QuestionService -> getList");

        Function<Object[], QuestionDTO> fn = (en -> entityToDTO((Question)en[0], (Member)en[1], (Long)en[2]));

        Page<Object[]> result = questionRepository.searchPage(requestDTO.getType(), requestDTO.getKeyword(), requestDTO.getPageable(Sort.by("qno").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public QuestionDTO get(Long qno) {

        log.info("QuestionService -> get");
        log.info("qno : " + qno);

        Object result = questionRepository.getQuestionByQno(qno);

        Object[] arr = (Object[]) result;

        QuestionDTO questionDTO = entityToDTO((Question) arr[0], (Member) arr[1], (Long) arr[2]);

        return questionDTO;
    }

    @Override
    public Long register(QuestionDTO dto) {

        log.info("QuestionService -> register");

        Question question = dtoToEntity(dto);

        questionRepository.save(question);

        return question.getQno();
    }

    @Override
    public void modify(QuestionDTO dto) {

        log.info("QuestionService -> modify");

        Optional<Question> result = questionRepository.findById(dto.getQno());

        if(result.isPresent()){
            Question question = result.get();

            question.changeTitle(dto.getTitle());
            question.changeContent(dto.getContent());

            questionRepository.save(question);
        }
    }

    @Transactional
    @Override
    public void removeWithReplies(Long qno) {

        log.info("QuestionService -> remove reply, question");

        replyRepository.deleteByQno(qno);

        questionRepository.deleteById(qno);
    }

}
