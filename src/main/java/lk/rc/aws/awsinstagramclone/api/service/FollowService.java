package lk.rc.aws.awsinstagramclone.api.service;

import lk.rc.aws.awsinstagramclone.api.dto.FollowRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.FollowResponseBean;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;

public interface FollowService {
    public FollowResponseBean followProfile(ProfileDetails profile, int followProfile) throws Exception;
    public FollowResponseBean getFollowers(ProfileDetails profile) throws Exception;
    public FollowResponseBean getFollowings(ProfileDetails profile) throws Exception;
}
