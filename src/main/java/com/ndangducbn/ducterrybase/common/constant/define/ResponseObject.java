package com.ndangducbn.ducterrybase.common.constant.define;

import lombok.Data;

@Data
public class ResponseObject<T> {
    private Boolean result;
    private int code;
    private String message;
    private T data;
    private Paging paging;

    public ResponseObject(Boolean result, ResponseStatus status, T data) {
        this.result = result;
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }
    public ResponseObject(Boolean result, ResponseStatus status, String message) {
        this.result = result;
        this.code = status.getCode();
        this.message = message;
    }

    public ResponseObject(Boolean result, int code, String message) {
        this.result = result;
        this.code = code;
        this.message = message;
    }
}
