package com.ndangducbn.ducterrybase.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class ExcelUsersDTO {
    private int id;

    private String email;

    private String phone;

    private String name;

    private String avatar;

    private Date birthday;

    private String role;
}
