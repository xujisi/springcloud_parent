package com.springcloud.spit.controller;


import com.springcloud.spit.pojo.Spit;
import com.springcloud.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    @PostMapping
    public Result save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @PutMapping("/{spitId}")
    public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
        spit.set_id(spitId);
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("/comment/{parentId}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }


    @PutMapping("/thumbup/{spitId")
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
