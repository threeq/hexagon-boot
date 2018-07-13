package com.hexagon.boot.domain.administrator.model.exception;

import com.hexagon.boot.Code;
import com.hexagon.boot.CodeException;

public class EmailExistException extends CodeException {

    public EmailExistException() {
        super(Code.EMAIL_EXIST);
    }
}
