package lk.rc.aws.awsinstagramclone.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "post_like", schema = "aws_instagram_clone", catalog = "")
public class PostLike {
    private int postLikeId;
    private Post postId;
    private ProfileDetails profileId;
    private Timestamp createdTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    public int getPostLikeId() {
        return postLikeId;
    }
    public void setPostLikeId(int postLikeId) {
        this.postLikeId = postLikeId;
    }

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    public Post getPostId() {
        return postId;
    }
    public void setPostId(Post postId) {
        this.postId = postId;
    }

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    public ProfileDetails getProfileId() {
        return profileId;
    }
    public void setProfileId(ProfileDetails profileId) {
        this.profileId = profileId;
    }

    @Basic
    @Column(name = "created_time")
    public Timestamp getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostLike postLike = (PostLike) o;
        return postLikeId == postLike.postLikeId &&
                Objects.equals(createdTime, postLike.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postLikeId, createdTime);
    }
}
