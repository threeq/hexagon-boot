package com.hexagon.boot.libs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @author three
 *
 * 上下文工具类。
 */
public interface ContextHelper {
    /**
     * 获取请求属性
     * @return 所有属性
     */
    static ServletRequestAttributes attributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    /**
     * 获取请求。
     * @return 当前请求对象
     */
    static HttpServletRequest request() {
        return attributes().getRequest();
    }

    /**
     * 请求 id
     * @return 当前请求 id
     */
    static String requestId() {
        return (String) request().getAttribute("RequestId");
    }
}
