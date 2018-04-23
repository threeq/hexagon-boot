package com.hexagon.boot.adapter.other.domain;

import com.hexagon.boot.domain.model.FeedBackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 外部服务 适配器
 */
@Service
class OtherDomainAdapter {

    @Autowired
    OtherDomainHttpFacade httpFacade;

    public FeedBackEntity getById(Long id) {
        // 远程获取数据操作，可以是 RPC，也可以是 REST
        String data = httpFacade.getById(id);
        return new OtherDomainTranslator().translate(id, data);
    }
}
