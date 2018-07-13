package com.hexagon.boot.libs.id;

import com.hexagon.boot.libs.id.snowflake.TimeServiceRuntimeException;

@FunctionalInterface
public interface ID {
    long next() throws TimeServiceRuntimeException;
}