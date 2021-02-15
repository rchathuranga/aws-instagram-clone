package lk.rc.aws.awsinstagramclone.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "post_comment", schema = "aws_instagram_clone", catalog = "")
public class PostComment {
    private int postCommentId;
    private Post postId;
    private String comment;
    private ProfileDetails profileId;
    private Timestamp createdTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id")
    public int getPostCommentId() {
        return postCommentId;
    }
    public void setPostCommentId(int postCommentId) {
        this.postCommentId = postCommentId;
    }

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    public Post getPostId() {
        return postId;
    }
    public void setPostId(Post postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
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
        PostComment that = (PostComment) o;
        return postCommentId == that.postCommentId &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postCommentId, comment, createdTime);
    }

    @Override
    public String toString() {
        return "PostComment{" +
                "postCommentId=" + postCommentId +
                ", postId=" + postId +
                ", comment='" + comment + '\'' +
                ", profileId=" + profileId +
                ", createdTime=" + createdTime +
                '}';
    }
}
