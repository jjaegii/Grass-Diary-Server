package chzzk.grassdiary.domain.member.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberDAO extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);

    @Query("SELECT m.rewardPoint FROM Member m WHERE m.id = :memberId")
    Integer findRewardPointById(Long memberId);
}
