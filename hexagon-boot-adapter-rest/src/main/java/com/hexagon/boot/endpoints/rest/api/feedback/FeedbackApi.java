package com.hexagon.boot.endpoints.rest.api.feedback;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.hexagon.boot.domain.model.FeedBackEntity;
import com.hexagon.boot.domain.model.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FeedbackApi {

    @Autowired
    private FeedBackRepository feedBackRepository;

    @PostMapping("/v1/report/error")
    @HystrixCommand(fallbackMethod = "defaultError")
    public FeedBackEntity feedback(@RequestBody FeedBackEntity feedBack, HttpServletRequest request) {
        feedBack.setReferer(request.getHeader("referer"));
        return feedBackRepository.save(feedBack);
    }

    @GetMapping("/v1/report/error")
    public Iterable<FeedBackEntity> feedbackList() {
        return feedBackRepository.findAll();
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
