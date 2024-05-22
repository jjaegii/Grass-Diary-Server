package chzzk.grassdiary.domain.image.service;

import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.image.entity.DiaryImage;
import chzzk.grassdiary.domain.image.entity.DiaryImageDAO;
import chzzk.grassdiary.global.util.file.FileFolder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryImageService {
    // todo: baseURL 전역으로 빼기
    private static String baseURL = "https://chzzk-image-server.s3.ap-northeast-2.amazonaws.com/";

    private final DiaryImageDAO diaryImageDAO;

    private final AwsS3Service awsS3Service;

    public String uploadDiaryImage(MultipartFile image, FileFolder category, Diary diary) {
        if(image.isEmpty()) {
            throw new IllegalArgumentException("Image cannot be empty");
        }

        String imagePath = awsS3Service.uploadBucket(image, category);
        diaryImageDAO.save(new DiaryImage(diary, imagePath));
        return imagePath;
    }

    public String updateImage(Boolean originalHasImage, MultipartFile image, FileFolder category, Diary diary) {
        if (!originalHasImage) {
            return uploadDiaryImage(image, category, diary);
        }
        DiaryImage diaryImage = diaryImageDAO.findByDiaryId(diary.getId());
        String originalImagePath = diaryImage.getImagePath();
        awsS3Service.deleteImage(originalImagePath);

        String newImagePath = awsS3Service.uploadBucket(image, category);
        diaryImage.updateImagePath(newImagePath);
        return newImagePath;
    }

    public void deleteImage(Diary diary) {
        DiaryImage diaryImage = diaryImageDAO.findByDiaryId(diary.getId());
        awsS3Service.deleteImage(diaryImage.getImagePath());
        diaryImageDAO.delete(diaryImage);
    }

    public String getImageURL(Diary diary) {
        return baseURL + diaryImageDAO.findByDiaryId(diary.getId()).getImagePath();
    }
}
