package com.ndangducbn.ducterrybase.service;

import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.model.request.UpdateUserRequest;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    ResponseObject<UserResDTO> detail(Integer userId);

    ResponseObject<UserResDTO> updateUser(Integer id, UpdateUserRequest request);

    ResponseObject<String> deleteUser(Integer id);

    ResponseObject<List<UserResDTO>> listAllUser();
}
