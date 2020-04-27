package com.springcloud.sms.listener;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RabbitListener(queues = "sms")
public class SmsListener {


    @RabbitHandler
    /**
     * @Title: 短信发送方法（暂时不写）：见控制台
     * @MethodName: executeSms
     * @Param: * @param map
     * @Return void
     * @Exception
     * @author: 许集思
     * @date: 2020/4/25 12:38
     */
    public void executeSms(Map<String, String> map) {
        System.out.println("手机号" + map.get("mobile"));
        System.out.println("验证码" + map.get("checkCode"));
        //阿里云先忽略（有钱再写）
    }
}
