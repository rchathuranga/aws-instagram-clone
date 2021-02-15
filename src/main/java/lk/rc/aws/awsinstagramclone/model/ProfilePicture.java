package lk.rc.aws.awsinstagramclone.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profile_picture", schema = "aws_instagram_clone", catalog = "")
public class ProfilePicture {
    private int profilePictureId;
    private ProfileDetails profileId;
    private Post post;
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_picture_id")
    public int getProfilePictureId() {
        return profilePictureId;
    }
    public void setProfilePictureId(int profilePictureId) {
        this.profilePictureId = profilePictureId;
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
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
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
        ProfilePicture that = (ProfilePicture) o;
        return profilePictureId == that.profilePictureId &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profilePictureId, status);
    }
}
