package com.romel.auth.error;

import lombok.Getter;

import static com.romel.auth.error.RomelAuthExceptionHandler.DEFAULT_MESSAGE;
import static com.romel.auth.error.RomelAuthExceptionHandler.DEFAULT_SERVER_MSG;
import static com.romel.auth.error.RomelAuthExceptionHandler.DEFAULT_TITLE;

@Getter
public class BaseRomelException extends RuntimeException {

    private final String title;
    private final String detailMessage;

    public BaseRomelException(String title, String message) {
        super(DEFAULT_SERVER_MSG);
        this.title = title;
        this.detailMessage = message;
    }

    public BaseRomelException(String title, String message, String serverMessage) {
        super(serverMessage);
        this.title = title;
        this.detailMessage = message;
    }

    public BaseRomelException(String serverMessage) {
        super(serverMessage);
        this.title = DEFAULT_TITLE;
        this.detailMessage = DEFAULT_MESSAGE;
    }
}
