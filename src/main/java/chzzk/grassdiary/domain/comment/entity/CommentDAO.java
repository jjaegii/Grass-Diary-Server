package chzzk.grassdiary.domain.comment.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDAO extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiaryId(Long diaryId);
}
