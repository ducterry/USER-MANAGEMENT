package com.ndangducbn.ducterrybase.service.impl;

import com.ndangducbn.ducterrybase.Repository.UserRepository;
import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.common.constant.define.ResponseStatus;
import com.ndangducbn.ducterrybase.entity.User;
import com.ndangducbn.ducterrybase.helper.Convert2DTO;
import com.ndangducbn.ducterrybase.helper.UserValidation;
import com.ndangducbn.ducterrybase.model.request.UpdateUserRequest;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import com.ndangducbn.ducterrybase.service.UserService;
import com.ndangducbn.ducterrybase.utils.JSONFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final String PREFIX = "UserServiceImpl_";

    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final Convert2DTO convert2DTO;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserValidation userValidation, Convert2DTO convert2DTO, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.convert2DTO = convert2DTO;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseObject<UserResDTO> detail(Integer userId) {
        try {
            log.error(PREFIX + "userSignup =>{}", JSONFactory.toString(userId));

            // 01. Validation Request
            User userCurrent = this.userValidation.detailUserValid(userId);


            // 04. Mapping DTO
            UserResDTO res = this.modelMapper.map(userCurrent, UserResDTO.class);

            return new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL, res);
        } catch (Exception e) {
            log.error(PREFIX + "userSignup Exception =>{}", JSONFactory.toString(e.getMessage()));
            return new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR, e.getMessage());
        }
    }

    @Override
    public ResponseObject<UserResDTO> updateUser(Integer id, UpdateUserRequest request) {
        try {
            log.error(PREFIX + "updateUser =>{}", JSONFactory.toString(request));

            // 01. Validation Request
            User userCurrent = this.userValidation.detailUserValid(id);

            // 02. Mapping to Entity
            User userUpdated = request.updateToEntity(userCurrent);

            // 03. Save Database
            User userSaved = this.userRepository.save(userCurrent);

            // 04. Mapping DTO
            UserResDTO res = this.modelMapper.map(userSaved, UserResDTO.class);

            return new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL, res);
        } catch (Exception e) {
            log.error(PREFIX + "userSignup Exception =>{}", JSONFactory.toString(e.getMessage()));
            return new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR, e.getMessage());
        }
    }

    @Override
    public ResponseObject<String> deleteUser(Integer id) {
        try {
            log.error(PREFIX + "deleteUser =>{}", JSONFactory.toString(id));

            // 01. Validation Request
            User userCurrent = this.userValidation.detailUserValid(id);
            
            // 02. Xóa Database
            this.userRepository.delete(userCurrent);

            return new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL, "Xóa User thành công");
        } catch (Exception e) {
            log.error(PREFIX + "userSignup Exception =>{}", JSONFactory.toString(e.getMessage()));
            return new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR, e.getMessage());
        }
    }

    @Override
    public ResponseObject<List<UserResDTO>> listAllUser() {
        try {
            log.error(PREFIX + "listAllUser =>");

            // 01. Validation Request
            List<User> userList = this.userRepository.findAll();

            // 02. Mapping DTO
            List<UserResDTO> resDTOS = this.convert2DTO.convertLstEntityToLstDTO(userList);

            return new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL, resDTOS);
        } catch (Exception e) {
            log.error(PREFIX + "userSignup Exception =>{}", JSONFactory.toString(e.getMessage()));
            return new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR, e.getMessage());
        }
    }
}
