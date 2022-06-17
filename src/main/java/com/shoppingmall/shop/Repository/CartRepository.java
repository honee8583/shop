package com.shoppingmall.shop.Repository;

import com.shoppingmall.shop.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
