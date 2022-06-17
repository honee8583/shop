package com.shoppingmall.shop.repository;

import com.shoppingmall.shop.Entity.Cart;
import com.shoppingmall.shop.Entity.Member;
import com.shoppingmall.shop.Repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("장바구니 생성 테스트")
    public void createDummies(){

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@gmail.com").build();

            Cart cart = Cart.builder().member(member).build();

            cartRepository.save(cart);
        });
    }
}
