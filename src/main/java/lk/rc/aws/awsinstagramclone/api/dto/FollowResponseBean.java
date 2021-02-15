package lk.rc.aws.awsinstagramclone.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowResponseBean extends AbstractResponseBean{
    private List<ProfileDTO> followersList;
    private List<ProfileDTO> followingList;
}
