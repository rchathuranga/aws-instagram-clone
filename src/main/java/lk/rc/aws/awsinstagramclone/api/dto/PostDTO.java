package lk.rc.aws.awsinstagramclone.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    public int postId;
    public ProfileDTO profileDTO;
    public String caption;
    public String imageUrl;
    public int likeCount;
    public List<ProfileDTO> likedByProfiles;
    public List<CommentDTO> comments;
    public String createdTime;
}
