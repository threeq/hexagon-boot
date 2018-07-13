package com.hexagon.boot.libs.id.snowflake;

/**
 * @author three
 */
public class SingleWorkIdGetter implements WorkIDGetter {

    @Override
    public long getDataCenterId() {
        return 0;
    }

    @Override
    public long getWorkId() {
        return 0;
    }
}
