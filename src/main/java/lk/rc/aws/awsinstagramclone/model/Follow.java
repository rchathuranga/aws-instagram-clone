package lk.rc.aws.awsinstagramclone.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Follow {
    private int followId;
    private ProfileDetails profileId;
    private ProfileDetails followingProfileId;
    private Timestamp followedDate;
    private Timestamp unfollowedDate;
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    public int getFollowId() {
        return followId;
    }
    public void setFollowId(int followId) {
        this.followId = followId;
    }

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    public ProfileDetails getProfileId() {
        return profileId;
    }
    public void setProfileId(ProfileDetails profileId) {
        this.profileId = profileId;
    }

    @ManyToOne
    @JoinColumn(name = "following_profile_id", referencedColumnName = "profile_id")
    public ProfileDetails getFollowingProfileId() {
        return followingProfileId;
    }
    public void setFollowingProfileId(ProfileDetails followingProfileId) {
        this.followingProfileId = followingProfileId;
    }

    @Basic
    @Column(name = "followed_date")
    public Timestamp getFollowedDate() {
        return followedDate;
    }
    public void setFollowedDate(Timestamp followedDate) {
        this.followedDate = followedDate;
    }

    @Basic
    @Column(name = "unfollowed_date")
    public Timestamp getUnfollowedDate() {
        return unfollowedDate;
    }
    public void setUnfollowedDate(Timestamp unfollowedDate) {
        this.unfollowedDate = unfollowedDate;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return followId == follow.followId &&
                Objects.equals(followedDate, follow.followedDate) &&
                Objects.equals(unfollowedDate, follow.unfollowedDate) &&
                Objects.equals(status, follow.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followId, followedDate, unfollowedDate, status);
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followId=" + followId +
                ", profileId=" + profileId +
                ", followingProfileId=" + followingProfileId +
                ", followedDate=" + followedDate +
                ", unfollowedDate=" + unfollowedDate +
                ", status='" + status + '\'' +
                '}';
    }
}
