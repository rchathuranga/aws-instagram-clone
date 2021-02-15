package lk.rc.aws.awsinstagramclone.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseBean extends AbstractResponseBean{
    private ProfileDTO profileDTO;
    private String profilePictureUrl;
}
