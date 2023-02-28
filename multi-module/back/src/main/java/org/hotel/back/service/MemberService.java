package org.hotel.back.service;


import lombok.RequiredArgsConstructor;
import org.hotel.back.data.request.RegisterData;
import org.hotel.back.domain.Gender;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Member;
import org.hotel.back.domain.MemberRole;
import org.hotel.back.repository.MemberRepository;
import org.hotel.back.repository.custom.ManagerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final ManagerRepository managerRepository;


    /**
     * @param registerData 회원가입에 사용한 데이터
     * @return true/false 성공시 true 실패시 false
     *
     * */
    public boolean registerSave(RegisterData registerData){


        Member member = Member.builder()
                .email(registerData.getEmail())
                .password(passwordEncoder.encode(registerData.getPassword()))
                .gender(registerData.getGender().equals("MAN") ? Gender.MAN : Gender.WOMAN)
                .nickName(registerData.getNickName())
                //.tellNumber(registerData.getTellNumber())
                .build();
        member.addRole(MemberRole.ROLE_USER);

        boolean fail =ObjectUtils.isEmpty(memberRepository.save(member));

        return !fail;
    }

    /**
     * @param email
     * @return true/false 있다면 true 없다면 false
     * */
    public boolean checkEmail(String email){
        return memberRepository.existsByEmail(email);
    }



    /**
     * DTO 변환필요
     *
     * */
    public Hotel getMemberCheckHotelInfo(String email){
        return managerRepository.getHotelInfo(email);
    }
}
