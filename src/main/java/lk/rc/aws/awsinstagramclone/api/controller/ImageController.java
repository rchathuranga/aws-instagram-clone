package lk.rc.aws.awsinstagramclone.api.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @PostMapping
    public void fileUpload(@RequestBody ImageRequestBean imageRequestBean) {
        System.out.println("GET IMAGE METHOD BEGINS");
        try {

            convertBase64toFile(imageRequestBean.getImage(), imageRequestBean.getImageFileName());

        } catch (IOException e) {
            System.out.println("GET IMAGE METHOD ERROR");
            e.printStackTrace();
        }
        System.out.println("GET IMAGE METHOD ENDS");
    }


/*    public String saveBase64File(String image) throws IOException {
        String fileUploadDir = null;
        String fileDownloadDir = null;

        String[] imageParts = new String[2];
        if (image == null) return null;
        if (image.startsWith("data:")) {
            imageParts = image.split(",");
        } else {
            isFullDetailsBase64 = false;
            imageParts[1] = image;
        }


        byte[] data = DatatypeConverter.parseBase64Binary(imageParts[1]);
        String extension = isFullDetailsBase64 ? getExtension(image) : getImageType(data);
        String fileName = UUID.randomUUID().toString() + extension;

        Path path = Paths.get(fileUploadDir, fileName);
        File file = new File(path.toString());

        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        outputStream.write(data);
        outputStream.close();

        return fileDownloadDir + fileName;
    }*/

    public File convertBase64toFile(String apiImage, String fileName) throws IOException {
        String fileTpye = getFileType(apiImage);
        String substring = apiImage.substring(23);
        byte[] decodedImg = Base64.getDecoder().decode(substring.getBytes(StandardCharsets.UTF_8));
        String imageName = fileName+"."+fileTpye;
        Path destinationFile = Paths.get("", imageName);
        Files.write(destinationFile, decodedImg);
        return new File(imageName);
    }

    public String getFileType(String encodedImage){
        int indexOfColen = encodedImage.indexOf(":");
        int indexOfSemiColen = encodedImage.indexOf(";");
        String fileTpye = encodedImage.substring(indexOfColen+1, indexOfSemiColen);
        return fileTpye.replace("image/", "");
    }

}
