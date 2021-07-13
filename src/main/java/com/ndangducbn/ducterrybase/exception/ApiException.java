package com.ndangducbn.ducterrybase.exception;


import com.ndangducbn.ducterrybase.common.constant.define.ResponseStatus;
import lombok.Data;

public class ApiException extends RuntimeException {
    private final ResponseStatus errorStatus;
    private  int code;

    public ApiException(ResponseStatus errorStatus, String msg) {
        super(msg);
        this.errorStatus = errorStatus;
    }

    public ApiException(ResponseStatus errorStatus) {
        super(errorStatus.getMessage());
        this.code = errorStatus.getCode();
        this.errorStatus = errorStatus;
    }

    public ResponseStatus getErrorStatus() {
        return errorStatus;
    }
}
