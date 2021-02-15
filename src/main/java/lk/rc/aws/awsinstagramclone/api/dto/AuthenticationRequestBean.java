package lk.rc.aws.awsinstagramclone.api.dto;

import lombok.Data;

@Data
public class AuthenticationRequestBean {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String mobileNo;
}
