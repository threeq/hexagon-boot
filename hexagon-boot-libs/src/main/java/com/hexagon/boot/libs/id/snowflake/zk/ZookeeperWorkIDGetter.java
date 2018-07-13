package com.hexagon.boot.libs.id.snowflake.zk;

import com.hexagon.boot.libs.id.snowflake.WorkIDGetter;
import org.apache.curator.utils.ZookeeperFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class ZookeeperWorkIDGetter implements WorkIDGetter {
    private static final Logger log = LoggerFactory.getLogger(ZookeeperWorkIDGetter.class);

    private int dataCenter;
    private int workId = 0;
    private ZooKeeper zkClient;

    private static final int MAX_ID = 2^8;
    private final String workerIdZkPath;
    private final String nodeInfo;

    public ZookeeperWorkIDGetter(int dataCenter, String nodeInfo,
                                 String zkConnect, ZookeeperFactory zookeeperFactory) throws Exception {
        this.zkClient = zookeeperFactory.newZooKeeper(zkConnect, 10000,
                ignored -> {
                    // ignored
                }, false);
        this.dataCenter = dataCenter;
        this.nodeInfo = nodeInfo;
        this.workerIdZkPath = "/snowflake-servers/dc_" + dataCenter;
    }

    @PreDestroy
    public void destroy() {
        try {
            if(zkClient != null) {
                String path = getWorkIdPath();
                int version = zkClient.exists(path, null).getVersion();
                zkClient.delete(getWorkIdPath(), version);
                zkClient.close();
            }
        } catch (Exception e) {
            log.error("work id destroy fail.", e);
        }

    }

    private String getWorkIdPath() {
        return String.format("%s/%d", workerIdZkPath, workId);
    }

    @Override
    public long getDataCenterId() {
        return dataCenter;
    }

    @Override
    public long getWorkId() throws KeeperException, InterruptedException {

        int triesCount = 1023;

        int tries = 0;
        while (workId <MAX_ID) {
            try {
                log.info("trying to claim workerId {}", workId);

                mkdirs();
                zkClient.create(getWorkIdPath(),
                        nodeInfo.getBytes(), OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                log.info("successfully claimed workerId {}", workId);

                return workId;
            } catch(KeeperException.NodeExistsException e) {

                Stat state = zkClient.exists(getWorkIdPath(), false);
                byte[] data = zkClient.getData(getWorkIdPath(), false, state);

                String workIdInfo = new String(data);
                log.info(String.format("%s/%d 已经存在 %s。当前 workIdInfo : %s",
                        workerIdZkPath, workId, workIdInfo, nodeInfo));
                if(nodeInfo.equals(workIdInfo)) {
                    log.info("successfully claimed exist workerId {}", workId);
                    return workId;
                }

                if (tries < triesCount) {
                    log.debug("Failed to claim worker id. Gonna wait a bit and retry because the node may be from the last time I was running.",e);
                    tries += 1;
                    workId++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        log.warn("workId Thread Interrupted: ", e1);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        throw new ZookeeperWorkIdException("服务数量太多了，请建立新的数据中心");
    }

    private void mkdirs() throws KeeperException, InterruptedException {
        String[] paths = workerIdZkPath.split("/");
        StringBuilder prefix = new StringBuilder("/");
        for(String p: paths) {
            if(p.length()>0) {
                prefix.append(p);
                if(zkClient.exists(prefix.toString(), false) == null) {
                    zkClient.create(prefix.toString(),null, OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    workId = 0;
                }
                prefix.append("/");
            }
        }

    }

    public static class ZookeeperWorkIdException extends RuntimeException {
        ZookeeperWorkIdException(String s) {
            super(s);
        }
    }
}
