package org.hotel.back.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Member {
    @Id
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String nickName;

    private String tellNumber;
    @ToString.Exclude
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roleSet = new HashSet<>();
    @ToString.Exclude
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "member")
    private List<Booking> bookingList = new ArrayList<>();

    private String tellNumber;

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

}
