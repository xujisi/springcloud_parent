package com.springcloud.recurit.controller;

import com.springcloud.recurit.pojo.Recruit;
import com.springcloud.recurit.service.RecruitService;
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
 * 职位控制器层
 *
 * @author: 许集思
 * @date: 2020/5/24 16:50
 */

@RestController
@CrossOrigin
@RequestMapping("/recruit")
@Api(tags = "职位微服务（标签）API")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 查询出排名前6的职位
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:51
     **/
    @ApiOperation(value = "查询出排名前6的职位", notes = "查询出排名前6的职位")
    @RequestMapping(value = "/search/recommend", method = RequestMethod.GET)
    public Result recommend() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.recommend());
    }

    /**
     * 查询出状态不为0排名前6的职位
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:52
     **/
    @ApiOperation(value = "查询出状态不为0排名前6的职位", notes = "查询出状态不为0排名前6的职位")
    @RequestMapping(value = "/search/newlist", method = RequestMethod.GET)
    public Result newList() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.newList());
    }


    /**
     * 查询全部职位
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:53
     **/
    @ApiOperation(value = "查询全部职位", notes = "查询全部职位")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findAll());
    }

    /**
     * 根据ID查询职位
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:53
     **/
    @ApiOperation(value = "根据ID查询职位", notes = "根据ID查询职位")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findById(id));
    }


    /**
     * 分页+多条件查询职位
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:53
     **/
    @ApiOperation(value = "分页+多条件查询职位", notes = "分页+多条件查询职位")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询职位
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:54
     **/
    @ApiOperation(value = "根据条件查询职位", notes = "根据条件查询职位")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findSearch(searchMap));
    }

    /**
     * 新增职位
     *
     * @param recruit
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:54
     **/
    @ApiOperation(value = "新增职位", notes = "新增职位")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Recruit recruit) {
        recruitService.add(recruit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改职位
     *
     * @param recruit,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:54
     **/
    @ApiOperation(value = "修改职位", notes = "修改职位")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.update(recruit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除职位
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:54
     **/
    @ApiOperation(value = "删除职位", notes = "删除职位")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        recruitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
