package com.springcloud.article.controller;

import com.springcloud.article.pojo.Column;
import com.springcloud.article.service.ColumnService;
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
 * 专栏控制层
 *
 * @author: 许集思
 * @date: 2020/5/23 23:39
 */
@Api(tags = "专栏管理API")
@RestController
@CrossOrigin
@RequestMapping("/column")
public class ColumnController {

    @Autowired
    private ColumnService columnService;


    /**
     * 查询全部专栏
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:39
     **/
    @ApiOperation(value = "查询全部专栏", notes = "查询全部专栏")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findAll());
    }

    /**
     * 根据ID查询专栏
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:40
     **/
    @ApiOperation(value = "根据ID查询专栏", notes = "根据ID查询专栏")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findById(id));
    }


    /**
     * 分页+多条件查询专栏
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:40
     **/
    @ApiOperation(value = "分页+多条件查询专栏", notes = "分页+多条件查询专栏")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Column> pageList = columnService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Column>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询专栏
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:40
     **/
    @ApiOperation(value = "根据条件查询专栏", notes = "根据条件查询专栏")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findSearch(searchMap));
    }

    /**
     * 增加专栏
     *
     * @param column
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:41
     **/
    @ApiOperation(value = "增加专栏", notes = "增加专栏")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Column column) {
        columnService.add(column);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改专栏
     *
     * @param column,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:41
     **/
    @ApiOperation(value = "修改专栏", notes = "修改专栏")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Column column, @PathVariable String id) {
        column.setId(id);
        columnService.update(column);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除专栏
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:41
     **/
    @ApiOperation(value = "删除专栏", notes = "删除专栏")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        columnService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
