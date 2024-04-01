package uttugseuja.lucklotteryserver.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uttugseuja.lucklotteryserver.domain.image.exception.*;
import uttugseuja.lucklotteryserver.domain.image.presentation.dto.UploadImageResponse;
import uttugseuja.lucklotteryserver.global.utils.security.SecurityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageService implements ImageUtils{

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base-url}")
    private String baseUrl;

    private final AmazonS3 amazonS3;

    public UploadImageResponse uploadImage(MultipartFile file) {
        String url = upload(file);
        return new UploadImageResponse(url);
    }

    public String upload(MultipartFile file) {
        if (file.isEmpty() && file.getOriginalFilename() != null)
            throw FileEmptyException.EXCEPTION;

        if(file.getSize() / (1024 * 1024) > 10){
            throw FileOversizeException.EXCEPTION;
        }
        String originalFilename = file.getOriginalFilename();
        log.info("=====================originalFilename===========================");
        log.info("originalFilename ={}",originalFilename);

        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        log.info("=====================ext===========================");
        log.info("ext ={}",ext);

        if (!(ext.equals("jpg")
                || ext.equals("HEIC")
                || ext.equals("jpeg")
                || ext.equals("png")
                || ext.equals("heic"))) {
            throw BadFileExtensionException.EXCEPTION;
        }

        String randomName = UUID.randomUUID().toString();

        String fileName = SecurityUtils.getCurrentUserId() + "|" + randomName + "." + ext;

        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentType(file.getContentType());
            objMeta.setContentLength(bytes.length);
            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, file.getInputStream(), objMeta)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw FileUploadFailException.EXCEPTION;
        }

        return baseUrl + "/" + fileName;
    }

    @Override
    public void delete(String profilePath) {
        String objectName = getBucketKey(profilePath);
        amazonS3.deleteObject(bucket, objectName);
    }

    public String getBucketKey(String profilePath){

        try {
            URI uri = new URI(profilePath);
            String path = uri.getPath();
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            return java.net.URLDecoder.decode(idStr, StandardCharsets.UTF_8.name());
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw BadProfilePathException.EXCEPTION;
        }

    }
}
