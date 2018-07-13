package com.hexagon.boot.domain.administrator.model.exception;

import com.hexagon.boot.Code;
import com.hexagon.boot.CodeException;

public class NameOrPwdErrorException extends CodeException {
    public NameOrPwdErrorException() {
        super(Code.NAME_OR_PWD_ERROR);
    }
}
