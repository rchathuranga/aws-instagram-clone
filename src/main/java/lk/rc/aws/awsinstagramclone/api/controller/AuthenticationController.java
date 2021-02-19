package lk.rc.aws.awsinstagramclone.api.controller;

import lk.rc.aws.awsinstagramclone.api.dto.AuthenticationRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.AuthenticationResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.AuthenticationService;
import lk.rc.aws.awsinstagramclone.model.User;
import lk.rc.aws.awsinstagramclone.util.JwtTokenUtil;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseBean> signIn(@RequestBody AuthenticationRequestBean authenticationRequestBean) {
        AuthenticationResponseBean responseBean = new AuthenticationResponseBean();
        try {
            authenticate(authenticationRequestBean.getUsername(), authenticationRequestBean.getPassword());
            User user = authenticationService.getUserDetailsByUsername(authenticationRequestBean.getUsername());
            responseBean.setUserId(user.getUserId());
            String token = jwtTokenUtil.generateToken(user);

            responseBean.setToken(token);
            responseBean.setResponseCode(ResponseCode.SUCCESS);
            responseBean.setResponseMsg("");
            return ResponseEntity.ok().body(responseBean);
        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Invalid Credentials");
            return ResponseEntity.ok(responseBean);
        }
    }

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseBean> signUp(@RequestBody AuthenticationRequestBean authenticationRequestBean) {
        AuthenticationResponseBean responseBean = new AuthenticationResponseBean();


        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            responseBean = authenticationService.createUser(authenticationRequestBean);
            User user = authenticationService.getUserDetailsByUsername(authenticationRequestBean.getUsername());
            responseBean.setUserId(user.getUserId());
            String token = jwtTokenUtil.generateToken(user);

            responseHeaders.set("token",token);
        } catch (Exception e) {
            responseBean.setResponseCode(ResponseCode.FAILED);
            responseBean.setResponseMsg("Server Error");
        }

        return ResponseEntity.ok().headers(responseHeaders).body(responseBean);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
