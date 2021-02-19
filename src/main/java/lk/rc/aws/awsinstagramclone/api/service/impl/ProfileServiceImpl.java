package lk.rc.aws.awsinstagramclone.api.service.impl;

import lk.rc.aws.awsinstagramclone.api.dao.FollowRepository;
import lk.rc.aws.awsinstagramclone.api.dao.PostRepository;
import lk.rc.aws.awsinstagramclone.api.dao.ProfileDetailRepository;
import lk.rc.aws.awsinstagramclone.api.dao.ProfilePictureRepository;
import lk.rc.aws.awsinstagramclone.api.dto.PostDTO;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileDTO;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.ProfileService;
import lk.rc.aws.awsinstagramclone.model.Post;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.model.ProfilePicture;
import lk.rc.aws.awsinstagramclone.model.User;
import lk.rc.aws.awsinstagramclone.util.JwtTokenUtil;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import lk.rc.aws.awsinstagramclone.util.S3FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private S3FileUploader s3FileUploader;

    @Autowired
    private ProfileDetailRepository profileDetailRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public ProfileResponseBean getProfileDetails(int userId) throws Exception {
        ProfileResponseBean responseBean = new ProfileResponseBean();

        User user = new User();
        user.setUserId(userId);

        ProfileDetails profileDetails = profileDetailRepository.getProfileDetailsByUser(user);

        ProfilePicture picture = profilePictureRepository.getProfilePictureByProfileIdAndStatus(profileDetails, "ACT");

        if (profileDetails != null) {

            ProfileDTO dto = new ProfileDTO();
            dto.setProfileId(profileDetails.getProfileId());
            dto.setFirstName(profileDetails.getFirstName());
            dto.setLastName(profileDetails.getLastName());
            dto.setFullName(profileDetails.getFullName());
            dto.setDateOfBirth(profileDetails.getDateOfBirth());
            dto.setStatus(profileDetails.getStatus());
            dto.setMobileNo(profileDetails.getUser().getMobileNo());
            dto.setEmail(profileDetails.getUser().getEmailAddress());

            if (picture != null) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostId(picture.getPost().getPostId());
                postDTO.setCaption(picture.getPost().getCaption());
                postDTO.setImageUrl(picture.getPost().getImageUrl());
                dto.setProfilePicture(postDTO);
            }

            responseBean.setProfileDTO(dto);
            responseBean.setToken(jwtTokenUtil.generateToken(profileDetails.getUser()));
            responseBean.setResponseCode(ResponseCode.SUCCESS);
            responseBean.setResponseMsg("");
        } else {

            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("No User Found");
        }

        return responseBean;
    }

    @Override
    public ProfileResponseBean updateProfileDetails(ProfileRequestBean profileRequestBean) throws Exception {
        throw new Exception("Not Implemented the Process");
    }

    @Override
    @Transactional
    public ProfileResponseBean updateProfilePicture(ProfileDetails userProfile, ProfileRequestBean profileRequestBean) throws Exception {
        ProfileResponseBean responseBean = new ProfileResponseBean();

        profilePictureRepository.updateProfilePictureStatusByProfileDetailId("DACT", userProfile);

        Post post;
        if (profileRequestBean.getPostId() == 0) {

            post = new Post();
            post.setCaption(profileRequestBean.getCaption());
            post.setProfileId(userProfile);

            String imageUrl = s3FileUploader.uploadFile(profileRequestBean.getProfileImage(), profileRequestBean.getProfileImageName());
            post.setImageUrl(imageUrl);
            post.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            post.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
            post.setStatus("ACT");

            post = postRepository.save(post);

        }else {
            post = postRepository.getPostByPostId(profileRequestBean.getPostId());
        }

        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setPost(post);
        profilePicture.setProfileId(userProfile);
        profilePicture.setStatus("ACT");

        profilePictureRepository.save(profilePicture);
        responseBean.setToken(jwtTokenUtil.generateToken(userProfile.getUser()));
        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");
        responseBean.setProfilePictureUrl(post.getImageUrl());
        return responseBean;
    }

    @Override
    public ProfileResponseBean searchProfile(ProfileDetails myProfile, String keyword) throws Exception {
        ProfileResponseBean responseBean = new ProfileResponseBean();
        List<ProfileDTO> dtoList = new ArrayList<>();

        List<ProfileDetails> list;
        if (keyword == null) {
            PageRequest pagination = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "profileId"));
            List<ProfileDetails> iFollowing = followRepository.getProfileWhichFollowedByMe(myProfile.getProfileId());
            iFollowing.add(myProfile);
            list = profileDetailRepository.getAllProfileDetailsByProfileIdNotIn(iFollowing.stream().map(ProfileDetails::getProfileId).collect(Collectors.toList()), pagination);
//            list = profileDetailRepository.getAllProfileDetailsByProfileIdNot(myProfile.getProfileId(), pagination);
        } else {
            list = profileDetailRepository.getProfileDetailsByFullNameContainingAndProfileIdNot(keyword, myProfile.getProfileId());
        }

        for (ProfileDetails profileDetails : list) {
            ProfileDTO dto = new ProfileDTO();
            dto.setProfileId(profileDetails.getProfileId());
            dto.setFirstName(profileDetails.getFirstName());
            dto.setLastName(profileDetails.getLastName());
            dto.setFullName(profileDetails.getFullName());
            dto.setDateOfBirth(profileDetails.getDateOfBirth());
            dto.setStatus(profileDetails.getStatus());
            dto.setMobileNo(profileDetails.getUser().getMobileNo());
            dto.setEmail(profileDetails.getUser().getEmailAddress());

            ProfilePicture picture = profilePictureRepository.getProfilePictureByProfileIdAndStatus(profileDetails, "ACT");
            if (picture != null) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostId(picture.getPost().getPostId());
                postDTO.setCaption(picture.getPost().getCaption());
                postDTO.setImageUrl(picture.getPost().getImageUrl());
                postDTO.setCreatedTime(picture.getPost().getCreatedTime().toString());
                dto.setProfilePicture(postDTO);
            }
            dtoList.add(dto);
        }

        responseBean.setToken(jwtTokenUtil.generateToken(myProfile.getUser()));
        responseBean.setSearchResult(dtoList);
        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");
        return responseBean;
    }
}
