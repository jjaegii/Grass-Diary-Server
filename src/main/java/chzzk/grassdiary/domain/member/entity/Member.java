package chzzk.grassdiary.domain.member.entity;

import chzzk.grassdiary.domain.base.BaseTimeEntity;
import chzzk.grassdiary.domain.color.ColorCode;
import chzzk.grassdiary.domain.diary.entity.Diary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, name = "given_name", unique = true)
    @Size(min = 1, max = 20)
    private String nickname;

    @Column(nullable = false, length = 200)
    private String email;

    @Lob
    private String picture;

    private long grassCount;

    private int rewardPoint;

    private boolean hasNewColor;

    @ManyToOne(fetch = FetchType.LAZY)
    private ColorCode currentColorCode;

    private String profileIntro;

    @OneToMany(fetch = FetchType.LAZY)
    private final List<Diary> diaries = new ArrayList<>();

    public Member(String nickname, String email, ColorCode currentColorCode) {
        this.nickname = nickname;
        this.email = email;
        this.currentColorCode = currentColorCode;
        this.rewardPoint = 0;
    }

    public static Member of(String nickname, String email, String picture, ColorCode currentColorCode) {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .picture(picture)
                .currentColorCode(currentColorCode)
                .profileIntro("")
                .build();
    }

    public void updateProfile(String nickname, String profileIntro) {
        if (isAvailable(nickname)) {
            this.nickname = nickname;
        }

        if (isAvailable(profileIntro)) {
            this.profileIntro = profileIntro;
        }
    }

    private boolean isAvailable(String text) {
        return text != null && !text.isBlank();
    }

    public void addRandomPoint(Integer randomPoint) {
        this.rewardPoint += randomPoint;
    }
}
