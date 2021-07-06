package com.ndangducbn.ducterrybase.helper;

import com.ndangducbn.ducterrybase.Repository.UserRepository;
import com.ndangducbn.ducterrybase.entity.User;
import com.ndangducbn.ducterrybase.enums.EUserResponse;
import com.ndangducbn.ducterrybase.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserValidation {
    private final String PREFIX = "UserValidation_";

    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //===============================================================================================//
    public User detailUserValid(Integer userId){
        Optional<User> userCurrent = this.userRepository.findUserById(userId);
        if (!userCurrent.isPresent()){
            throw new UserException(EUserResponse.USER_NOT_FOUND);
        }
        return userCurrent.get();
    }

}
