package com.lzg.cloud.study.user.feign;

import com.lzg.cloud.study.user.entity.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "microservice-provider-user", fallback = UserFeignClientFallbackbackFactory.class)
public interface UserFeignClient {
    @GetMapping("/users/{id}")
    User findById(@PathVariable("id") Long id);
}


//@Component
//class UserFeignClientFallback implements UserFeignClient {
//    @Override
//    public User findById(Long id) {
//        return new User(id, "默认用户", "默认用户", 0, new BigDecimal(1));
//    }
//}

@Component
@Slf4j
class UserFeignClientFallbackbackFactory implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public User findById(Long id) {
                System.out.println("---->> UserFeicgnClientFallbackbackFactory -- 进入回退逻辑");
                log.error("进入回退逻辑", throwable);
                return new User(id, "默认用户", "默认用户", 0, new BigDecimal(1));
            }
        };
    }
}