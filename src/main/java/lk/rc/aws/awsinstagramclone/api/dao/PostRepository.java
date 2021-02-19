package lk.rc.aws.awsinstagramclone.api.dao;

import lk.rc.aws.awsinstagramclone.model.Post;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    public Post getPostByPostId(int postId);

    public List<Post> getAllPostsByProfileIdOrderByCreatedTimeDesc(ProfileDetails profileId);

    public List<Post> getAllPostsByProfileIdInOrderByCreatedTimeDesc(List<ProfileDetails> followingList);


}
