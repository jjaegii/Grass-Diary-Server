package chzzk.grassdiary.storage.test;

import chzzk.grassdiary.storage.AwsS3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageTestService {

    private final AwsS3Service awsS3Service;

    public void uploadImage(MultipartFile testFile) {
        if (!testFile.isEmpty()) {
            String s3FilePath = awsS3Service.uploadBucket(testFile);
            // 이후에 이미지 url을 db에 저장해줘야함.
        }
    }
}
