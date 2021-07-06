package com.ndangducbn.ducterrybase.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInRequest {
    @NotNull(message = "Email is required")
    @Email(message = "Please provide a valid email")
    @ApiModelProperty(
            example = "dev.ndangduc.bn@gmail.com",
            notes = "Email cannot be empty",
            required = true
    )
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    @ApiModelProperty(
            example = "abc13579",
            notes = "Password cannot be empty",
            required = true
    )
    private String password;
}
