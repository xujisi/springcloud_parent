package com.springcloud.qa.controller;

import com.springcloud.qa.pojo.Reply;
import com.springcloud.qa.service.ReplyService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 回复控制层
 *
 * @author: 许集思
 * @date: 2020/5/24 16:38
 */
@Api(tags = "回复问题微服务API")
@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;


    /**
     * 查询全部回复
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:39
     **/
    @ApiOperation(value = "查询全部回复", notes = "查询全部回复")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findAll());
    }

    /**
     * 根据ID查询回复
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:39
     **/
    @ApiOperation(value = "根据ID查询回复", notes = "根据ID查询回复")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findById(id));
    }


    /**
     * 分页+多条件查询回复
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:39
     **/
    @ApiOperation(value = "分页+多条件查询回复", notes = "分页+多条件查询回复")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Reply> pageList = replyService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询回复
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:39
     **/
    @ApiOperation(value = "根据条件查询回复", notes = "根据条件查询回复")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findSearch(searchMap));
    }

    /**
     * 增加回复
     *
     * @param reply
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:40
     **/
    @ApiOperation(value = "增加回复", notes = "增加回复")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Reply reply) {
        replyService.add(reply);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改回复
     *
     * @param reply,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:40
     **/
    @ApiOperation(value = "修改回复", notes = "修改回复")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Reply reply, @PathVariable String id) {
        reply.setId(id);
        replyService.update(reply);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除回复
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:40
     **/
    @ApiOperation(value = "删除回复", notes = "删除回复")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        replyService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
