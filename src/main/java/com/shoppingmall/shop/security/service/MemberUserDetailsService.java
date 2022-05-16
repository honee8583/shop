package com.shoppingmall.shop.security.service;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Repository.MemberRepository;
import com.shoppingmall.shop.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("MemberUserDetailsService loadUserByUsername" + username);

        Optional<Member> result = memberRepository.findByEmail(username, false);

        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check Email or Social");
        }

        Member member = result.get();

        log.info("----------------------------");
        log.info(member);

        AuthMemberDTO memberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        memberDTO.setName(member.getName());
        memberDTO.setFromSocial(member.isFromSocial());

        return memberDTO;
    }
}
