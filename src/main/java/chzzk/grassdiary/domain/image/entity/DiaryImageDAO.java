package chzzk.grassdiary.domain.image.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryImageDAO extends JpaRepository<DiaryImage, Long> {
    DiaryImage findByDiaryId(Long diaryId);
}
