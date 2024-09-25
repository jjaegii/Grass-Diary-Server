package chzzk.grassdiary.domain.color.service;

import chzzk.grassdiary.domain.color.dto.ColorCodeResponseDTO;
import chzzk.grassdiary.domain.color.entity.ColorCode;
import chzzk.grassdiary.domain.color.entity.ColorCodeDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColorCodeService {

    private final ColorCodeDAO colorCodeDAO;

    @Transactional(readOnly = true)
    public List<ColorCodeResponseDTO> getAllColors() {
        List<ColorCode> colorCodes = colorCodeDAO.findAll();
        return colorCodes.stream()
                .map(ColorCodeResponseDTO::from)
                .collect(Collectors.toList());
    }
}