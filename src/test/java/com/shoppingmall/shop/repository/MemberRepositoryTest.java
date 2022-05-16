package com.shoppingmall.shop.repository;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.MemberRole;
import com.shoppingmall.shop.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findByEmailTest(){
        Optional<Member> result = memberRepository.findByEmail("user1@gmail.com", false);

        Member member = result.get();

        System.out.println(member.toString());
    }
}
