package chzzk.grassdiary.domain.image.dto;


import chzzk.grassdiary.domain.image.entity.DiaryToImage;


public record ImageDTO (
        Long imageId,
        String imageURL
) {
    public static ImageDTO from(DiaryToImage diaryToImage, String imageURL) {
        return new ImageDTO(diaryToImage.getImage().getId(), imageURL);
    }
}
