package lk.rc.aws.awsinstagramclone.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {
    private int postId;
    private String caption;
    private String imageUrl;
    private ProfileDetails profileId;
    private Timestamp createdTime;
    private Timestamp lastUpdatedTime;
    private String status;
    private List<PostLike> postLikes;
    private List<PostComment> postComments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "caption")
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Basic
    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    public ProfileDetails getProfileId() {
        return profileId;
    }
    public void setProfileId(ProfileDetails profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return postId == post.postId &&
                Objects.equals(caption, post.caption) &&
                Objects.equals(imageUrl, post.imageUrl);
    }


    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY)
    public List<PostLike> getPostLikes() {
        return postLikes;
    }
    public void setPostLikes(List<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY)
    public List<PostComment> getPostComments() {
        return postComments;
    }
    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, caption, imageUrl);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", caption='" + caption + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", profileId=" + profileId +
                '}';
    }

    @Basic
    @Column(name = "created_time")
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_updated_time")
    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
