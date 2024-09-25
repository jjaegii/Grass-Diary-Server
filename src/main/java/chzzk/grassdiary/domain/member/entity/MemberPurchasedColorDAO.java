package chzzk.grassdiary.domain.member.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPurchasedColorDAO extends JpaRepository<MemberPurchasedColor, Long> {
    boolean existsByColorCodeIdAndMemberId(Long memberId, Long colorCodeId);
}
