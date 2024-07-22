package chzzk.grassdiary.domain.diary.entity.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagListDAO extends JpaRepository<TagList, Long> {
    List<TagList> findTagDTOByIdIn(List<Long> ids);

    Optional<TagList> findByTag(String tag);
}
