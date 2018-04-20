package com.hexagon.boot.domain.model;

import lombok.*;

/**
 * 领域实体
 */
@Setter
@Getter
@NoArgsConstructor
public class FeedBackEntity {
    Long id;

    String referer;
    String appType;
    String userAgent;
    String pageUrl;
    String errorType;
    String errorDesc;
    String errorApi;
    String errorRequest;
    String errorResponse;
    Long   errorRequestTime;

    public FeedBackEntity(FeedBackEntity var1) {
        this.id = var1.getId();
        this.referer = var1.getReferer();
        this.appType = var1.getAppType();
        this.userAgent = var1.getUserAgent();
        this.pageUrl = var1.getPageUrl();
        this.errorType = var1.getErrorType();
        this.errorDesc = var1.getErrorDesc();
        this.errorApi = var1.getErrorApi();
        this.errorRequest = var1.getErrorRequest();
        this.errorResponse = var1.getErrorResponse();
        this.errorRequestTime = var1.getErrorRequestTime();
    }

//    private FeedBackRepository repository;
//    public void setRepository(FeedBackRepository repository) {
//        this.repository = repository;
//    }
//
//    public void save() {
//        repository.save(this);
//    }

}
