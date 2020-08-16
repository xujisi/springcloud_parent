package com.springcloud.gathering.controller;

import com.springcloud.gathering.pojo.Gathering;
import com.springcloud.gathering.service.GatheringService;
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
 * 活动微服务控制层
 *
 * @author: 许集思
 * @date: 2020/5/24 16:23
 */

@Api(tags = "活动微服务API")
@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;


    /**
     * 查询所有活动
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:23
     **/
    @ApiOperation(value = "查询所有活动", notes = "查询所有活动")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findAll());
    }

    /**
     * 根据ID查询所有活动
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:23
     **/
    @ApiOperation(value = "根据ID查询所有活动", notes = "根据ID查询所有活动")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findById(id));
    }


    /**
     * 分页+多条件查询活动
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:23
     **/
    @ApiOperation(value = "分页+多条件查询活动", notes = "分页+多条件查询活动")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询活动
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:24
     **/
    @ApiOperation(value = "根据条件查询活动", notes = "根据条件查询活动")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findSearch(searchMap));
    }

    /**
     * 新增活动
     *
     * @param gathering
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:24
     **/
    @ApiOperation(value = "新增活动", notes = "新增活动")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Gathering gathering) {
        gatheringService.add(gathering);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改活动
     *
     * @param gathering,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:24
     **/
    @ApiOperation(value = "修改活动", notes = "修改活动")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Gathering gathering, @PathVariable String id) {
        gathering.setId(id);
        gatheringService.update(gathering);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除活动
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:24
     **/
    @ApiOperation(value = "删除活动", notes = "删除活动")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        gatheringService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
