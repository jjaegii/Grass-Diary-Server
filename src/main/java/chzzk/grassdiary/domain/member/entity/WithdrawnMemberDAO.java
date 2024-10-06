package chzzk.grassdiary.domain.member.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawnMemberDAO extends JpaRepository<WithdrawnMember, Long> {
    boolean existsByHashedEmail(String hashedEmail);
}