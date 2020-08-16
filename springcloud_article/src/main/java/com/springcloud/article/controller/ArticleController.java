package com.springcloud.article.controller;

import com.springcloud.article.pojo.Article;
import com.springcloud.article.service.ArticleService;
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
 * Article控制层
 *
 * @author: 许集思
 * @date: 2020/5/23 22:53
 */
@Api(tags = "文章管理API")
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 审核文章
     *
     * @param articleId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 22:52
     **/
    @ApiOperation(value = "审核文章", notes = "审核文章")
    @PutMapping("/examine/{articleId}")
    public Result examine(@PathVariable String articleId) {
        articleService.updateState(articleId);
        return new Result(true, StatusCode.OK, "审核成功");
    }

    /**
     * 点赞文章
     *
     * @param articleId
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 22:52
     **/
    @ApiOperation(value = "点赞文章", notes = "点赞文章")
    @PutMapping("/thumbup/{articleId}")
    public Result thumbup(@PathVariable String articleId) {
        articleService.addThumbup(articleId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 查询全部文章
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 22:52
     **/
    @ApiOperation(value = "查询所有文章", notes = "查询所有文章")
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findAll());
    }

    /**
     * 根据ID查询文章
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 22:53
     **/
    @ApiOperation(value = "根据ID查询文章", notes = "根据ID查询文章")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findById(id));
    }

    /**
     * 分页+多条件查询文章
     *
     * @param searchMap,@param page,@param size
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 22:54
     **/
    @ApiOperation(value = "分页+多条件查询文章", notes = "分页+多条件查询文章")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询文章
     *
     * @param searchMap
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 22:54
     **/
    @ApiOperation(value = "根据条件查询文章", notes = "根据条件查询文章")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 新增文章
     *
     * @param article
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 22:54
     **/
    @ApiOperation(value = "新增文章", notes = "新增文章")
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改文章
     *
     * @param article,@param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:26
     **/
    @ApiOperation(value = "修改文章", notes = "修改文章")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除文章
     *
     * @param id
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/23 23:27
     **/
    @ApiOperation(value = "删除文章", notes = "删除文章")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
