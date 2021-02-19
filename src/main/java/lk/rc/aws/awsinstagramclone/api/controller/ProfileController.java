package lk.rc.aws.awsinstagramclone.api.controller;

import lk.rc.aws.awsinstagramclone.api.dto.ProfileRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.ProfileResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.ProfileService;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfileController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileResponseBean> updateProfileDetails(@RequestBody ProfileRequestBean profileRequestBean){
        ProfileResponseBean responseBean = new ProfileResponseBean();

        try{
            responseBean = profileService.updateProfileDetails(profileRequestBean);
        }catch (Exception e){
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }

        return new ResponseEntity<>(responseBean,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ProfileResponseBean> getProfileDetails(){
        ProfileResponseBean responseBean = new ProfileResponseBean();
        try {

            Integer userId = (Integer) httpServletRequest.getAttribute("userId");
            responseBean = profileService.getProfileDetails(userId);

        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error : "+ e.getMessage());
        }
        return new ResponseEntity<>(responseBean,HttpStatus.OK);
    }

    @PostMapping("/update-picture")
    public ResponseEntity<ProfileResponseBean> updateProfilePicture(@RequestBody ProfileRequestBean profileRequestBean){
        ProfileResponseBean responseBean = new ProfileResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = profileService.updateProfilePicture(userProfile, profileRequestBean);

        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }

        return new ResponseEntity<>(responseBean,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ProfileResponseBean> searchProfiles(@RequestParam(value = "keyword", required = false) String keyword){
        ProfileResponseBean responseBean = new ProfileResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = profileService.searchProfile(userProfile, keyword);

        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }
        return new ResponseEntity<>(responseBean,HttpStatus.OK);
    }
}
