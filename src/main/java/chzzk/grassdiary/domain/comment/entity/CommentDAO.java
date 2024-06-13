package chzzk.grassdiary.domain.comment.entity;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDAO extends JpaRepository<Comment, Long> {
    Slice<Comment> findAllByDiaryId(Long diaryId, Pageable pageable);
}
