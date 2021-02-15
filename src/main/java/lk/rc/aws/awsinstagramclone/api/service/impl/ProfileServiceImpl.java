package lk.rc.aws.awsinstagramclone.api.service.impl;

import lk.rc.aws.awsinstagramclone.api.dao.PostRepository;
import lk.rc.aws.awsinstagramclone.api.dao.ProfileDetailRepository;
import lk.rc.aws.awsinstagramclone.api.dao.ProfilePictureRepository;
import lk.rc.aws.awsinstagramclone.api.dto.PostDTO;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileDTO;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.ProfileService;
import lk.rc.aws.awsinstagramclone.config.AWSConfig;
import lk.rc.aws.awsinstagramclone.model.Post;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.model.ProfilePicture;
import lk.rc.aws.awsinstagramclone.model.User;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import lk.rc.aws.awsinstagramclone.util.S3FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

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

    @Override
    public ProfileResponseBean getProfileDetails(int userId) throws Exception {
        ProfileResponseBean responseBean = new ProfileResponseBean();

        User user = new User();
        user.setUserId(userId);

        ProfileDetails profileDetails = profileDetailRepository.getProfileDetailsByUser(user);

        ProfilePicture picture = profilePictureRepository.getProfilePictureByProfileIdAndStatus(profileDetails, "ACT");

        ProfileDTO dto = new ProfileDTO();
        dto.setProfileId(profileDetails.getProfileId());
        dto.setFirstName(profileDetails.getFirstName());
        dto.setLastName(profileDetails.getLastName());
        dto.setFullName(profileDetails.getFullName());
        dto.setDateOfBirth(profileDetails.getDateOfBirth());
        dto.setStatus(profileDetails.getStatus());

        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(picture.getPost().getPostId());
        postDTO.setCaption(picture.getPost().getCaption());
        postDTO.setImageUrl(picture.getPost().getImageUrl());
        dto.setProfilePicture(postDTO);

        responseBean.setProfileDTO(dto);

        return responseBean;
    }

    @Override
    public ProfileResponseBean updateProfileDetails(ProfileRequestBean profileRequestBean) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public ProfileResponseBean updateProfilePicture(ProfileDetails userProfile, ProfileRequestBean profileRequestBean) throws Exception {
        ProfileResponseBean responseBean = new ProfileResponseBean();

        int i = profilePictureRepository.updateProfilePictureStatusByProfileDetailId("DACT", userProfile);
        System.out.println("Deactive count : "+ i);

        Post post;
        if (profileRequestBean.getPostId() == 0) {

            post = new Post();
            post.setCaption(profileRequestBean.getCaption());
            post.setProfileId(userProfile);


            String imageUrl = uploadImageFileToS3(profileRequestBean.getProfilePictureUrl());

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
        responseBean.setResponseCode(ResponseCode.SUCCESS);
        responseBean.setResponseMsg("");
        responseBean.setProfilePictureUrl(post.getImageUrl());
        return responseBean;
    }

    private String uploadImageFileToS3(MultipartFile multipartFile) {
        return s3FileUploader.uploadFile(multipartFile);
    }
}
