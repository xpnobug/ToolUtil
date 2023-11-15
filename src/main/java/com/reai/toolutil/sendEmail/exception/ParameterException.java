package com.reai.toolutil.sendEmail.exception;

/**
 * 参数检查异常
 *
 * @author thundzeng
 */
public class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
    }
}
