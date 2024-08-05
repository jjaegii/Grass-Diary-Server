package chzzk.grassdiary.domain.image.service;

import chzzk.grassdiary.domain.image.dto.ImageDTO;
import chzzk.grassdiary.domain.image.entity.DiaryToImage;
import chzzk.grassdiary.domain.image.entity.DiaryToImageDAO;
import chzzk.grassdiary.domain.image.entity.Image;
import chzzk.grassdiary.domain.image.entity.ImageDAO;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import chzzk.grassdiary.global.util.file.FileFolder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    // todo: baseURL 전역으로 빼기
    private static String baseURL = "https://chzzk-image-server.s3.ap-northeast-2.amazonaws.com/";

    private final ImageDAO imageDAO;
    private final AwsS3Service awsS3Service;
    private final DiaryToImageDAO diaryToImageDAO;

    public ImageDTO uploadImage(MultipartFile image, FileFolder category) {
        if(image.isEmpty()) {
            throw new SystemException(ClientErrorCode.IMAGE_FILE_EMPTY);
        }
        String imagePath = awsS3Service.uploadBucket(image, category);
        Image uploadedImage = imageDAO.save(new Image(imagePath));

        return new ImageDTO(uploadedImage.getId(), baseURL + imagePath);
    }

    /**
     * imagePath 값은 'personal-diary/653b73af-719a-4b3a-9916-d7ba0a2a77bb.PNG' 와 같이 나타나야 함
     * DB 내 image id로 검색 후 제거 > aws 버킷에서 이미지 제거
     */
    public void deleteImage(Long imageId) {
//        1. DiaryToImage 매핑 값 제거
//        1. DB에서 이미지 제거
//        2. AWS 버킷에서 이미지 제거
        DiaryToImage diaryToImageMapping = diaryToImageDAO.findByImageId(imageId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.IMAGE_MAPPING_NOT_FOUND_ERR));
        diaryToImageDAO.delete(diaryToImageMapping);

        Image image = imageDAO.findById(imageId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.IMAGE_NOT_FOUND_ERR));

        imageDAO.deleteById(imageId);
        awsS3Service.deleteImage(image.getImagePath());
    }

    public String getImageURLByImage(Image image) {
        return baseURL + image.getImagePath();
    }

}
