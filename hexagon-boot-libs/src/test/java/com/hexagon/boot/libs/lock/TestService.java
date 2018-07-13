package com.hexagon.boot.libs.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author three
 */
@Component
public class TestService {
    static Logger logger = LoggerFactory.getLogger(TestService.class);

    private StringBuffer shareData = new StringBuffer();

    public void task(String taskId) {
        int index = 0;
        int count = 10;
        logger.info("sync execute [" + taskId + "] start.");
        shareData.append("<");
        while (index++ < count) {

            logger.info("sync execute: " + taskId + " -> " + index);
            shareData.append(index);
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        shareData.append(">");
        logger.info("sync execute [" + taskId + "] finished.");
    }

    public String getShareData() {
        return shareData.toString();
    }

    public void clearShareData() {
        shareData.delete(0, shareData.length());
    }
}
