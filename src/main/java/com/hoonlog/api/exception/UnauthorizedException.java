package com.hoonlog.api.exception;

/**
 * status 401
 */
public class UnauthorizedException extends PostException {

    private static final String MESSAGE = "인증이 필요합니다.";
    private static final int STATUS = 401;

    public UnauthorizedException() {
        super(MESSAGE);
    }

    public UnauthorizedException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return STATUS;
    }
}
