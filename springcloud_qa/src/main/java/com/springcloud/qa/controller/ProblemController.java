package com.springcloud.qa.controller;

import com.springcloud.qa.client.BaseClient;
import com.springcloud.qa.pojo.Problem;
import com.springcloud.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 问题控制器层
 *
 * @author: 许集思
 * @date: 2020/5/24 16:31
 */
@Api(tags = "回答问题微服务API")
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private HttpServletRequest request;

    @Qualifier("baseClientImpl")
    @Autowired
    private BaseClient baseClient;


    /**
     * 调用Base模块的findById
     *
     * @param labelId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:32
     **/
    @ApiOperation(value = "调用Base模块的findById", notes = "调用Base模块的findById")
    @GetMapping(value = "/label/{labelId}")
    public Result findByLabelId(@PathVariable("labelId") String labelId) {
        return baseClient.findById(labelId);
    }


    /**
     * 根据回复时间排序查询问题
     *
     * @param labelId,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:33
     **/
    @ApiOperation(value = "根据回复时间排序查询", notes = "根据回复时间排序查询")
    @GetMapping("/newList/{labelId}/{page}/{size}")
    public Result newList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pagaData = problemService.newList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pagaData.getTotalElements(), pagaData.getContent()));
    }

    /**
     * 根据回复数量排序查询问题
     *
     * @param labelId,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:34
     **/
    @ApiOperation(value = "根据回复数量排序查询", notes = "根据回复数量排序查询")
    @GetMapping("/hotList/{labelId}/{page}/{size}")
    public Result hotList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pagaData = problemService.hotList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pagaData.getTotalElements(), pagaData.getContent()));
    }

    /**
     * 查出回复为0的问题
     *
     * @param labelId,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:34
     **/
    @ApiOperation(value = "查出回复为0的数据", notes = "查出回复为0的数据")
    @GetMapping("/waitList/{labelId}/{page}/{size}")
    public Result waitList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pagaData = problemService.waitList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pagaData.getTotalElements(), pagaData.getContent()));
    }


    /**
     * 查询全部问题
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:35
     **/
    @ApiOperation(value = "查询全部数据", notes = "查询全部数据")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询问题
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:35
     **/
    @ApiOperation(value = "根据ID查询问题", notes = "根据ID查询问题")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询问题
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:35
     **/
    @ApiOperation(value = "分页+多条件查询问题", notes = "分页+多条件查询问题")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询问题
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:36
     **/
    @ApiOperation(value = "根据条件查询问题", notes = "根据条件查询问题")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 新增问题
     *
     * @param problem
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:36
     **/
    @ApiOperation(value = "新增问题", notes = "新增问题")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        String token = (String) request.getAttribute("claims_user");
        if (StringUtils.isEmpty(token)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改问题
     *
     * @param problem,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:36
     **/
    @ApiOperation(value = "修改问题", notes = "修改问题")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除问题
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:37
     **/
    @ApiOperation(value = "删除问题", notes = "删除问题")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
