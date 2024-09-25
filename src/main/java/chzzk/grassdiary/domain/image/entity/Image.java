package chzzk.grassdiary.domain.image.entity;

import chzzk.grassdiary.domain.base.BaseCreatedTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Image extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Lob
    private String imagePath;

    private String imageName;

    private Long size;

    public Image(String imagePath, String imageName, Long size) {
        this.imagePath = imagePath;
        this.imageName = imageName;
        this.size = size;
    }
}
