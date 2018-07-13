package com.hexagon.boot.libs.id.snowflake;

/**
 * @author three
 */
public class DefaultTimeService implements TimeService {
    public long time() {
        return System.currentTimeMillis();
    }
}
