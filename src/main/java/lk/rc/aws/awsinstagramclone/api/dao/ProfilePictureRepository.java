package lk.rc.aws.awsinstagramclone.api.dao;

import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.model.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer> {

    @Modifying
    @Query("update ProfilePicture pp set pp.status=:status where pp.profileId=:profileId")
    public int updateProfilePictureStatusByProfileDetailId(@Param("status") String status,@Param("profileId") ProfileDetails profileId);

    public ProfilePicture getProfilePictureByProfileIdAndStatus(ProfileDetails profile, String status);

}
