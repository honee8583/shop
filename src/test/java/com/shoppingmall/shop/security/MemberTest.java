package com.shoppingmall.shop.security;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.MemberRole;
import com.shoppingmall.shop.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .name("Member" + i)
                    .address("Seoul")
                    .phone("010-1234-5678")
                    .mileage(0)
                    .fromSocial(false)
                    .build();

            member.addMemberRole(MemberRole.USER);

            if(i > 80){
                member.addMemberRole(MemberRole.MANAGER);
            }
            if(i > 90){
                member.addMemberRole(MemberRole.ADMIN);
            }
            repository.save(member);
        });
    }

    @Test
    public void insertAdmin(){
        Member member = Member.builder()
                .email("honee8583@naver.com")
                .password(passwordEncoder.encode("dahoon0508"))
                .name("LeeDaHoon")
                .address("Seoul")
                .phone("010-1111-2222")
                .mileage(0)
                .fromSocial(false)
                .build();

        member.addMemberRole(MemberRole.ADMIN);

        repository.save(member);
    }

    @Test
    public void deleteAdmin(){
        repository.delete(Member.builder().email("honee8583@naver.com").build());
    }

}
