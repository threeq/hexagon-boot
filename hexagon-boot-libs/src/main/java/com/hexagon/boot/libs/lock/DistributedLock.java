package com.hexagon.boot.libs.lock;

import java.lang.annotation.*;

/**
 * @Date 16/6/24
 * @User three
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DistributedLock {
    /**
     * redis的key
     * @return
     */
    String prefix();

    /**
     * 是否拼接方法参数
     * @return
     */
    boolean checkArgs() default true;
    /**
     * 持锁时间,单位毫秒,默认 10 秒
     */
    long keepMills() default 10000;
    /**
     * 当获取失败时候动作
     */
    LockFailAction action() default LockFailAction.GIVEUP;

    public enum LockFailAction{
        /**
         * 放弃
         */
        GIVEUP,
        /**
         * 继续
         */
        CONTINUE;
    }
    /**
     * 睡眠时间,设置GIVEUP忽略此项
     * @return
     */
    long sleepMills() default 1000;
}
