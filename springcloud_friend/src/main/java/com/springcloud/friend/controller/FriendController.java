package com.springcloud.friend.controller;

import com.springcloud.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @PutMapping("/like/{friendId}/{type}")
    public Result addFriend(@PathVariable String friendId, @PathVariable String type) {
        //验证是否登录,并且拿到用户Id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null) {
            //说明当前用户没有USER角色 （表示未登录或者是权限不足）
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //得到当前登录的用户ID
        String userId = claims.getId();
        //判断是添加好友还是添加非好友
        if (type != null) {
            if ("1".equals(type)) {
                //添加好友
                int flag = friendService.addFriend(userId, friendId);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加");
                }
                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            } else if ("2".equals(type)) {
                //添加非好友

            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }

    }

}
