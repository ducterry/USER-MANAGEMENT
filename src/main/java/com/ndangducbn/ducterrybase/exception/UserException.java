package com.ndangducbn.ducterrybase.exception;

import com.ndangducbn.ducterrybase.common.constant.define.ResponseStatus;
import com.ndangducbn.ducterrybase.enums.EUserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserException extends RuntimeException {
    private int code;

    private ResponseStatus errorStatus;

    private EUserResponse userStatus;

    public UserException(ResponseStatus errorStatus, String msg) {
        super(msg);
        this.errorStatus = errorStatus;
    }

    public UserException(ResponseStatus errorStatus) {
        super(errorStatus.getMessage());
        this.code = errorStatus.getCode();
        this.errorStatus = errorStatus;
    }

    public UserException(EUserResponse userStatus) {
        super(userStatus.getName());
        this.code = errorStatus.getCode();
        this.userStatus = userStatus;
    }

}
