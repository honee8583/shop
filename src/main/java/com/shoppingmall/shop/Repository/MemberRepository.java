package com.shoppingmall.shop.Repository;

import com.shoppingmall.shop.Entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    //소셜가입여부와 이메일을 기준으로 사용자 조회
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email = :email and m.fromSocial = :social")
    Optional<Member> findByEmail(@Param("email") String email, @Param("social") boolean social);

}
