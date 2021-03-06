package com.hexagon.boot.adapter.repository.feedback;


import com.hexagon.boot.domain.feedback.model.FeedBackEntity;

import javax.persistence.*;

/**
 * 资源库自己存储所需要的的 数据类型
 * 不会暴露到外边
 */
@Entity
class JpaFeedBackEntity extends FeedBackEntity {

    @Override
    public Long getErrorRequestTime() {
        return super.getErrorRequestTime();
    }

    @Override
    public String getReferer() {
        return super.getReferer();
    }

    @Override
    public String getAppType() {
        return super.getAppType();
    }

    @Override
    public String getUserAgent() {
        return super.getUserAgent();
    }

    @Override
    public String getPageUrl() {
        return super.getPageUrl();
    }

    @Override
    public String getErrorType() {
        return super.getErrorType();
    }

    @Override
    public String getErrorDesc() {
        return super.getErrorDesc();
    }

    @Override
    public String getErrorApi() {
        return super.getErrorApi();
    }

    @Override
    public String getErrorRequest() {
        return super.getErrorRequest();
    }

    @Override
    public String getErrorResponse() {
        return super.getErrorResponse();
    }

    public JpaFeedBackEntity() {
        super();
    }

    public JpaFeedBackEntity(FeedBackEntity var1) {
        super(var1);
    }

    @Override
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return super.getId();
    }

    @Override
    public Long getCreated() {
        return super.getCreated();
    }

    @Override
    public Long getUpdated() {
        return super.getUpdated();
    }

    @Override
    public Boolean getActive() {
        return super.getActive();
    }
}
