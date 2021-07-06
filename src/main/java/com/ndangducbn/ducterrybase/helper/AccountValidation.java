package com.ndangducbn.ducterrybase.helper;

import com.ndangducbn.ducterrybase.Repository.UserRepository;
import com.ndangducbn.ducterrybase.enums.EUserResponse;
import com.ndangducbn.ducterrybase.exception.UserException;
import com.ndangducbn.ducterrybase.model.request.SignInRequest;
import com.ndangducbn.ducterrybase.model.request.SignUpRequest;
import com.ndangducbn.ducterrybase.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountValidation {
    private final String PREFIX = "AccountValidation_";

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AccountValidation(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    //==============================================================================================================//
    public void signUpValid(SignUpRequest request) {
        /*
          .Kiểm tra tồn tại tài khoản UserName?
         */
        Boolean existsByEmail = this.userRepository.existsByEmail(request.getEmail());
        if (existsByEmail) {
            throw new UserException(EUserResponse.USER_EXISTED_BY_EMAIL);
        }

        /*
         . Kiểm tra tồn tại tài khoản với SĐT
         */
        Boolean existsByPhone = this.userRepository.existsByPhone(request.getPhone());
        if (existsByPhone) {
            throw new UserException(EUserResponse.USER_EXISTED_BY_PHONE);
        }
    }

    public String signInValid(SignInRequest request) {
        /*
             . Kiểm tra xác thực UserName/ Password
             . TH ko có Exception => Xác thực thành công
         */
        UsernamePasswordAuthenticationToken userAuthentication =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(userAuthentication);

        /*
             . Set thông tin xác thực vào sercurityContext
         */
        SecurityContextHolder.getContext().setAuthentication(authentication);


        /*
            . Tạo Token từ thông tin xác thực
         */
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        return this.jwtTokenUtil.generateToken(userDetails);
    }
}
