package com.springcloud.recurit.controller;

import com.springcloud.recurit.pojo.Enterprise;
import com.springcloud.recurit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 企业控制器层
 *
 * @author: 许集思
 * @date: 2020/5/24 16:46
 */

@RestController
@CrossOrigin
@RequestMapping("/enterprise")
@Api(tags = "企业微服务（标签）API")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 查询热门企业
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:46
     **/
    @GetMapping("/search/hotlist")
    @ApiOperation(value = "查询热门企业", notes = "查询热门企业")
    public Result hotlist() {
        List<Enterprise> list = enterpriseService.hotList("1");
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 查询全部企业
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:47
     **/
    @ApiOperation(value = "查询全部企业", notes = "查询全部企业")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findAll());
    }

    /**
     * 根据ID查询企业
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:47
     **/
    @ApiOperation(value = "根据ID查询企业", notes = "根据ID查询企业")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findById(id));
    }

    /**
     * 分页+多条件查询企业
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:48
     **/
    @ApiOperation(value = "分页+多条件查询企业", notes = "分页+多条件查询企业")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询企业
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:48
     **/
    @ApiOperation(value = "根据条件查询企业", notes = "根据条件查询企业")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findSearch(searchMap));
    }

    /**
     * 新增企业
     *
     * @param enterprise
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:48
     **/
    @ApiOperation(value = "新增企业", notes = "新增企业")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Enterprise enterprise) {
        enterpriseService.add(enterprise);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改企业信息
     *
     * @param enterprise,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:48
     **/
    @ApiOperation(value = "修改企业信息", notes = "修改企业信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Enterprise enterprise, @PathVariable String id) {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除企业
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:49
     **/
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        enterpriseService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
