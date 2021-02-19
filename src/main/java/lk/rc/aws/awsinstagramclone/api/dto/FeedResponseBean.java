package lk.rc.aws.awsinstagramclone.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedResponseBean extends AbstractResponseBean{
    private int postId;
    private List<PostDTO> postList;
}
