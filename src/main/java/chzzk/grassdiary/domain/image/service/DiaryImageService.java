package chzzk.grassdiary.domain.image.service;

import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.image.entity.DiaryToImage;
import chzzk.grassdiary.domain.image.entity.DiaryToImageDAO;
import chzzk.grassdiary.domain.image.entity.Image;
import chzzk.grassdiary.domain.image.entity.ImageDAO;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryImageService {
    private final ImageDAO imageDAO;
    private final ImageService imageService;
    private final DiaryToImageDAO diaryToImageDAO;

    public Long getImageIdByDiaryId(Long diaryId) {
        return diaryToImageDAO.findByDiaryId(diaryId)
                .map(diaryToImage -> diaryToImage.getImage().getId())
                .orElse(0L);
    }

    /**
     * FRONT: 이미지 id와 Diary 값을 주면 검색해서 둘을 잇는 값을 추가
     */
    public void mappingImageToDiary(Diary diary, Long imageId) {
        Image uploadedImage = imageDAO.findById(imageId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.IMAGE_NOT_FOUND_ERR));

        diaryToImageDAO.save(new DiaryToImage(diary, uploadedImage));
    }

    public void deleteImageAndMapping(Diary diary) {
        diaryToImageDAO.findByDiaryId(diary.getId()).ifPresent(diaryImage -> {
            imageService.deleteImage(diaryImage.getImage().getId());
            diaryToImageDAO.delete(diaryImage);
        });
    }
}
