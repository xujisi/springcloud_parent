package com.springcloud.search.dao;

import com.springcloud.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchDao extends ElasticsearchRepository<Article, String> {


    Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

}
