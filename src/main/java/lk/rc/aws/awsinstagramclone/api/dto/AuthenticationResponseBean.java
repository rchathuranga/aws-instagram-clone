package lk.rc.aws.awsinstagramclone.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponseBean extends AbstractResponseBean{
    private int userId;
}
