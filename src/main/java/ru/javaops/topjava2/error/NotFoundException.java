package ru.javaops.topjava2.error;

import static ru.javaops.topjava2.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}