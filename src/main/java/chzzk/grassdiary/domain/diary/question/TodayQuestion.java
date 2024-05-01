package chzzk.grassdiary.domain.diary.question;

import chzzk.grassdiary.domain.base.BaseCreatedTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TodayQuestion extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "today_question_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionPrompt questionPrompt;

    @Builder
    public TodayQuestion(QuestionPrompt questionPrompt) {
        this.questionPrompt = questionPrompt;
    }
}
