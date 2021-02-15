package lk.rc.aws.awsinstagramclone.api.service;

import lk.rc.aws.awsinstagramclone.api.dto.AuthenticationRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.AuthenticationResponseBean;
import lk.rc.aws.awsinstagramclone.model.User;

public interface AuthenticationService {
    public User getUserDetailsByUsername(String username) throws Exception;
    public AuthenticationResponseBean createUser(AuthenticationRequestBean requestBean) throws Exception;
}
