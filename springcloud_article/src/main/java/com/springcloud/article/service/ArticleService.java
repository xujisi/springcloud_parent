package com.springcloud.article.service;

import com.springcloud.article.dao.ArticleDao;
import com.springcloud.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 文章Service层
 *
 * @author: 许集思
 * @date: 2020/5/23 23:44
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 审核文章
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:45
     **/
    public void updateState(String id) {
        articleDao.updateState(id);
    }

    /**
     * 点赞文章
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:45
     **/
    public void addThumbup(String id) {
        articleDao.addThumbup(id);
    }

    /**
     * 查询全部文章
     *
     * @param
     * @return java.util.List<com.springcloud.article.pojo.Article>
     * @author: 许集思
     * @date: 2020/5/23 23:46
     **/
    public List<Article> findAll() {
        return articleDao.findAll();
    }


    /**
     * 分页+多条件查询文章
     *
     * @param whereMap,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.article.pojo.Article>
     * @author: 许集思
     * @date: 2020/5/23 23:47
     **/
    public Page<Article> findSearch(Map whereMap, int page, int size) {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 根据条件查询文章
     *
     * @param whereMap
     * @return java.util.List<com.springcloud.article.pojo.Article>
     * @author: 许集思
     * @date: 2020/5/23 23:47
     **/
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    /**
     * 根据ID查询文章
     *
     * @param id
     * @return com.springcloud.article.pojo.Article
     * @author: 许集思
     * @date: 2020/5/23 23:46
     **/
    public Article findById(String id) {
        //先从缓存中查询当前对象
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        //如果没有渠道
        if (article == null) {
            //从数据库中查询
            article = articleDao.findById(id).get();
            //存入缓存中(设置一天的缓存时间)
            redisTemplate.opsForValue().set("article_" + id, article, 1, TimeUnit.DAYS);
        }
        return article;
    }

    /**
     * 新增文章
     *
     * @param article
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:47
     **/
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改文章
     *
     * @param article
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:47
     **/
    public void update(Article article) {
        //需要清除redis缓存
        redisTemplate.delete("article_" + article);
        articleDao.save(article);
    }

    /**
     * 删除文章
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:48
     **/
    public void deleteById(String id) {
        articleDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return org.springframework.data.jpa.domain.Specification<com.springcloud.article.pojo.Article>
     * @author: 许集思
     * @date: 2020/5/23 23:48
     **/
    private Specification<Article> createSpecification(Map searchMap) {

        return new Specification<Article>() {

            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 专栏ID
                if (searchMap.get("columnid") != null && !"".equals(searchMap.get("columnid"))) {
                    predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + (String) searchMap.get("columnid") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                // 文章正文
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 文章封面
                if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
                }
                // 是否公开
                if (searchMap.get("ispublic") != null && !"".equals(searchMap.get("ispublic"))) {
                    predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + (String) searchMap.get("ispublic") + "%"));
                }
                // 是否置顶
                if (searchMap.get("istop") != null && !"".equals(searchMap.get("istop"))) {
                    predicateList.add(cb.like(root.get("istop").as(String.class), "%" + (String) searchMap.get("istop") + "%"));
                }
                // 审核状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }
                // 所属频道
                if (searchMap.get("channelid") != null && !"".equals(searchMap.get("channelid"))) {
                    predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + (String) searchMap.get("channelid") + "%"));
                }
                // URL
                if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
                }
                // 类型
                if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                    predicateList.add(cb.like(root.get("type").as(String.class), "%" + (String) searchMap.get("type") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
