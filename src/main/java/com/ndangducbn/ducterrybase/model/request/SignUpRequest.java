package com.ndangducbn.ducterrybase.model.request;

import com.ndangducbn.ducterrybase.entity.User;
import com.ndangducbn.ducterrybase.utils.DateUtil;
import com.ndangducbn.ducterrybase.utils.UserUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpRequest {
    @NotNull(message = "Full name is required")
    @NotEmpty(message = "Full name is required")
    @ApiModelProperty(
            example = "Duc Terry",
            notes = "Full name cannot be empty",
            required = true
    )
    private String name;

    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email")
    @ApiModelProperty(
            example = "dev.ndangduc.bn@gmail.com",
            notes = "Email cannot be empty",
            required = true
    )
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    @ApiModelProperty(
            example = "abc13579",
            notes = "Password cannot be empty",
            required = true
    )
    private String password;

    @Pattern(regexp = "(09|01[2|6|8|9])+([0-9]{8})\\b", message = "Please provide a valid phone number")
    @Size(min = 10, max = 11)
    @ApiModelProperty(
            example = "0916016972",
            notes = "Phone cannot be empty",
            required = true
    )
    private String phone;


    @ApiModelProperty(
            example = "USER",
            notes = "Role cannot be empty",
            required = true
    )
    private String role;

    public User mapToEntity() {
        User entity = new User();

        entity.setName(this.getName());
        entity.setEmail(this.getEmail());
        entity.setPhone(this.getPhone());
        entity.setRole(this.getRole());
        entity.setPassword(UserUtils.hashPassword(this.getPassword()));
        entity.setCreatedDate(DateUtil.now());
        entity.setModifiedDate(DateUtil.now());
        entity.setCreatedBy("ĐứcNĐ");
        entity.setModifiedBy("ĐứcNĐ");

        return entity;
    }
}
