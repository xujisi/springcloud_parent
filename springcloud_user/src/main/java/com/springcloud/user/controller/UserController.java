package com.springcloud.user.controller;

import com.springcloud.user.pojo.User;
import com.springcloud.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(tags = "用户模块接口", description = "用户模块接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 更新好友粉丝数和用户关注数
     *
     * @param
     * @return void
     * @author: 许集思
     * @date: 2020/5/1 20:33
     **/
    @ApiOperation(value = "更新好友粉丝数和用户关注数", notes = "更新好友粉丝数和用户关注数")
    @PutMapping("/{userId}/{friendId}/{x}")
    public void updateFansAndFollowCount(@PathVariable String userId, @PathVariable String friendId, @PathVariable int x) {
        userService.updateFansAndFollowCount(x, userId, friendId);
    }

    /**
     * 用户登录
     *
     * @param user,@param response
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:06
     **/
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletResponse response) {
        User userLogin = userService.login(user.getMobile(), user.getPassword());
        if (userLogin == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }
        //使得前后端可以通话的操作,采用jwt
        String token = jwtUtil.createJWT(userLogin.getId(), userLogin.getMobile(), "user");
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("role", "user");
        map.put("userName",user.getMobile());
        //放进Cookies
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        return new Result(true, StatusCode.OK, "登录成功", map);
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:06
     **/
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 注册User用户
     *
     * @param code,@param user
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:06
     **/
    @ApiOperation(value = "注册User用户", notes = "注册User用户")
    @PostMapping("/register/{code}")
    public Result regist(@PathVariable String code, @RequestBody User user) {
        //得到缓存中的验证码
        String checkCodeRedis = (String) redisTemplate.opsForValue().get("checkCode_" + user.getMobile());
        if (checkCodeRedis.isEmpty()) {
            return new Result(false, StatusCode.ERROR, "您的验证码已经过期，请重试");
        }
        if (!checkCodeRedis.equals(code)) {
            return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
        }
        userService.add(user);
        return new Result(true, StatusCode.OK, "注册成功");
    }


    /**
     * 查询全部用户
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:06
     **/
    @ApiOperation(value = "查询全部用户", notes = "查询全部用户")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询User用户
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:07
     **/
    @ApiOperation(value = "根据ID查询User用户", notes = "根据ID查询User用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询User用户
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:07
     **/
    @ApiOperation(value = "分页+多条件查询User用户", notes = "分页+多条件查询User用户")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询User用户
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:07
     **/
    @ApiOperation(value = "根据条件查询User用户", notes = "根据条件查询User用户")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }


    /**
     * 修改User用户
     *
     * @param user,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:08
     **/
    @ApiOperation(value = "修改User用户", notes = "修改User用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除User用户(必须有管理员权限才可以删除)
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:08
     **/
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
