package lk.rc.aws.awsinstagramclone.api.dao;

import lk.rc.aws.awsinstagramclone.model.Follow;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    //Followings
    public List<Follow> getAllFollowsByProfileId(ProfileDetails profileId);

    //Followings
    @Query("SELECT f.followingProfileId from Follow f where f.profileId.profileId= ?1")
    public List<ProfileDetails> getProfileWhichFollowedByMe(int profileId);

    //Followers
    public List<Follow> getAllFollowsByFollowingProfileId(ProfileDetails profileId);

    //Followers
    @Query("SELECT f.profileId from Follow f where f.followingProfileId.profileId= ?1")
    public List<ProfileDetails> getProfileWhichFollowMe(int profileId);

}
