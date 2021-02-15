package lk.rc.aws.awsinstagramclone.api.dao;

import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDetailRepository extends JpaRepository<ProfileDetails, Integer> {

    public ProfileDetails getProfileDetailsByUser(User user);

    public ProfileDetails getProfileDetailsByProfileId(int profileId);

}
