package com.shoppingmall.shop.Entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
@Getter
public class Member extends BaseEntity{

    @Id
    private String email;

    private String password;

    private String name;

    private String address;

    private String phone;

    private int mileage;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    //권한 추가 메소드
    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }
}
