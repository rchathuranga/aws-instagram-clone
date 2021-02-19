package lk.rc.aws.awsinstagramclone.api.service;

import lk.rc.aws.awsinstagramclone.api.dto.ProfileRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileResponseBean;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;

public interface ProfileService {

    public ProfileResponseBean getProfileDetails(int userId) throws Exception;

    public ProfileResponseBean updateProfileDetails(ProfileRequestBean profileRequestBean) throws Exception;

    public ProfileResponseBean updateProfilePicture(ProfileDetails userProfile, ProfileRequestBean profileRequestBean) throws Exception;

    public ProfileResponseBean searchProfile(ProfileDetails profileDetails, String keyword) throws Exception;
}
