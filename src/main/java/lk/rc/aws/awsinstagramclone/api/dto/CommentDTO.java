package lk.rc.aws.awsinstagramclone.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private int postCommentId;
    private String comment;
    private String createdDate;
    private ProfileDTO commentedProfile;
}
