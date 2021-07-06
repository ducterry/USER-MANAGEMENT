package com.ndangducbn.ducterrybase.controller;

import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.model.request.UpdateUserRequest;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import com.ndangducbn.ducterrybase.service.UserService;
import com.ndangducbn.ducterrybase.utils.JSONFactory;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/")
@Api(tags = "03. User Controller", description = "User API")
@Slf4j
public class UserController {
    private final String PREFIX = "UserServiceImpl_";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Get user info by id", response = UserResDTO.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "No user found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<UserResDTO>> detailUser(@PathVariable
                                                                 @ApiParam(value = "Mã ID User", example = "1")
                                                                         Integer id) {
        log.debug(PREFIX + "detailUser => {}", JSONFactory.toString(id));

        return new ResponseEntity<>(this.userService.detail(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Update user", response = UserResDTO.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "No user found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<UserResDTO>> updateUser(@PathVariable
                                        @ApiParam(value = "Mã ID User", example = "1")
                                                Integer id,
                                        @Valid @RequestBody
                                        @ApiParam(value = "Cập nhật tài khoản User mới", required = true)
                                                UpdateUserRequest request) {
        log.debug(PREFIX + "updateUser => {}", JSONFactory.toString(request));

        return new ResponseEntity<>(this.userService.updateUser(id, request), HttpStatus.OK);
    }


    @ApiOperation(value = "Delete user by id", response = String.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "No user found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<String>> deleteUser(@PathVariable
                                            @ApiParam(value = "Mã ID User", example = "1")
                                                    Integer id) {
        return new ResponseEntity<>(this.userService.deleteUser(id), HttpStatus.OK);
    }
}
