package chzzk.grassdiary.domain.image.dto;


import chzzk.grassdiary.domain.image.entity.DiaryToImage;


public record ImageDTO (
        Long imageId,
        String imageURL,
        String imageName,
        Long imageSize
) {
    public static ImageDTO from(DiaryToImage diaryToImage, String imageURL, String imageName, Long imageSize) {
        return new ImageDTO(diaryToImage.getImage().getId(), imageURL, imageName, imageSize);
    }
}
