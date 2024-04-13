package chzzk.grassdiary.service;

import static chzzk.grassdiary.domain.diary.question.QuestionPrompt.getRandomQuestion;

import chzzk.grassdiary.domain.diary.question.QuestionPrompt;
import chzzk.grassdiary.web.dto.main.TodayInfoDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainService {

    public TodayInfoDTO getTodayInfo() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("오늘은 M월 d일입니다.");
        String todayDate = today.format(dateTimeFormatter);

        QuestionPrompt todayQuestion = getRandomQuestion();

        return new TodayInfoDTO(todayDate, "fix");
    }
}
