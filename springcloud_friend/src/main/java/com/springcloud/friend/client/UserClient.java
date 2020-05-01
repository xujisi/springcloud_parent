package com.springcloud.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 调用其他模块的Client类，需要注明调用的模块名字用@FeignClient进行注解
 *
 * @author: 许集思
 * @date: 2020/5/1 20:42
 */

@FeignClient("springcloud-user")
public interface UserClient {

    @PutMapping("/user/{userId}/{friendId}/{x}")
    public void updateFansAndFollowCount(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId, @PathVariable("x") int x);
}
