package chzzk.grassdiary.domain.member.controller;

import chzzk.grassdiary.domain.member.dto.MemberPurchasedColorResponseDTO;
import chzzk.grassdiary.domain.member.service.MemberPurchasedColorService;
import chzzk.grassdiary.global.auth.common.AuthenticatedMember;
import chzzk.grassdiary.global.auth.service.dto.AuthMemberPayload;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store/color")
@Tag(name = "유저색상 컨트롤러")
public class MemberPurchasedColorController {
    private final MemberPurchasedColorService memberPurchasedColorService;

    @PostMapping("/{colorCodeId}")
    public MemberPurchasedColorResponseDTO purchaseColor(
            @PathVariable(name = "colorCodeId") Long colorCodeId,
            @AuthenticatedMember AuthMemberPayload payload
    ){
        return memberPurchasedColorService.purchaseColor(colorCodeId, payload.id());
    }
}
