package com.shoppingmall.shop.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encodeTest(){
        String pwd = "1111";

        String enPwd = passwordEncoder.encode(pwd);

        System.out.println("encoded password : " + enPwd);
        //$2a$10$OM5puPVI0KcoWYWk.Ey05uIqoPEGPZEufCD2Y6x4w63eDFQhLd2cS

        Boolean match = passwordEncoder.matches(pwd, enPwd);

        System.out.println("Is it same? > " + match);
    }
}
