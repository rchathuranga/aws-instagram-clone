package lk.rc.aws.awsinstagramclone.api.service.impl;

import lk.rc.aws.awsinstagramclone.api.dao.FollowRepository;
import lk.rc.aws.awsinstagramclone.api.dao.PostRepository;
import lk.rc.aws.awsinstagramclone.api.dao.ProfilePictureRepository;
import lk.rc.aws.awsinstagramclone.api.dto.CommentDTO;
import lk.rc.aws.awsinstagramclone.api.dto.FeedResponseBean;
import lk.rc.aws.awsinstagramclone.api.dto.PostDTO;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileDTO;
import lk.rc.aws.awsinstagramclone.api.service.FeedService;
import lk.rc.aws.awsinstagramclone.model.Post;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.model.ProfilePicture;
import lk.rc.aws.awsinstagramclone.util.JwtTokenUtil;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Override
    public FeedResponseBean getTimeline(ProfileDetails userProfile) throws Exception {
        FeedResponseBean responseBean = new FeedResponseBean();
        List<PostDTO> postDTOList = new ArrayList<>();

        List<Post> postList = postRepository.getAllPostsByProfileIdOrderByCreatedTimeDesc(userProfile);

        postDTOList = convertPostToDTOs(postList);

        responseBean.setPostList(postDTOList);
        responseBean.setToken(jwtTokenUtil.generateToken(userProfile.getUser()));
        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");
        return responseBean;
    }

    @Override
    public FeedResponseBean getFeed(ProfileDetails userProfile) throws Exception {
        FeedResponseBean responseBean = new FeedResponseBean();
        List<PostDTO> postDTOList = new ArrayList<>();

//        List<Follow> followingList = followRepository.getAllFollowsByProfileId(userProfile);
//        List<ProfileDetails> list = followingList.stream().map(Follow::getFollowingProfileId).collect(Collectors.toList());
        List<ProfileDetails> followings = followRepository.getProfileWhichFollowedByMe(userProfile.getProfileId());
        followings.add(userProfile);
        List<Post> feedPosts = postRepository.getAllPostsByProfileIdInOrderByCreatedTimeDesc(followings);
        postDTOList = convertPostToDTOs(feedPosts);

        responseBean.setPostList(postDTOList);
        responseBean.setToken(jwtTokenUtil.generateToken(userProfile.getUser()));
        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");

        return responseBean;
    }

    private List<PostDTO> convertPostToDTOs(List<Post> postList) {
        List<PostDTO> postDTOList = new ArrayList<>();

        for (Post post : postList) {
            PostDTO postDTO = new PostDTO();

            ProfileDetails prof = post.getProfileId();

            postDTO.setPostId(post.getPostId());
            postDTO.setCaption(post.getCaption());
            postDTO.setImageUrl(post.getImageUrl());
            postDTO.setLikeCount(post.getPostLikes().size());

            ProfilePicture picture = profilePictureRepository.getProfilePictureByProfileIdAndStatus(post.getProfileId(), "ACT");
            ProfileDTO dto1 = new ProfileDTO();
            dto1.setProfileId(prof.getProfileId());
            dto1.setFullName(prof.getFullName());

            if (picture != null) {
                PostDTO profilePicDTO = new PostDTO();
                profilePicDTO.setImageUrl(picture.getPost().getImageUrl());
                profilePicDTO.setCaption(picture.getPost().getImageUrl());
                profilePicDTO.setCreatedTime(picture.getPost().getCreatedTime().toString());

                dto1.setProfilePicture(profilePicDTO);
            }

            postDTO.setProfileDTO(dto1);

            postDTO.setLikedByProfiles(post.getPostLikes().stream().map(postLike -> {
                ProfileDTO dto = new ProfileDTO();
                dto.setProfileId(postLike.getProfileId().getProfileId());
                dto.setFullName(postLike.getProfileId().getFullName());
                return dto;
            }).collect(Collectors.toList()));

            postDTO.setComments(post.getPostComments().stream().map(postComment -> {
                CommentDTO dto = new CommentDTO();
                dto.setPostCommentId(postComment.getPostCommentId());
                dto.setComment(postComment.getComment());
                dto.setCreatedDate(postComment.getCreatedTime().toString());
                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setProfileId(postComment.getProfileId().getProfileId());
                profileDTO.setFullName(postComment.getProfileId().getFullName());
                dto.setCommentedProfile(profileDTO);
                return dto;
            }).collect(Collectors.toList()));

            postDTO.setCreatedTime(post.getCreatedTime().toString());
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

}
