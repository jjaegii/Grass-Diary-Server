package chzzk.grassdiary.domain.diary.entity.tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TagList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagList_id")
    private Long id;

    @Column(nullable = false)
    private String tag;

    @ColumnDefault("0")
    private int tagUsageCount;

    @Builder
    public TagList(String tag) {
        this.tag = tag;
        this.tagUsageCount = 0;
    }

    public void incrementCount() {
        tagUsageCount += 1;
    }

    public void decrementCount() {
        tagUsageCount -= 1;
    }
}
