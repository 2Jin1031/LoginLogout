package dev.zkffl0.LoginLogout.service;

import dev.zkffl0.LoginLogout.domain.Member;
import dev.zkffl0.LoginLogout.dto.FindEmailRequestDto;
import dev.zkffl0.LoginLogout.dto.UpdatePasswordDto;
import dev.zkffl0.LoginLogout.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {

        try {
            Member memberData = Member.builder()
                    .loginID(member.getLoginID())
                    .password(member.getPassword())
                    .name(member.getName())
                    .email(member.getEmail())
                    .phoneN(member.getPhoneN())
                    .build();
            return memberRepository.save(memberData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Optional<Member> findById (Long id) {

        try {
            Optional<Member> memberData = memberRepository.findById(id);
            if (memberData.isPresent()) {
                return memberData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Optional<Member> findByLoginId (String loginId) {

        try {
            Optional<Member> memberData = memberRepository.findByLoginID(loginId);
            if (memberData.isPresent()) {
                return memberData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Optional<Member> updatePassword(String loginId, UpdatePasswordDto updatePasswordDto) {

        try {
            Optional<Member> memberData = memberRepository.findByLoginID(loginId);
            if (memberData.isPresent()) {
                Member _member = memberData.get();
                _member.setPassword(updatePasswordDto.get_password());
                memberRepository.save(_member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public Optional<Member> update (Long id, Member member) {

        try {
            Optional<Member> memberData = memberRepository.findById(id);
            if (memberData.isPresent()) {
                Member _member = memberData.get();
                _member.setPassword(member.getPassword());
                _member.setName(member.getName());
                _member.setEmail(member.getEmail());
                _member.setPhoneN(member.getPhoneN());
                memberRepository.save(_member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete (Long id) {

        try {
            memberRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ResponseEntity<?> sendSmsToFindEmail(FindEmailRequestDto requestDto) {
        String name = requestDto.getName();
        //수신번호 형태에 맞춰 "-"을 ""로 변환
        String phoneNum = requestDto.getPhoneNum().replaceAll("-","");

        Member memberData = memberRepository.findByNameAndPhone(name, phoneNum).orElseThrow(()->
                new NoSuchElementException("회원이 존재하지 않습니다."));

        String receiverEmail = memberData.getEmail();
        String verificationCode = validationUtil.createCode();
        smsUtil.sendOne(phoneNum, verificationCode);

        //인증코드 유효기간 5분 설정
        redisUtil.setDataExpire(verificationCode, receiverEmail, 60 * 5L);

        return ResponseEntity.ok(new Message("SMS 전송 성공"));
    }
}
