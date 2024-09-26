package chzzk.grassdiary.domain.color.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ColorCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "colorCode_id")
    private Long id;

    private String colorName;

    private String rgb;

    private int price;

    @Builder
    public ColorCode(String colorName, String rgb, int price) {
        this.colorName = colorName;
        this.rgb = rgb;
        this.price = price;
    }
}
