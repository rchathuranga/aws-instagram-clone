package lk.rc.aws.awsinstagramclone.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

@Component
public class S3FileUploader {

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    // @Async annotation ensures that the method is executed in a different background thread
    // but not consume the main thread.
    @Async
    public String uploadFile(final String base64Image, final String fileName) {
        System.out.println("File upload in progress.");
        String url = null;
        try {
            final File file = convertBase64toFile(base64Image,fileName);
            url = uploadFileToS3Bucket(bucketName, file);
            System.out.println("File upload is completed.");
            file.delete();  // To remove the file locally created in the project folder.
        } catch (final AmazonServiceException ex) {
            System.out.println("File upload is failed.");
            System.out.println("Error= {} while uploading file."+ ex.getMessage());
        }
        return url;
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            System.out.println("Error converting the multi-part file to file= "+ ex.getMessage());
        }
        return file;
    }

    private String uploadFileToS3Bucket(final String bucketName, final File file) {
        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        System.out.println("Uploading file with name= " + uniqueFileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);

        String resourceUrl = ((AmazonS3Client) amazonS3).getResourceUrl(bucketName, uniqueFileName);
        return resourceUrl;
    }

    public File convertBase64toFile(String apiImage, String fileName) {
        String fileType = getFileType(apiImage);
        String substring = apiImage.substring(23);
        byte[] decodedImg = Base64.getDecoder().decode(substring.getBytes(StandardCharsets.UTF_8));
        String imageName = fileName+"."+fileType;
        Path destinationFile = Paths.get("", imageName);
        try {
            Files.write(destinationFile, decodedImg);
        } catch (IOException e) {
            System.out.println("Error converting the base64 to file= "+ e.getMessage());
        }
        return new File(imageName);
    }

    public String getFileType(String encodedImage){
        int indexOfColen = encodedImage.indexOf(":");
        int indexOfSemiColen = encodedImage.indexOf(";");
        String fileType = encodedImage.substring(indexOfColen+1, indexOfSemiColen);
        return fileType.replace("image/", "");
    }

}
