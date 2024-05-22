package chzzk.grassdiary.domain.diary.dto;

public class DiarySaveResponseDTO {
    // todo: baseURL 전역으로 빼기
    private static String baseURL = "https://chzzk-image-server.s3.ap-northeast-2.amazonaws.com/";
    public Long diaryId;
    public Boolean hasImage;
    public String imageUrl;

    public DiarySaveResponseDTO(Long diaryId, Boolean hasImage, String imagePath) {
        this.diaryId = diaryId;
        this.hasImage = hasImage;
        this.imageUrl = baseURL + imagePath;
    }
}
