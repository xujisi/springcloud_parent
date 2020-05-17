package com.springcloud.qa.client;

import com.springcloud.qa.client.impl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * 标识如果出错则往熔断器自定义Impl调（fallback)
 *
 * @author: 许集思
 * @date: 2020/5/1 23:44
 */

@FeignClient(value = "springcloud-base", fallback = BaseClientImpl.class)
public interface BaseClient {

    @GetMapping(value = "/label/{labelId}")
    Result findById(@PathVariable("labelId") String labelId);


}
