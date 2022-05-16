package com.shoppingmall.shop.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "member")
@Getter
public class Notice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;  //작성자

    @Column(nullable = false)
    private String title;   //제목

    @Column(nullable = false)
    private String content; //내용

    private int count;  //조회수

    //조회수 증가
    public void addCount(){
        this.count++;
    }

    //제목 수정
    public void changeTitle(String title){
        this.title = title;
    }

    //내용 수정
    public void changeContent(String content){
        this.content = content;
    }
}
