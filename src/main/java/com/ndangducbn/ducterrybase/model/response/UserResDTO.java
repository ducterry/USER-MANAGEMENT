package com.ndangducbn.ducterrybase.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResDTO {
    private int id;

    private String email;

    private String phone;

    private String name;

    private String avatar;

    private Date birthday;

    private String role;

    private String token;
}
