package lk.rc.aws.awsinstagramclone.api.controller;

import lk.rc.aws.awsinstagramclone.api.dto.FeedRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.FeedResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.PostService;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<FeedResponseBean> createPost(@RequestBody FeedRequestBean feedRequestBean) {
        FeedResponseBean responseBean = new FeedResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = postService.createPost(userProfile, feedRequestBean);

        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }

        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/like/{postId}")
    public ResponseEntity<FeedResponseBean> incrementLikeCount(@PathVariable("postId") int postId) {
        FeedResponseBean responseBean = new FeedResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = postService.incrementLikeCount(userProfile, postId);

        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<FeedResponseBean> addCommentToPost(@RequestBody FeedRequestBean feedRequestBean) {
        FeedResponseBean responseBean = new FeedResponseBean();
        try {

            ProfileDetails userProfile = (ProfileDetails) httpServletRequest.getAttribute("userProfile");
            responseBean = postService.addComment(userProfile, feedRequestBean);

        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

}
