package chzzk.grassdiary.domain.color.entity;

import chzzk.grassdiary.domain.color.entity.ColorCode;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorCodeDAO extends JpaRepository<ColorCode, Long> {
    Optional<ColorCode> findByColorName(String colorName);
}
