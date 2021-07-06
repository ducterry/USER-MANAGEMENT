package com.ndangducbn.ducterrybase.enums;

public enum EUserResponse {
    USER_EXISTED_BY_EMAIL(9999, "Email này đã được đăng kí. Vui lòng chọn mail khác"),
    USER_EXISTED_BY_PHONE(9998, "SDT này đã được đăng kí. Vui lòng chọn SDT khác"),
    USER_NOT_FOUND(9997, "Tài khoản không tìm thấy");
    private int code;
    private String name;

    EUserResponse(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
