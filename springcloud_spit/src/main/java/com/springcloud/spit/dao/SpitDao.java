package com.springcloud.spit.dao;

import com.springcloud.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * 这里就是MONgo介入的关键（继承了一个MongoRepository)
 *
 * @author: 许集思
 * @date: 2020/5/11 23:21
 */

public interface SpitDao extends MongoRepository<Spit, String> {

    public Page<Spit> findByParentid(String parentId, Pageable pageable);
}
