package com.hexagon.boot.libs.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * redis lock 拦截器
 *
 * @Date 16/6/24
 * @User three
 */
@Aspect
@Component
@ConditionalOnProperty(name = "lock.enabled")
public class DistributedLockAspect {
    private final LockExecutorHelper lockExecutorHelper;

    @Autowired
    public DistributedLockAspect(LockExecutorHelper lockExecutorHelper) {
        this.lockExecutorHelper = lockExecutorHelper;
    }

    @Pointcut("@annotation(com.changyi.libs.common.lock.DistributedLock)")
    private void lockPoint(){ /* Pointcut */}

    @Around("lockPoint()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        DistributedLock lockInfo = getRedisLock(pjp);
        String key = getLockKey(pjp, lockInfo);

        return lockExecutorHelper.distributedLockExecutor(key, lockInfo.keepMills())
                .execute(executor -> pjp.proceed());

    }

    private String getLockKey(ProceedingJoinPoint pjp, DistributedLock lockInfo) {
        Object[] args = pjp.getArgs();
        StringBuilder keyBuffer = new StringBuilder();
        keyBuffer.append("LOCK_").append(lockInfo.prefix());
        if(lockInfo.checkArgs()) {
            if(lockInfo.checkArgs()) {
                for(Object arg: args) {
                    keyBuffer.append("_").append(String.valueOf(arg));
                }
            }
        }
        return keyBuffer.toString();
    }

    private DistributedLock getRedisLock(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        String methodName=pjp.getSignature().getName();
        Class<?> classTarget=pjp.getTarget().getClass();
        Class<?>[] par=((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod=classTarget.getMethod(methodName, par);

        return objMethod.getAnnotation(DistributedLock.class);
    }

}
