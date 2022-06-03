package com.shoppingmall.shop.Repository;

import com.shoppingmall.shop.Entity.Question;
import com.shoppingmall.shop.Entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r where r.question.qno = :qno")
    void deleteByQno(Long qno);

    List<Reply> getRepliesByQuestionOrderByRno(Question question);

}
