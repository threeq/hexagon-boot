package com.hexagon.boot.adapter.other.domain;

import com.hexagon.boot.domain.model.FeedBackEntity;

/**
 * 不同领域数据间的转换器
 *
 * 不会暴露给外界
 */
class OtherDomainTranslator {
    public FeedBackEntity translate(Long id, String jsonData) {
        FeedBackEntity entity = new FeedBackEntity();
        entity.setId(id);
        entity.setPageUrl(jsonData);
        entity.setErrorRequestTime(System.currentTimeMillis());

        return entity;
    }
}
