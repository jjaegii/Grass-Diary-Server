package chzzk.grassdiary.storage;

import chzzk.grassdiary.utils.file.FileFolder;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadImage(MultipartFile file, String destLocation, FileFolder fileFolder);
    void deleteImage(String fileName);
}
