package chzzk.grassdiary.domain.member.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberPurchasedColorDAO extends JpaRepository<MemberPurchasedColor, Long> {
    boolean existsByColorCodeIdAndMemberId(Long memberId, Long colorCodeId);

    List<MemberPurchasedColor> findAllByMemberId(Long memberId);

    @Modifying
    @Transactional
    @Query("DELETE FROM MemberPurchasedColor m WHERE m.member.id = :memberId")
    void deleteAllByMemberId(Long memberId);
}
