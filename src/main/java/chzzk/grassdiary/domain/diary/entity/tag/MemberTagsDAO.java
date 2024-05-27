package chzzk.grassdiary.domain.diary.entity.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberTagsDAO extends JpaRepository<MemberTags, Long> {
    @Query("SELECT mt.tagList.id FROM MemberTags mt WHERE mt.member.id = :memberId")
    List<Long> findTagIdsByMemberId(Long memberId);

    Optional<MemberTags> findByMemberIdAndTagList(Long memberId, TagList tagList);
}
