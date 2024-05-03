package chzzk.grassdiary.web.dto.main;

public record TodayInfoDTO(
        int year,
        int month,
        int date,
        String day,
        String todayQuestion
) {
}
