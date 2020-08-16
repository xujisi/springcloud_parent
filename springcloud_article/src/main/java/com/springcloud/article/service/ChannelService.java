package com.springcloud.article.service;

import com.springcloud.article.dao.ChannelDao;
import com.springcloud.article.pojo.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 频道Service层
 *
 * @author: 许集思
 * @date: 2020/5/23 23:48
 */
@Service
public class ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部频道
     *
     * @param
     * @return java.util.List<com.springcloud.article.pojo.Channel>
     * @author: 许集思
     * @date: 2020/5/23 23:49
     **/
    public List<Channel> findAll() {
        return channelDao.findAll();
    }


    /**
     * 分页+多条件查询频道
     *
     * @param whereMap,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.article.pojo.Channel>
     * @author: 许集思
     * @date: 2020/5/23 23:49
     **/
    public Page<Channel> findSearch(Map whereMap, int page, int size) {
        Specification<Channel> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return channelDao.findAll(specification, pageRequest);
    }


    /**
     * 根据条件查询频道
     *
     * @param whereMap
     * @return java.util.List<com.springcloud.article.pojo.Channel>
     * @author: 许集思
     * @date: 2020/5/23 23:49
     **/
    public List<Channel> findSearch(Map whereMap) {
        Specification<Channel> specification = createSpecification(whereMap);
        return channelDao.findAll(specification);
    }

    /**
     * 根据ID查询频道
     *
     * @param id
     * @return com.springcloud.article.pojo.Channel
     * @author: 许集思
     * @date: 2020/5/23 23:49
     **/
    public Channel findById(String id) {
        return channelDao.findById(id).get();
    }

    /**
     * 增加频道
     *
     * @param channel
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:49
     **/
    public void add(Channel channel) {
        channel.setId(idWorker.nextId() + "");
        channelDao.save(channel);
    }

    /**
     * 修改频道
     *
     * @param channel
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:49
     **/
    public void update(Channel channel) {
        channelDao.save(channel);
    }

    /**
     * 删除频道
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:49
     **/
    public void deleteById(String id) {
        channelDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return org.springframework.data.jpa.domain.Specification<com.springcloud.article.pojo.Channel>
     * @author: 许集思
     * @date: 2020/5/23 23:50
     **/
    private Specification<Channel> createSpecification(Map searchMap) {
        return new Specification<Channel>() {
            @Override
            public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 频道名称
                if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
                }
                // 状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
