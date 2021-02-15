package lk.rc.aws.awsinstagramclone.api.service.impl;

import lk.rc.aws.awsinstagramclone.api.dao.PostCommentRepository;
import lk.rc.aws.awsinstagramclone.api.dao.PostLikeRepository;
import lk.rc.aws.awsinstagramclone.api.dao.PostRepository;
import lk.rc.aws.awsinstagramclone.api.dto.FeedRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.FeedResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.PostService;
import lk.rc.aws.awsinstagramclone.model.Post;
import lk.rc.aws.awsinstagramclone.model.PostComment;
import lk.rc.aws.awsinstagramclone.model.PostLike;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Override
    @Transactional
    public FeedResponseBean createPost(ProfileDetails profileDetails, FeedRequestBean feedRequestBean) throws Exception {
        FeedResponseBean responseBean = new FeedResponseBean();

        Post post = new Post();
        post.setProfileId(profileDetails);
        post.setCaption(feedRequestBean.getCaption());
        post.setImageUrl(feedRequestBean.getImageUrl());
        post.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        post.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
        post.setStatus("PBL");

        post = postRepository.save(post);

        responseBean.setPostId(post.getPostId());
        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");
        return responseBean;
    }

    @Override
    @Transactional
    public FeedResponseBean incrementLikeCount(ProfileDetails userProfile, int postId) {
        FeedResponseBean responseBean = new FeedResponseBean();

        PostLike postLike = new PostLike();
        Post post = postRepository.getPostByPostId(postId);
        postLike.setProfileId(userProfile);
        postLike.setPostId(post);
        postLike.setCreatedTime(new Timestamp(System.currentTimeMillis()));

        postLikeRepository.save(postLike);

        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");
        return responseBean;
    }

    @Override
    @Transactional
    public FeedResponseBean addComment(ProfileDetails userProfile, FeedRequestBean feedRequestBean) throws Exception {
        FeedResponseBean responseBean = new FeedResponseBean();

        Post post = postRepository.getPostByPostId(feedRequestBean.getPostId());

        PostComment comment = new PostComment();
        comment.setPostId(post);
        comment.setProfileId(userProfile);
        comment.setComment(feedRequestBean.getComment());
        comment.setCreatedTime(new Timestamp(System.currentTimeMillis()));

        postCommentRepository.save(comment);

        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");
        return responseBean;
    }

}
