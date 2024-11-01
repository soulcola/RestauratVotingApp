package com.petrtitov.votingApp.error;

import static com.petrtitov.votingApp.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}