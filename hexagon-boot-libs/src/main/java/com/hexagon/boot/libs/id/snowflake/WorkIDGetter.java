package com.hexagon.boot.libs.id.snowflake;

import org.apache.zookeeper.KeeperException;

/**
 * @author three
 */
public interface WorkIDGetter {
    long getDataCenterId();
    long getWorkId() throws Exception;
}
