package chzzk.grassdiary.domain.image.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryToImageDAO extends JpaRepository<DiaryToImage, Long> {
    Optional<DiaryToImage> findByDiaryId(Long diaryId);
    Optional<DiaryToImage> findByImageId(Long imageId);
}
