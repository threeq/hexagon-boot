package com.hexagon.boot.domain.administrator.model.exception;

import com.hexagon.boot.Code;
import com.hexagon.boot.CodeException;

public class MobilePhoneExistException extends CodeException {

    public MobilePhoneExistException() {
        super(Code.MOBILE_PHONE_EXIST);
    }
}
