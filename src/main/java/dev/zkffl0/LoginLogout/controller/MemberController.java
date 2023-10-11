package dev.zkffl0.LoginLogout.controller;

import dev.zkffl0.LoginLogout.domain.Member;
import dev.zkffl0.LoginLogout.dto.UpdatePasswordDto;
import dev.zkffl0.LoginLogout.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member") // 멤버 생성
    public ResponseEntity<Member> createMember(@RequestBody Member member) {

        try {
            ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(memberService.save(member));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/member/{id}") // 멤버 id로 멤버 조회
    public ResponseEntity<Optional<Member>> getMemberById(@PathVariable("id") Long id) {

        try {
            ResponseEntity.ok(memberService.findById(id)); // ResponseEntity.ok
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/update-password") // 로그인아이디멤버 찾고, passwordData, _password로 password 업데이트
    public ResponseEntity<Optional<Member>> updatePassword(@RequestParam("loginId") String loginId, @RequestBody UpdatePasswordDto updatePasswordDto) {

        try {
            ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(memberService.updatePassword(loginId, updatePasswordDto));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/member/{id}") // 멤버 id로 멤버 정보 수정, 업데이트
    public ResponseEntity<Optional<Member>> updateMember(@PathVariable("id") Long id, @RequestBody Member member) {

        try {
            ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(memberService.update(id, member));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @DeleteMapping("/member/{id}") // 멤버 id로 멤버 삭제
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("id") Long id) {

        try {
            memberService.delete(id);
            ResponseEntity.noContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
