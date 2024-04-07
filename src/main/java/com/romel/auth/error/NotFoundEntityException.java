package com.romel.auth.error;

public class NotFoundEntityException extends BaseRomelException {

    private static final String DEFAULT_ERROR_MSG = "Couldn't find %s entity with id: %s.";

    public NotFoundEntityException(String entity, Object identifier) {
        super("Not found error", String.format(DEFAULT_ERROR_MSG, entity, identifier));
    }
}
