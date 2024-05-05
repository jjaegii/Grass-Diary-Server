package chzzk.grassdiary.web.controller.test;

import chzzk.grassdiary.storage.test.ImageTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/storage")
public class StorageTestController {
    private final ImageTestService imageTestService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void uploadImage(@RequestParam("file") MultipartFile testImage) {
        imageTestService.uploadImage(testImage);
    }
}
