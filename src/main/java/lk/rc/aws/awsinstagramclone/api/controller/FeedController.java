package lk.rc.aws.awsinstagramclone.api.controller;

import lk.rc.aws.awsinstagramclone.api.dto.FeedRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.FeedResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.FeedService;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.util.JwtTokenUtil;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/feed")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeedController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private FeedService feedService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/time-line")
    public ResponseEntity<FeedResponseBean> getMyTimeLine() {
        FeedResponseBean responseBean = new FeedResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = feedService.getTimeline(userProfile);

        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<FeedResponseBean> getMyFeed(){
        FeedResponseBean responseBean = new FeedResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = feedService.getFeed(userProfile);

        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
