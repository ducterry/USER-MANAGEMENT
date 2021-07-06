package com.ndangducbn.ducterrybase.controller;

import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.model.request.SignInRequest;
import com.ndangducbn.ducterrybase.model.request.SignUpRequest;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import com.ndangducbn.ducterrybase.service.AccountService;
import com.ndangducbn.ducterrybase.utils.JSONFactory;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/account")
@Api(tags = "02. Account Controller", description = "Account API")
@Slf4j
public class AccountController {
    private final String PREFIX = "UserServiceImpl_";

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create user", response = UserResDTO.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Email already exists in the system"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @PostMapping("/v1/signup")
    public ResponseEntity<ResponseObject<UserResDTO>> userSignup(@Valid
                                                                 @RequestBody
                                                                 @ApiParam(value = "Tạo tài khoản User mới", required = true) SignUpRequest request) {
        log.debug(PREFIX + "userSignup => {}", JSONFactory.toString(request));

        return new ResponseEntity<>(this.accountService.userSignup(request), HttpStatus.OK);
    }

    @ApiOperation(value = "User Login", response = UserResDTO.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Email already exists in the system"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @PostMapping("/v1/signin")
    public ResponseEntity<ResponseObject<UserResDTO>> userSignIn(@Valid
                                                                 @RequestBody
                                                                 @ApiParam(value = "Đăng nhập tài khoản", required = true) SignInRequest request) {
        log.debug(PREFIX + "userSignup => {}", JSONFactory.toString(request));

        return new ResponseEntity<>(this.accountService.userSignIn(request), HttpStatus.OK);
    }

}
