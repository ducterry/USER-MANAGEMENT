package com.ndangducbn.ducterrybase.exception;

import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.common.constant.define.ResponseStatus;
import com.ndangducbn.ducterrybase.utils.JSONFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class CustomExceptionGlobal {
    private final String PREFIX = "CustomExceptionGlobal_";

    /*
        API Exception
    */
    @ExceptionHandler
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
    @ResponseBody
    ResponseObject<String> apiExceptionHandle(ApiException e) {
        log.error(PREFIX + "apiExceptionHandle => {}", JSONFactory.toString(e));

        return new ResponseObject<>(false, e.getErrorStatus(),e.getMessage());
    }

    /*
        User Exception
     */
    @ExceptionHandler(UserException.class)
    @ResponseBody
    ResponseEntity<?> userExceptionHandler(UserException e) {
        log.error(PREFIX + "apiExceptionHandle => {}", JSONFactory.toString(e));

        ResponseObject<String> res = new ResponseObject<>(false, e.getCode(), e.getMessage());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /*
        Common Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<?> unhandledError(Exception e) {
        log.error(PREFIX + "unhandledError => {}", JSONFactory.toString(e));

        ResponseObject<String> res = new ResponseObject<>(false, ResponseStatus.UNHANDLED_ERROR.getCode(), e.getMessage());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
