package com.nullen.tdengineorm.exception;


/**
 * @author Nullen
 */
public class TdOrmException extends RuntimeException {

    private final Integer code;

    public TdOrmException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public TdOrmException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMsg());
        this.code = exceptionCode.getCode();
    }

}
