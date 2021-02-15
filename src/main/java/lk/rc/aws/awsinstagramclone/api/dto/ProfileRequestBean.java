package lk.rc.aws.awsinstagramclone.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileRequestBean {
    private String caption;
    private MultipartFile profilePictureUrl;
    private int postId;
}
