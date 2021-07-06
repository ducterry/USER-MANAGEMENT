package com.ndangducbn.ducterrybase.model.request;

import com.ndangducbn.ducterrybase.entity.User;
import com.ndangducbn.ducterrybase.utils.UserUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;

@Getter
public class UpdateUserRequest extends SignUpRequest {
    @Valid
    @URL(regexp = "(https?:\\/\\/.*\\.(?:png|jpg))", message = "Avatar must be an url image")
    @ApiModelProperty(
            example = "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png",
            notes = "Avatar must be an url image",
            required = false
    )
    private String avatar;


    public User updateToEntity(User entity) {
        entity.setRole(this.getRole());
        entity.setPhone(this.getPhone());
        entity.setEmail(this.getEmail());
        entity.setName(this.getName());
        entity.setPassword(UserUtils.hashPassword(this.getPassword()));
        entity.setAvatar(this.getAvatar());
        return entity;
    }
}
