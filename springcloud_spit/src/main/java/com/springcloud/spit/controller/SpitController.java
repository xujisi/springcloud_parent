package com.springcloud.spit.controller;


import com.springcloud.spit.pojo.Spit;
import com.springcloud.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
@Api(tags = "吐槽微服务API")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有吐槽
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:59
     **/
    @ApiOperation(value = "查询所有吐槽", notes = "查询所有吐槽")
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据ID查询吐槽
     *
     * @param spitId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:59
     **/
    @ApiOperation(value = "根据ID查询吐槽", notes = "根据ID查询吐槽")
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 新增吐槽
     *
     * @param spit
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:59
     **/
    @ApiOperation(value = "新增吐槽", notes = "新增吐槽")
    @PostMapping
    public Result save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 修改吐槽
     *
     * @param spit,@param spitId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:59
     **/
    @ApiOperation(value = "修改吐槽", notes = "修改吐槽")
    @PutMapping("/{spitId}")
    public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
        spit.set_id(spitId);
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除吐槽
     *
     * @param spitId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:59
     **/
    @ApiOperation(value = "删除吐槽", notes = "删除吐槽")
    @DeleteMapping("/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查询上级吐槽
     *
     * @param parentId,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 17:00
     **/
    @ApiOperation(value = "查询上级吐槽", notes = "查询上级吐槽")
    @GetMapping("/comment/{parentId}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }


    /**
     * 吐槽点赞
     *
     * @param spitId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/11 23:27
     **/
    @ApiOperation(value = "吐槽点赞", notes = "吐槽点赞")
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId) {
        //判断用户是否已经点赞(存进redis）
        String userId = "111";
        if (redisTemplate.opsForValue().get("thumbup_" + userId) != null) {
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_" + userId, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

}
