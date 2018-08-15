package com.hexagon.boot.adapter.rest.api.feedback;

import com.hexagon.boot.domain.feedback.model.FeedbackAppService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.hexagon.boot.domain.feedback.model.FeedBackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * rest 端口适配器
 */
@RestController
public class FeedbackApi {

    @Autowired
    private FeedbackAppService feedbackAppService;

    @PostMapping("/v1/report/error")
    @HystrixCommand(fallbackMethod = "defaultError")
    public FeedBackEntity feedback(@RequestBody FeedBackEntity feedBack, HttpServletRequest request) {
        feedBack.setReferer(request.getHeader("referer"));
        return feedbackAppService.save(feedBack);
    }

    @GetMapping("/v1/report/error")
    public Iterable<FeedBackEntity> feedbackList() {
        return feedbackAppService.findAll();
    }

    /**
     * Fallback method
     *
     * @return
     */
    public FeedBackEntity defaultError(FeedBackEntity feedBack) {
        return feedBack;
    }
}
