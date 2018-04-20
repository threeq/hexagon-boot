package com.hexagon.boot.adapter.other.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 外部领域调用 http 方式实现
 *
 * 不会暴露给外界
 */
@Service
class OtherDomainHttpFacade {
    String getById(Long id) {
        return UUID.randomUUID().toString();
    }
}
