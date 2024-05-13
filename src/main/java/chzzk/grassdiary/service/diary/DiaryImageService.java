package chzzk.grassdiary.service.diary;

import chzzk.grassdiary.domain.diary.Diary;
import chzzk.grassdiary.domain.diary.DiaryImage;
import chzzk.grassdiary.domain.diary.DiaryImageRepository;
import chzzk.grassdiary.storage.AwsS3Service;
import chzzk.grassdiary.utils.file.FileFolder;
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

    private final DiaryImageRepository diaryImageRepository;

    private final AwsS3Service awsS3Service;

    public String uploadDiaryImage(MultipartFile image, FileFolder category, Diary diary) {
        if(image.isEmpty()) {
            throw new IllegalArgumentException("Image cannot be empty");
        }

        String imagePath = awsS3Service.uploadBucket(image, category);
        diaryImageRepository.save(new DiaryImage(diary, imagePath));
        return imagePath;
    }

    public String updateImage(Boolean originalHasImage, MultipartFile image, FileFolder category, Diary diary) {
        if (!originalHasImage) {
            return uploadDiaryImage(image, category, diary);
        }
        DiaryImage diaryImage = diaryImageRepository.findByDiaryId(diary.getId());
        String originalImagePath = diaryImage.getImagePath();
        awsS3Service.deleteImage(originalImagePath);

        String newImagePath = awsS3Service.uploadBucket(image, category);
        diaryImage.updateImagePath(newImagePath);
        return newImagePath;
    }

    public void deleteImage(Diary diary) {
        DiaryImage diaryImage = diaryImageRepository.findByDiaryId(diary.getId());
        awsS3Service.deleteImage(diaryImage.getImagePath());
        diaryImageRepository.delete(diaryImage);
    }

    public String getImageURL(Diary diary) {
        return baseURL + diaryImageRepository.findByDiaryId(diary.getId()).getImagePath();
    }
}
