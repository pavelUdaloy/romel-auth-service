package com.romel.auth.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Primary
@RestControllerAdvice
public class RomelAuthExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DEFAULT_SERVER_MSG = "ERROR EMAE HAXPAH";
    public static final String DEFAULT_TITLE = "Sorry";
    public static final String DEFAULT_MESSAGE = "Internal server error";

    @ExceptionHandler(BaseRomelException.class)
    public ResponseEntity<Object> handleBasePathologyAdminException(BaseRomelException exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String title = exception.getTitle();
        String message = exception.getDetailMessage();
        String serverMessage = exception.getMessage();

        return fillResponseEntity(title, message, serverMessage, status);
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<Object> handleNotFoundEntityException(NotFoundEntityException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String title = exception.getTitle();
        String errorCode = exception.getDetailMessage();
        String serverMessage = exception.getMessage();

        return fillResponseEntity(title, errorCode, serverMessage, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Throwable throwable) {
        return fillResponseEntity(DEFAULT_TITLE, DEFAULT_MESSAGE, DEFAULT_SERVER_MSG + throwable.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        return fillResponseEntity(DEFAULT_TITLE, exception.getLocalizedMessage(), DEFAULT_SERVER_MSG, HttpStatus.UNAUTHORIZED);
    }

    private static ResponseEntity<Object> fillResponseEntity(String title, String message, String serverMessage, HttpStatus status) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setTitle(title);
        errorDto.setMessage(message);
        log.error("Title \"{}\", Message: \"{}\", ServerMessage \"{}\", Status \"{}\"", title, message, serverMessage, status);
        return ResponseEntity.status(status).body(errorDto);
    }
}
