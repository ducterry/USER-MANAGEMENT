package com.ndangducbn.ducterrybase.common.constant.define;

public enum ResponseStatus {
    DO_SERVICE_SUCCESSFUL(1000, "Thành công"),
    UNHANDLED_ERROR(1004, "Lỗi hệ thống. Vui lòng thử lại")
    ;
    public int code;
    public String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
