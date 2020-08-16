package com.springcloud.spit.service;


import com.springcloud.spit.dao.SpitDao;
import com.springcloud.spit.pojo.Spit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有吐槽
     *
     * @param
     * @return java.util.List<com.springcloud.spit.pojo.Spit>
     * @author: 许集思
     * @date: 2020/5/24 17:01
     **/
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据ID查询吐槽
     *
     * @return com.springcloud.spit.pojo.Spit
     * @author: 许集思
     * @date: 2020/5/24 17:01
     **/
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 修改新增吐槽
     *
     * @param spit
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 17:01
     **/
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果当前添加的吐槽有父节点，那么父节点的吐槽回复数+1
        if (StringUtils.isNotEmpty(spit.getParentid())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }

        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 删除吐槽
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 17:00
     **/
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    /**
     * 查询上级吐槽
     *
     * @param parentid,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.spit.pojo.Spit>
     * @author: 许集思
     * @date: 2020/5/24 17:00
     **/
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageable);
    }


    /**
     * 吐槽点赞
     *
     * @param spidId
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 17:00
     **/
    public void thumbup(String spidId) {
        //效率有问题
        //Spit spit = spitDao.findById(spidId).get();
        //spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup()) + 1);
        //spitDao.save(spit);

        //方式二:使用mongo命令实现自增db.spit.update(["_id":"1"],[$inc:{thumbup:NumberInt(1)]])
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spidId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

}
