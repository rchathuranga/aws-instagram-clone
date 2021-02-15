package lk.rc.aws.awsinstagramclone.api.service;

import lk.rc.aws.awsinstagramclone.api.dto.FeedResponseBean;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;

public interface FeedService {
    public FeedResponseBean getTimeline(ProfileDetails userProfile) throws Exception;
    public FeedResponseBean getFeed(ProfileDetails userProfile) throws Exception;
}
