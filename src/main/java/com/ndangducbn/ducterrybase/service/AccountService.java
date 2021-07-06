package com.ndangducbn.ducterrybase.service;

import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.model.request.SignInRequest;
import com.ndangducbn.ducterrybase.model.request.SignUpRequest;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import org.springframework.stereotype.Component;

@Component
public interface AccountService {
    ResponseObject<UserResDTO> userSignup(SignUpRequest request);

    ResponseObject<UserResDTO> userSignIn(SignInRequest request);
}
