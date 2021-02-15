package lk.rc.aws.awsinstagramclone.api.service;

import lk.rc.aws.awsinstagramclone.api.dto.FeedRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.FeedResponseBean;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;

public interface PostService {
    public FeedResponseBean createPost(ProfileDetails userProfile, FeedRequestBean feedRequestBean) throws Exception;
    public FeedResponseBean incrementLikeCount(ProfileDetails userProfile, int postId) throws Exception;
    public FeedResponseBean addComment(ProfileDetails userProfile, FeedRequestBean feedRequestBean) throws Exception;
}
