package com.shoppingmall.shop.security.service;

import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Entity.MemberRole;
import com.shoppingmall.shop.Repository.MemberRepository;
import com.shoppingmall.shop.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuth2MemberUserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("-------------------------------");
        log.info("userRequest : " + userRequest);

        //String clientName = userRequest.getClientRegistration().getClientId();
        String clientName = userRequest.getClientRegistration().getClientName();    //google

        log.info("clientName : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("===============================");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + ":" + v);
        });

        String email = null;

        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }

        log.info("EMAIL : " + email);

        Member member = saveSocialMember(email);    //최초 로그인일경우 강제 회원가입 진행

        //OAuth2User 구성
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getAddress(),
                member.getPhone(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name())
                ).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );

        authMemberDTO.setName(member.getName());

        //OAuth2User 반환시 로그인 완료
        return authMemberDTO;
    }

    private Member saveSocialMember(String email){

        Optional<Member> result = memberRepository.findByEmail(email, true);

        if(result.isPresent()){ //가입된 회원일경우 반환
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        member.addMemberRole(MemberRole.USER);

        memberRepository.save(member);  //신규가입일경우 데이터베이스에 저장후 반환

        return member;
    }
}
