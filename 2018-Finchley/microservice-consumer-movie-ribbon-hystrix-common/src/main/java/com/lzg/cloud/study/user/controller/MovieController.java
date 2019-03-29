package com.lzg.cloud.study.user.controller;

import com.lzg.cloud.study.user.entity.User;
import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RequestMapping("/movies")
@RestController
@Slf4j
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "findByIdFallback")
    @GetMapping("/users/{id}")
    public User findById(@PathVariable Long id) {
        //hystrix.command.default.circuitBreaker.requestVolumeThreshold
        //hystrix.command.default.metrics.rollingStats.timeInMilliseconds
//        HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(5);

        System.out.println("---->> MovieController >> findById()");
        // 这里用到了RestTemplate的占位符能力
        User user = this.restTemplate.getForObject(
                "http://microservice-provider-user/users/{id}",
                User.class,
                id
        );
        // ...电影微服务的业务...
        return user;
    }

    public User findByIdFallback(Long id, Throwable throwable) {
        System.out.println("---->> MovieController >> 进入回退方法");
        log.error("进入回退方法", throwable);
        return new User(id, "默认用户", "默认用户", 0, new BigDecimal(1));
    }
}