package org.hotel.back.repository;

import org.hotel.back.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("Member EntityGraph 테스트")
    void test(){
        Member member = memberRepository.getMember("test").get();

        Assertions.assertNotNull(member);
    }
}