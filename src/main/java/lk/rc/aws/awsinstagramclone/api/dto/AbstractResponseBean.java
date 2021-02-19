package lk.rc.aws.awsinstagramclone.api.dto;

import lombok.Data;

@Data
abstract class AbstractResponseBean {
    private int responseCode;
    private String responseMsg;
    private String token;
}
