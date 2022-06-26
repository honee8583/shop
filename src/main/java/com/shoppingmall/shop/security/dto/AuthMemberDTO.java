package com.shoppingmall.shop.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String email;

    private String password;

    private String name;

    private String address;

    private String phone;

    private boolean fromSocial;

    private Map<String, Object> attr;

    //일반로그인
    public AuthMemberDTO(
            String username,
            String password,
            String name,
            String address,
            String phone,
            boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fromSocial = fromSocial;
    }

    //OAuth 로그인
    public AuthMemberDTO(
            String username,
            String password,
            String name,
            String address,
            String phone,
            boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attr) {
        this(username, password, name, address, phone, fromSocial, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
