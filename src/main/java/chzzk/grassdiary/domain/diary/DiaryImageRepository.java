package chzzk.grassdiary.domain.diary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    DiaryImage findByDiaryId(Long diaryId);
}
