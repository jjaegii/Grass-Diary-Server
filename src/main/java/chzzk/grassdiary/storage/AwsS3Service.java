package chzzk.grassdiary.storage;

import chzzk.grassdiary.exception.file.ImageRoadFailedException;
import chzzk.grassdiary.utils.file.FileNameUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AwsS3Service implements StorageService {
    private final AwsProperties awsProperties;
    private AmazonS3 s3Client;

    @PostConstruct
    private void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(
                awsProperties.getAccessKey(), awsProperties.getSecretKey()
        );

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsProperties.getRegionStatic())
                .build();
    }

    public String uploadBucket(MultipartFile file) {
        return upload(file, awsProperties.getBucket());
    }

    @Override
    public String upload(MultipartFile file, String bucket) {
        String fileName = file.getOriginalFilename();
        String convertedFileName = FileNameUtils.fileNameConvert(fileName);

        try {
            String mimeType = new Tika().detect(file.getInputStream());
            ObjectMetadata metadata = new ObjectMetadata();

            FileNameUtils.checkImageMimeType(mimeType);
            metadata.setContentType(mimeType);
            s3Client.putObject(
                    new PutObjectRequest(
                            bucket,
                            convertedFileName,
                            file.getInputStream(), metadata
                    )
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new ImageRoadFailedException();
        }

        return s3Client.getUrl(bucket, convertedFileName).toString();
    }
}
