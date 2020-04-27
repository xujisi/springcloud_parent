package com.springcloud.base.controller;


import com.springcloud.base.Labelservice;
import com.springcloud.base.pojo.Label;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelControllsr {

    @Autowired
    private Labelservice labelservice;

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", labelservice.findAll());
    }

    @GetMapping(value = "/{labelId}")
    public Result findById(@PathVariable String labelId) {
        return new Result(true, StatusCode.OK, "查找成功", labelservice.findById(labelId));
    }

    @PostMapping()
    public Result save(@RequestBody Label label) {
        labelservice.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PutMapping(value = "/{labelId}")
    public Result deleteById(@PathVariable String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelservice.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping(value = "/{labelId}")
    public Result deleteById(@PathVariable String labelId) {
        labelservice.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Label label) {
        List<Label> list = labelservice.findSearch(label);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result findSearch(@RequestBody Label label, @PathVariable int size, @PathVariable int page) {
        Page<Label> pageLabel = labelservice.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Label>(pageLabel.getTotalElements(), pageLabel.getContent()));
    }
}
