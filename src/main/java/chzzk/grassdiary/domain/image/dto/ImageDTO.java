package chzzk.grassdiary.domain.image.dto;


import chzzk.grassdiary.domain.image.entity.DiaryToImage;


public record ImageDTO (
        Long imageId,
        String imageURL,
        String imageName
) {
    public static ImageDTO from(DiaryToImage diaryToImage, String imageURL, String imageName) {
        return new ImageDTO(diaryToImage.getImage().getId(), imageURL, imageName);
    }
}
