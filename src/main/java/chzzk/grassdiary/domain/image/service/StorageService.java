package chzzk.grassdiary.domain.image.service;

import chzzk.grassdiary.global.util.file.FileFolder;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadImage(MultipartFile file, String destLocation, FileFolder fileFolder);
    void deleteImage(String fileName);
}
