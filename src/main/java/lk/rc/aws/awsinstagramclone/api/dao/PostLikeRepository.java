package lk.rc.aws.awsinstagramclone.api.dao;

import lk.rc.aws.awsinstagramclone.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
}
