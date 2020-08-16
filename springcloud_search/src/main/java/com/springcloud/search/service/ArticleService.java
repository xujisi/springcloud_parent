package com.springcloud.search.service;

import com.springcloud.search.dao.SearchDao;
import com.springcloud.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private SearchDao searchDao;

    //    @Autowired
    //    private IdWorker idWorker;

    /**
     * 添加进索引库
     *
     * @param article
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 16:57
     **/
    public void save(Article article) {
        //article.setId(idWorker.nextId() + "");
        searchDao.save(article);
    }

    /**
     * 根据keyworks模糊查询
     *
     * @param key,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.search.pojo.Article>
     * @author: 许集思
     * @date: 2020/5/13 21:46
     **/
    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return searchDao.findByTitleLikeOrContentLike(key, key, pageable);
    }


}
