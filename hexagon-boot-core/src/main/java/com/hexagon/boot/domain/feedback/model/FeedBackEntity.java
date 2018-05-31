package com.hexagon.boot.domain.feedback.model;

import com.hexagon.boot.domain.BaseEntity;
import lombok.*;

/**
 * 领域实体
 */
@Setter
@Getter
@NoArgsConstructor
public class FeedBackEntity extends BaseEntity {

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
        super(var1);
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
//    public void register() {
//        repository.register(this);
//    }

}
