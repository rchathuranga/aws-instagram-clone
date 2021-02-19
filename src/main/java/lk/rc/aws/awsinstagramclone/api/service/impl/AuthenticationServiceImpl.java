package lk.rc.aws.awsinstagramclone.api.service.impl;

import lk.rc.aws.awsinstagramclone.api.dao.ProfileDetailRepository;
import lk.rc.aws.awsinstagramclone.api.dao.UserRepository;
import lk.rc.aws.awsinstagramclone.api.dto.AuthenticationRequestBean;
import lk.rc.aws.awsinstagramclone.api.dto.AuthenticationResponseBean;
import lk.rc.aws.awsinstagramclone.api.service.AuthenticationService;
import lk.rc.aws.awsinstagramclone.model.ProfileDetails;
import lk.rc.aws.awsinstagramclone.model.User;
import lk.rc.aws.awsinstagramclone.util.JwtTokenUtil;
import lk.rc.aws.awsinstagramclone.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileDetailRepository profileDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        GrantedAuthority authority = null;
        User apiUser = userRepository.getUserByUsername(s);

        if (apiUser !=null) {
            if (apiUser.getUserRole().equalsIgnoreCase("ADMIN")){
                authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            } else if (apiUser.getUserRole().equalsIgnoreCase("USER")) {
                authority = new SimpleGrantedAuthority("ROLE_USER");
            }
            return new org.springframework.security.core.userdetails.User(apiUser.getUsername(),apiUser.getPassword(), Collections.singleton(authority));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
    }

    @Override
    public User getUserDetailsByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        return user;
    }

    @Override
    @Transactional
    public AuthenticationResponseBean createUser(AuthenticationRequestBean requestBean) {
        AuthenticationResponseBean responseBean = new AuthenticationResponseBean();
        User user = new User();
        user.setUsername(requestBean.getUsername());
        user.setPassword(passwordEncoder.encode(requestBean.getPassword()));
        user.setStatus("ACT");
        user.setEmailAddress(requestBean.getEmail());
        user.setMobileNo(requestBean.getMobileNo());
        user.setUserRole("USER");

        user = userRepository.save(user);

        if (user != null) {

            ProfileDetails profileDetails = new ProfileDetails();
            profileDetails.setFullName(requestBean.getFullName());
            profileDetails.setUser(user);
            profileDetails = profileDetailRepository.save(profileDetails);

            if (profileDetails != null) {

                responseBean.setResponseCode(ResponseCode.SUCCESS);
                responseBean.setResponseMsg("");
                responseBean.setUserId(user.getUserId());
                return responseBean;
            }

        }

        String token = jwtTokenUtil.generateToken(user);
        responseBean.setToken(token);
        responseBean.setResponseCode(ResponseCode.FAILED);
        responseBean.setResponseMsg("Failed to Save User Profile");
        return responseBean;
    }
}
