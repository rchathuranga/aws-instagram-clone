package lk.rc.aws.awsinstagramclone.api.controller;

import lk.rc.aws.awsinstagramclone.api.dto.FollowResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.FollowService;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/follow")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FollowController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private FollowService followService;

    @PostMapping("/{followProfileId}")
    public ResponseEntity<FollowResponseBean> followProfile(@PathVariable("followProfileId") int followProfileId){
        FollowResponseBean responseBean = new FollowResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = followService.followProfile(userProfile, followProfileId);

        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }

        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<FollowResponseBean> getFollowers(){
        FollowResponseBean responseBean = new FollowResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = followService.getFollowers(userProfile);

        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/followings")
    public ResponseEntity<FollowResponseBean> getFollowings(){
        FollowResponseBean responseBean = new FollowResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = followService.getFollowings(userProfile);

        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

}
