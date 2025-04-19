package stdev.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileStore {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName; // 버킷 이름 설정

    @Value("${cloud.aws.region.static}")
    private String bucketRegion; // 지역 설정



    private final S3Client s3Client; // AmazonS3Client 대신 S3Client 사용

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        log.info("크하하하하하");

        // 원본 파일 이름 가져오기
        String originalFilename = multipartFile.getOriginalFilename(); // 예: image.png

        // 저장할 파일 이름 생성
        String storeFileName = createStoreFileName(originalFilename); // 고유 파일 이름 생성

        // S3에 파일 업로드 (AWS SDK v2 방식)
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(storeFileName)
                .contentType(multipartFile.getContentType())
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

        // 업로드된 파일의 URL 생성
        String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,               // S3 버킷 이름
                bucketRegion,             // S3 리전
                storeFileName             // 저장된 파일 이름
        );

        return fileUrl;
    }

    private static String createStoreFileName(String originalFilename) {
        String ext = extractedExt(originalFilename);
        // 서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();
        //asd3f143as5d4f5.png

        String storeFileName = uuid + "." + ext;

        return storeFileName;
    }

    private static String extractedExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1); //png반환
        return ext;
    }
}