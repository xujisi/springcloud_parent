package com.springcloud.base.controller;


import com.springcloud.base.pojo.Label;
import com.springcloud.base.service.Labelservice;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "基础微服务（标签）API")
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelControllsr {

    @Autowired
    private Labelservice labelservice;

    /**
     * 查询所有标签
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:33
     **/
    @ApiOperation(value = "查询所有标签", notes = "查询所有标签")
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", labelservice.findAll());
    }

    /**
     * 根据ID查找标签
     *
     * @param labelId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:33
     **/
    @ApiOperation(value = "根据ID查找标签", notes = "根据ID查找标签")
    @GetMapping(value = "/{labelId}")
    public Result findById(@PathVariable String labelId) {
        return new Result(true, StatusCode.OK, "查找成功", labelservice.findById(labelId));
    }

    /**
     * 新增标签
     *
     * @param label
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:34
     **/
    @ApiOperation(value = "新增标签", notes = "新增标签")
    @PostMapping()
    public Result save(@RequestBody Label label) {
        labelservice.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 修改标签
     *
     * @param labelId,@param label
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:34
     **/
    @ApiOperation(value = "修改标签", notes = "修改标签")
    @PutMapping(value = "/{labelId}")
    public Result deleteById(@PathVariable String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelservice.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除标签
     *
     * @param labelId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:34
     **/
    @ApiOperation(value = "删除标签", notes = "删除标签")
    @DeleteMapping(value = "/{labelId}")
    public Result deleteById(@PathVariable String labelId) {
        labelservice.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据条件查询标签
     *
     * @param label
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:35
     **/
    @ApiOperation(value = "根据条件查询标签", notes = "根据条件查询标签")
    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Label label) {
        List<Label> list = labelservice.findSearch(label);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据条件+分页查询标签
     *
     * @param label,@param size,@param page
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:35
     **/
    @ApiOperation(value = "根据条件+分页查询标签", notes = "根据条件+分页查询标签")
    @PostMapping(value = "/search/{page}/{size}")
    public Result findSearch(@RequestBody Label label, @PathVariable int size, @PathVariable int page) {
        Page<Label> pageLabel = labelservice.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Label>(pageLabel.getTotalElements(), pageLabel.getContent()));
    }
}
