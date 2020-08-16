package com.springcloud.article.controller;

import com.springcloud.article.pojo.Channel;
import com.springcloud.article.service.ChannelService;
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
 * 频道控制层
 *
 * @author: 许集思
 * @date: 2020/5/23 23:32
 */
@Api(tags = "频道管理API")
@RestController
@CrossOrigin
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;


    /**
     * 查询全部频道
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:33
     **/
    @ApiOperation(value = "查询全部频道", notes = "查询全部频道")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", channelService.findAll());
    }

    /**
     * 根据ID查询频道
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:33
     **/
    @ApiOperation(value = "根据ID查询频道", notes = "根据ID查询频道")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", channelService.findById(id));
    }


    /**
     * 分页+多条件查询频道
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:33
     **/
    @ApiOperation(value = "多条件查询频道", notes = "多条件查询频道")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Channel> pageList = channelService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询频道
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:34
     **/
    @ApiOperation(value = "根据条件查询频道", notes = "根据条件查询频道")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", channelService.findSearch(searchMap));
    }

    /**
     * 增加频道
     *
     * @param channel
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:35
     **/
    @ApiOperation(value = "增加频道", notes = "增加频道")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Channel channel) {
        channelService.add(channel);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改频道
     *
     * @param channel,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:35
     **/
    @ApiOperation(value = "修改频道", notes = "修改频道")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Channel channel, @PathVariable String id) {
        channel.setId(id);
        channelService.update(channel);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除频道
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:36
     **/
    @ApiOperation(value = "删除频道", notes = "删除频道")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        channelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
