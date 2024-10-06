package chzzk.grassdiary.domain.member.controller;

import chzzk.grassdiary.domain.member.dto.EquipColorResponseDTO;
import chzzk.grassdiary.domain.member.dto.MemberPurchasedColorsResponseDTO;
import chzzk.grassdiary.domain.member.service.MemberService;
import chzzk.grassdiary.domain.member.service.MyPageService;
import chzzk.grassdiary.domain.member.dto.MemberInfoDTO;
import chzzk.grassdiary.domain.member.dto.TotalRewardDTO;
import chzzk.grassdiary.domain.member.service.WithdrawnMemberService;
import chzzk.grassdiary.domain.reward.service.RewardService;
import chzzk.grassdiary.global.auth.common.AuthenticatedMember;
import chzzk.grassdiary.global.auth.service.dto.AuthMemberPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
@Tag(name = "멤버 컨트롤러")
public class MemberController {
    private final MyPageService myPageService;
    private final RewardService rewardService;
    private final WithdrawnMemberService withdrawnMemberService;
    private final MemberService memberService;

    @GetMapping("profile/{memberId}")
    @Operation(
            summary = "멤버의 프로필 기본 정보",
            description = "이미지 URL, 닉네임, 한줄 소개")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MemberInfoDTO.class)))
    @Parameter(name = "memberId", description = "멤버 아이디")
    public ResponseEntity<?> getProfileInfo(@PathVariable Long memberId) {
        return ResponseEntity.ok(myPageService.findProfileById(memberId));
    }

    @GetMapping("totalReward/{memberId}")
    @Operation(
            summary = "멤버의 누적 리워드 정보",
            description = "")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = TotalRewardDTO.class)))
    @Parameter(name = "memberId", description = "멤버 아이디")
    public ResponseEntity<?> getTotalReward(@PathVariable Long memberId) {
        return ResponseEntity.ok(rewardService.findTotalRewardById(memberId));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdrawMember(@AuthenticatedMember AuthMemberPayload payload) {
        withdrawnMemberService.withdrawMember(payload.id());
        return ResponseEntity.ok("탈퇴가 완료되었습니다.");
    }

    @GetMapping("/{memberId}/colors")
    public MemberPurchasedColorsResponseDTO getMemberColors(@PathVariable Long memberId) {
        return memberService.getPurchasedColors(memberId);
    }

    @PostMapping("/colors/{colorCodeId}/equip")
    public EquipColorResponseDTO equipColor(
            @PathVariable(name = "colorCodeId") Long colorCodeId,
            @AuthenticatedMember AuthMemberPayload payload) {
        return memberService.equipColor(payload.id(), colorCodeId);
    }
}
