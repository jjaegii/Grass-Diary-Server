package chzzk.grassdiary.domain.diary.service;

import chzzk.grassdiary.domain.diary.entity.question.TodayQuestion;
import chzzk.grassdiary.domain.diary.entity.question.TodayQuestionDAO;
import chzzk.grassdiary.domain.diary.dto.TodayQuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static chzzk.grassdiary.domain.diary.entity.question.QuestionPrompt.getRandomQuestion;

@RequiredArgsConstructor
@Service
public class TodayQuestionService {

    private final TodayQuestionDAO todayQuestionDAO;

    @Scheduled(cron = "0 0 0 * * *")
    public void makeTodayQuestions() {
        todayQuestionDAO.save(new TodayQuestion(getRandomQuestion()));
    }

    @Transactional(readOnly = true)
    public TodayQuestionDTO getTodayQuestion() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        TodayQuestion todayQuestion = todayQuestionDAO.findByCreatedAtBetween(startOfDay, endOfDay);

        return new TodayQuestionDTO(todayQuestion.getQuestionPrompt().getQuestion());
    }
}
