package lk.rc.aws.awsinstagramclone.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FeedRequestBean {
    private String caption;
    private String postImage;
    private String postImageName;
    private String likeCount;

    private int postId;
    private String comment;
}
