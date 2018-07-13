package com.hexagon.boot.libs.id.snowflake;

import com.hexagon.boot.libs.id.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * @author three
 *
 * |..........................................|。。。。。|。。。。。|。。。。。。。。。。。。|
 * | 42                                       |     3   |   7     |     12              |
 * | 时间戳(毫秒)                              | 数据中心 | 工作机器 |   序号               |
 * | 139 year                                 | 8个     | 128 个   |   4096 / 毫秒         |
 */
public class SnowflakeID implements ID {

    private TimeService timeService;
    private static final Logger log = LoggerFactory.getLogger(SnowflakeID.class);

    public synchronized long next() {
        long timestamp = timeService.time();

        if (timestamp < lastTimestamp) {
            log.warn("clock is moving backwards. Rejecting requests until "+lastTimestamp);
            throw new TimeServiceRuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;

    // 系统开始时间
    private static final long twepoch;

    private static final long datacenterIdBits = 3L;
    private static final long workerIdBits = 7L;
    private static final long maxWorkerId = ~(-1L << workerIdBits);
    private static final long maxDatacenterId = ~(-1L << datacenterIdBits);
    private static final long sequenceBits = 12L;

    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private static final long sequenceMask = ~(-1L << sequenceBits);

    static {
        // 2018-01-01 00:00:00.000
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JANUARY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        twepoch = calendar.getTimeInMillis();
    }

    private long lastTimestamp = -1L;

    SnowflakeID(long workerId, long datacenterId) {

        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can‘t be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can‘t be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public SnowflakeID(WorkIDGetter workIDGetter, TimeService timeService) throws Exception {
        this(workIDGetter.getWorkId(), workIDGetter.getDataCenterId());
        this.timeService = timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeService.time();
        while (timestamp <= lastTimestamp) {
            timestamp = timeService.time();
        }
        return timestamp;
    }
}
