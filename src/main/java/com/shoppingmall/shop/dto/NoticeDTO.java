package com.shoppingmall.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class NoticeDTO {

    private Long nno;

    private String title;

    private String content;

    private String writerEmail;

    private String writerName;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int count;

}
