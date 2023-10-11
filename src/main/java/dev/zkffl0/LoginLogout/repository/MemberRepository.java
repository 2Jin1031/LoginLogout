package dev.zkffl0.LoginLogout.repository;

import dev.zkffl0.LoginLogout.domain.Member;
import dev.zkffl0.LoginLogout.dto.UpdatePasswordDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginID(String loginID);
    Optional<Member> updatePassword(String loginId, UpdatePasswordDto updatePasswordDto);
}
