package com.ndangducbn.ducterrybase.helper;

import com.ndangducbn.ducterrybase.entity.User;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Convert2DTO {
    private final String PREFIX = "Convert2DTO_";
    private final ModelMapper modelMapper;

    public Convert2DTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<UserResDTO> convertLstEntityToLstDTO(List<User> userList) {
        log.debug(PREFIX + "convertLstEntityToLstDTO => ");
        List<UserResDTO> resDTOS = new ArrayList<>();

        if (!userList.isEmpty()) {
            userList.forEach(item -> {
                resDTOS.add(this.convertEntityToDTO(item));
            });
        }
        return  resDTOS;
    }

    private UserResDTO convertEntityToDTO(User item) {
        return this.modelMapper.map(item, UserResDTO.class);
    }
}
