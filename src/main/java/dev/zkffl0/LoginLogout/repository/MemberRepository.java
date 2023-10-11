package dev.zkffl0.LoginLogout.repository;

import dev.zkffl0.LoginLogout.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
