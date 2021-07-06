package com.ndangducbn.ducterrybase.service.impl;

import com.ndangducbn.ducterrybase.Repository.UserRepository;
import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.common.constant.define.ResponseStatus;
import com.ndangducbn.ducterrybase.entity.User;
import com.ndangducbn.ducterrybase.enums.EUserResponse;
import com.ndangducbn.ducterrybase.exception.UserException;
import com.ndangducbn.ducterrybase.helper.AccountValidation;
import com.ndangducbn.ducterrybase.model.request.SignInRequest;
import com.ndangducbn.ducterrybase.model.request.SignUpRequest;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import com.ndangducbn.ducterrybase.service.AccountService;
import com.ndangducbn.ducterrybase.utils.JSONFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final String PREFIX = "UserServiceImpl_";

    private final UserRepository userRepository;
    private final AccountValidation accountValidation;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(UserRepository userRepository, AccountValidation accountValidation, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.accountValidation = accountValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseObject<UserResDTO> userSignup(SignUpRequest request) {
        try {
            log.error(PREFIX + "userSignup =>{}", JSONFactory.toString(request));

            // 01. Validation Request
            this.accountValidation.signUpValid(request);

            // 02. Mapping to Entity
            User entity = request.mapToEntity();

            // 03. Save Entity
            User userSaved = this.userRepository.save(entity);

            // 04. Mapping DTO
            UserResDTO res = this.modelMapper.map(userSaved, UserResDTO.class);

            return new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL, res);
        } catch (Exception e) {
            log.error(PREFIX + "userSignup Exception =>{}", JSONFactory.toString(request));
            return new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR, e.getMessage());
        }
    }

    @Override
    public ResponseObject<UserResDTO> userSignIn(SignInRequest request) {
        try {
            log.error(PREFIX + "userSignIn =>{}", JSONFactory.toString(request));

            // 01. Validation Request
            String token = this.accountValidation.signInValid(request);

            // 03. Save Entity
            User userCurrent = this.userRepository.findUserByEmail(request.getEmail());
            if (userCurrent == null) {
                throw new UserException(EUserResponse.USER_NOT_FOUND);
            }

            // 04. Mapping DTO
            UserResDTO res = this.modelMapper.map(userCurrent, UserResDTO.class);
            res.setToken(token);

            return new ResponseObject<>(true, ResponseStatus.DO_SERVICE_SUCCESSFUL, res);
        } catch (Exception e) {
            log.error(PREFIX + "userSignIn Exception =>{}", JSONFactory.toString(request));
            return new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR, e.getMessage());
        }
    }
}
