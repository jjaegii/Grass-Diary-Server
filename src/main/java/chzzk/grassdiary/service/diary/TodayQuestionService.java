package chzzk.grassdiary.service.diary;

import chzzk.grassdiary.domain.diary.question.TodayQuestion;
import chzzk.grassdiary.domain.diary.question.TodayQuestionRepository;
import chzzk.grassdiary.web.dto.main.TodayInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import static chzzk.grassdiary.domain.diary.question.QuestionPrompt.getRandomQuestion;

@RequiredArgsConstructor
@Service
public class TodayQuestionService {

    private final TodayQuestionRepository todayQuestionRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void makeTodayQuestions() {
        todayQuestionRepository.save(new TodayQuestion(getRandomQuestion()));
    }

    @Transactional(readOnly = true)
    public TodayInfoDTO getTodayQuestion() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        TodayQuestion todayQuestion = todayQuestionRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        return new TodayInfoDTO(
                today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                todayQuestion.getQuestionPrompt().getQuestion()
        );
    }
}
