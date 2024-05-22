package chzzk.grassdiary.domain.diary.controller;

import chzzk.grassdiary.domain.diary.dto.CountAndMonthGrassDTO;
import chzzk.grassdiary.domain.diary.service.GrassCountService;
import chzzk.grassdiary.domain.member.dto.GrassInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/grass")
@Tag(name = "잔디 컨트롤러")
public class GrassCountController {
    private final GrassCountService grassCountService;

    @GetMapping("{memberId}")
    @Operation(
            summary = "사용자 전체 잔디 불러오기",
            description = "")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = GrassInfoDTO.class)))
    @Parameter(name = "memberId", description = "멤버 아이디")
    public ResponseEntity<?> getGrassHistory(@PathVariable Long memberId) {
        return ResponseEntity.ok(grassCountService.findGrassHistoryById(memberId));
    }

    @GetMapping("/main-page/{memberId}")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CountAndMonthGrassDTO.class)))
    @Operation(
            summary = "사용자의 현재 총 일기 개수, 이번 달 잔디 기록",
            description = "사용자의 현재까지의 총 잔디(일기) 개수, 이번달 잔디 기록, 이번달 잔디 개수")
    @Parameter(name = "memberId", description = "멤버 아이디")
    public ResponseEntity<?> getGrassCountAndMonthGrass(@PathVariable Long memberId) {
        return ResponseEntity.ok(grassCountService.countAllAndMonthGrass(memberId));
    }
}
