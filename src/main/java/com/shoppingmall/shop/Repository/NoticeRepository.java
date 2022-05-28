package com.shoppingmall.shop.Repository;

import com.shoppingmall.shop.Entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;


public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice> {

    @Query("select n, w from Notice n left join n.member w where n.nno = :id")
    Object getNoticeWithMember(@Param("id") Long id);
}
