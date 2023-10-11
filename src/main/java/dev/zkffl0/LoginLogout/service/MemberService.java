package dev.zkffl0.LoginLogout.service;

import dev.zkffl0.LoginLogout.domain.Member;
import dev.zkffl0.LoginLogout.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

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
}
