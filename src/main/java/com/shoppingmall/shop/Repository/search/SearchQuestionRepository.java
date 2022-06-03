package com.shoppingmall.shop.Repository.search;

import com.shoppingmall.shop.Entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchQuestionRepository {

    Question search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
