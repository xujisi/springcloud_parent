package com.springcloud.user.controller;

import com.springcloud.user.pojo.Admin;
import com.springcloud.user.service.AdminService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Admin登录控制器
 *
 * @author: 许集思
 * @date: 2020/5/17 11:31
 */

@RestController
@CrossOrigin
@RequestMapping("/admin")
@Api(tags = "管理员登录微服务API")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Admin登录
     *
     * @MethodName: login
     * @Param: * @param admin
     * @Return entity.Result
     * @Exception
     * @author: 许集思
     * @date: 2020/4/25 14:44
     */
    @ApiOperation(value = "Admin登录", notes = "Admin登录")
    @PostMapping(value = "/login")
    public Result login(@RequestBody Admin admin) {
        Admin adminLogin = adminService.login(admin);
        if (adminLogin == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }
        // 使得前后端可以通话的操作,采用jwt
        // 生成令牌
        String token = jwtUtil.createJWT(adminLogin.getId(), adminLogin.getLoginname(), "admin");
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("role", "admin");
        return new Result(true, StatusCode.OK, "登录成功", map);

    }

    /**
     * 查询全部Admin用户
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:02
     **/
    @ApiOperation(value = "查询全部Admin用户", notes = "查询全部Admin用户")
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询Admin用户
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:03
     **/
    @ApiOperation(value = "根据ID查询Admin用户", notes = "根据ID查询Admin用户")
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
    }

    /**
     * 分页+多条件查询Admin用户
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:03
     **/
    @ApiOperation(value = "分页+多条件查询Admin用户", notes = "分页+多条件查询Admin用户")
    @PostMapping(value = "/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询Admin用户
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:03
     **/
    @ApiOperation(value = "根据条件查询Admin用户", notes = "根据条件查询Admin用户")
    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 增加Admin用户
     *
     * @param admin
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:04
     **/
    @ApiOperation(value = "增加Admin用户", notes = "增加Admin用户")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改Admin用户
     *
     * @param admin,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:04
     **/
    @ApiOperation(value = "修改Admin用户", notes = "修改Admin用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除Admin用户
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:04
     **/
    @ApiOperation(value = "删除Admin用户", notes = "删除Admin用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        adminService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
