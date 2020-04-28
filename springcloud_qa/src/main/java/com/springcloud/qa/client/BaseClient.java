package com.springcloud.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("springcloud-base")
public interface BaseClient {

    @GetMapping(value = "/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);


}
