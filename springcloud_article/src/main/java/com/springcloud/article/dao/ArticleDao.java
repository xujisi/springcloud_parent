package com.springcloud.article.dao;

import com.springcloud.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 文章数据访问接口
 *
 * @author: 许集思
 * @date: 2020/5/23 23:42
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {


    @Modifying
    @Query(value = "UPDATE tb_article Set state = 1 where id = ?1", nativeQuery = true)
    void updateState(String id);

    @Modifying
    @Query(value = "UPDATE tb_article set thumbup = thumbup +1 where id = ?1", nativeQuery = true)
    void addThumbup(String id);
}
