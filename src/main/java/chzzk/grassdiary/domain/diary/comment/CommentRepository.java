package chzzk.grassdiary.domain.diary.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiary(Long diaryId);
}
