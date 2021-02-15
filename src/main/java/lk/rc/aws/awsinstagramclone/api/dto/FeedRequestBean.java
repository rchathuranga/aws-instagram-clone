package lk.rc.aws.awsinstagramclone.api.dto;

import lombok.Data;

@Data
public class FeedRequestBean {
    private String caption;
    private String imageUrl;
    private String likeCount;

    private int postId;
    private String comment;
}
