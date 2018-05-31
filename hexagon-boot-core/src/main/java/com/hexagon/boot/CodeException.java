package com.hexagon.boot;

public class CodeException extends Exception {

    private final Code code;

    public CodeException(Code code) {
        super("[" + code.val() + "] "+code.desc());
        this.code = code;
    }

    @Override
    public String toString() {
        return "[" + code.val() + "] "+code.desc();
    }
}
