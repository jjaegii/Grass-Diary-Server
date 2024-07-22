package chzzk.grassdiary.global.util.date;

import chzzk.grassdiary.global.system.date.dto.TodayDateDTO;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtil {
    public static TodayDateDTO getTodayDate() {
        LocalDate today = LocalDate.now();

        return new TodayDateDTO(
                today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        );
    }
}
