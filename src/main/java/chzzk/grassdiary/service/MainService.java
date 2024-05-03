package chzzk.grassdiary.service;

import chzzk.grassdiary.web.dto.main.TodayDateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {
    public TodayDateDTO getTodayDate() {
        LocalDate today = LocalDate.now();

        return new TodayDateDTO(
                today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        );
    }
}
