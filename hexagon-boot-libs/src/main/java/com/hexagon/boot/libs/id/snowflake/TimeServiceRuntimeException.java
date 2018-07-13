package com.hexagon.boot.libs.id.snowflake;

public class TimeServiceRuntimeException extends RuntimeException {
    public TimeServiceRuntimeException(String format) {
        super(format);
    }
}
