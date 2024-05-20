package chzzk.grassdiary.domain.reward.controller;

import chzzk.grassdiary.domain.reward.service.RewardService;
import chzzk.grassdiary.domain.member.dto.RewardHistoryDTO;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/reward")
@Tag(name = "리워드 컨트롤러")
public class RewardController {
    private final RewardService rewardService;

    @GetMapping("history/{memberId}")
    @Operation(
            summary = "멤버의 리워드 내역",
            description = "")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RewardHistoryDTO.class)))
    public ResponseEntity<?> getRewardHistory(@PathVariable Long memberId) {
        return ResponseEntity.ok(rewardService.getRewardHistory(memberId));
    }
}
