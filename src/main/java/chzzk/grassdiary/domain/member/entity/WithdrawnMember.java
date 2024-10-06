package chzzk.grassdiary.domain.member.entity;

import chzzk.grassdiary.domain.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawnMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawn_member_id")
    private Long id;

    @Column(name = "hashed_email", nullable = false, unique = true)
    private String hashedEmail;

    @Builder
    public WithdrawnMember(String hashedEmail) {
        this.hashedEmail = hashedEmail;
    }
}
