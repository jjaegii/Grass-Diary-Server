package chzzk.grassdiary.domain.image.controller;

import chzzk.grassdiary.domain.image.service.ImageService;
import chzzk.grassdiary.global.util.file.FileFolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
@Tag(name = "이미지 컨트롤러")
public class ImageController {
    private final ImageService imageService;

    String MAIN_BANNER_URL = "static/images/main_page_exchange_diary.jpg";
    String THEME_SHOP_URL = "static/images/theme_shop.jpg";

    @PostMapping("/diary")
    @Operation(
            summary = "일기 이미지 저장",
            description = "이미지 저장시 사이즈는 KB(킬로바이트) 단위")
    public ResponseEntity<?> uploadDiaryImage(
            @RequestPart MultipartFile image
    ) {
        return ResponseEntity.ok()
                .body(imageService.uploadImage(image, FileFolder.PERSONAL_DIARY));
    }

    private ResponseEntity<?> sendImage(String mainBannerUrl) throws IOException {
        Resource resource = new ClassPathResource(mainBannerUrl);
        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        InputStream inputStream = resource.getInputStream();
        byte[] image = inputStream.readAllBytes();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping("main-banner")
    @Operation(
            summary = "메인 페이지 배너 및 교환 일기장 모달 이미지",
            description = "고슴도치가 일기장을 들고 있는 사진")
    public ResponseEntity<?> mainBanner() throws IOException {
        return sendImage(MAIN_BANNER_URL);
    }

    @GetMapping("theme-shop")
    @Operation(
            summary = "테마 상점 모달 이미지",
            description = "왕관을 쓴 잔디 일기장 사진")
    public ResponseEntity<?> exchangeDiary() throws IOException {
        return sendImage(THEME_SHOP_URL);
    }
}
