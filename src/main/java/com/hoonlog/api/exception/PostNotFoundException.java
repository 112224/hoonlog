package com.hoonlog.api.exception;

/**
 * status 404
 */
public class PostNotFoundException extends PostException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    private static final int STATUS = 404;

    public PostNotFoundException() {
        super(MESSAGE);
    }

    public PostNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return STATUS;
    }
}
