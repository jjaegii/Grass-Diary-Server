package chzzk.grassdiary.domain.member.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPurchasedColorDAO extends JpaRepository<MemberPurchasedColor, Long> {
    boolean existsByColorCodeIdAndMemberId(Long memberId, Long colorCodeId);
    List<MemberPurchasedColor> findAllByMemberId(Long memberId);
}
