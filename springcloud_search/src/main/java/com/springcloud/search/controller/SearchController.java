package com.springcloud.search.controller;


import com.springcloud.search.pojo.Article;
import com.springcloud.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin
@Api(tags = "搜索微服务（标签）API")
public class SearchController {

    @Autowired
    private ArticleService articleService;


    /**
     * 添加进索引库
     *
     * @param article
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:57
     **/
    @ApiOperation(value = "添加进索引库", notes = "添加进索引库")
    @PostMapping
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据key值模糊查询查询
     *
     * @param key,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/13 21:10
     **/
    @ApiOperation(value = "根据key值模糊查询查询", notes = "根据key值模糊查询查询")
    @GetMapping(value = "/{key}/{page}/{size}")
    public Result findByKey(@PathVariable String key, @PathVariable int page, @PathVariable int size) {
        Page<Article> pagaData = articleService.findByKey(key, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pagaData.getTotalElements(), pagaData.getContent()));
    }


}
