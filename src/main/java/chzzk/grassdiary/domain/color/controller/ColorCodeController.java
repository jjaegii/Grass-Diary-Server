package chzzk.grassdiary.domain.color.controller;

import chzzk.grassdiary.domain.color.dto.ColorCodeResponseDTO;
import chzzk.grassdiary.domain.color.service.ColorCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/colors")
@Tag(name = "색상 컨트롤러")
public class ColorCodeController {

    private final ColorCodeService colorCodeService;

    @GetMapping
    public List<ColorCodeResponseDTO> getAllColors() {
        return colorCodeService.getAllColors();
    }
}
