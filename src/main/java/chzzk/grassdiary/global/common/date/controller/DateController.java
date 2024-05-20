package chzzk.grassdiary.global.common.date.controller;

import chzzk.grassdiary.global.util.date.DateUtil;
import chzzk.grassdiary.global.common.date.dto.TodayDateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/main")
@Tag(name = "날짜 정보를 반환하는 컨트롤러")
public class DateController {

    @GetMapping("/today-date")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = TodayDateDTO.class)))
    @Operation(
            summary = "오늘의 날짜 정보",
            description = "")
    public ResponseEntity<?> getTodayDate() {
        return ResponseEntity.ok(DateUtil.getTodayDate());
    }
}
