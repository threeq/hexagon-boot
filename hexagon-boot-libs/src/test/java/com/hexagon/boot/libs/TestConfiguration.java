package com.hexagon.boot.libs;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class TestConfiguration {

    public static List<Thread> createThreads(String groupId, int count, TestExecutor executor) {
        List<Thread> threads = new LinkedList<>();
        int i =0;
        while (i++ < count) {
            int finalI = i;
            threads.add(new Thread(() -> {
                executor.execute(groupId+"_"+ finalI);
            }));
        }
        return threads;
    }

    public static void joinThreads(List<Thread> threads) {
        for(Thread t: threads ) {
            t.start();
        }

        try {
            for(Thread t: threads ) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
