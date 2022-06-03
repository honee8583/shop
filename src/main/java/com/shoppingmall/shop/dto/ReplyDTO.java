package com.shoppingmall.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDTO {

    private Long rno;

    private String text;

    private String replyerEmail; //member의 email 필드

    private String replyerName; //member의 name필드

    private Long qno;

    private LocalDateTime regDate, modDate;
}
