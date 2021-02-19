package lk.rc.aws.awsinstagramclone.api.dao;

import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileDetailRepository extends JpaRepository<ProfileDetails, Integer> {

    public ProfileDetails getProfileDetailsByUser(User user);

    public ProfileDetails getProfileDetailsByProfileId(int profileId);

    public List<ProfileDetails> getProfileDetailsByFullNameContainingAndProfileIdNot(String keyword, int profileId);

    public List<ProfileDetails> getAllProfileDetailsByProfileIdNot(int myProfileId, Pageable pageable);

    public List<ProfileDetails> getAllProfileDetailsByProfileIdNotIn(List<Integer> myProfileId, Pageable pageable);
}
