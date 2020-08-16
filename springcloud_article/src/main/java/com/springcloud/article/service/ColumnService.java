package com.springcloud.article.service;

import com.springcloud.article.dao.ColumnDao;
import com.springcloud.article.pojo.Column;
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
 * 专栏Service层
 *
 * @author: 许集思
 * @date: 2020/5/23 23:50
 */
@Service
public class ColumnService {

    @Autowired
    private ColumnDao columnDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部专栏
     *
     * @param
     * @return java.util.List<com.springcloud.article.pojo.Column>
     * @author: 许集思
     * @date: 2020/5/23 23:50
     **/
    public List<Column> findAll() {
        return columnDao.findAll();
    }


    /**
     * 分页+多条件查询专栏
     *
     * @param whereMap,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.article.pojo.Column>
     * @author: 许集思
     * @date: 2020/5/23 23:51
     **/
    public Page<Column> findSearch(Map whereMap, int page, int size) {
        Specification<Column> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return columnDao.findAll(specification, pageRequest);
    }


    /**
     * 根据条件查询专栏
     *
     * @param whereMap
     * @return java.util.List<com.springcloud.article.pojo.Column>
     * @author: 许集思
     * @date: 2020/5/23 23:51
     **/
    public List<Column> findSearch(Map whereMap) {
        Specification<Column> specification = createSpecification(whereMap);
        return columnDao.findAll(specification);
    }

    /**
     * 根据ID查询专栏
     *
     * @param id
     * @return com.springcloud.article.pojo.Column
     * @author: 许集思
     * @date: 2020/5/23 23:50
     **/
    public Column findById(String id) {
        return columnDao.findById(id).get();
    }

    /**
     * 增加专栏
     *
     * @param column
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:51
     **/
    public void add(Column column) {
        column.setId(idWorker.nextId() + "");
        columnDao.save(column);
    }

    /**
     * 修改专栏
     *
     * @param column
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:51
     **/
    public void update(Column column) {
        columnDao.save(column);
    }

    /**
     * 删除专栏
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/23 23:51
     **/
    public void deleteById(String id) {
        columnDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return org.springframework.data.jpa.domain.Specification<com.springcloud.article.pojo.Column>
     * @author: 许集思
     * @date: 2020/5/23 23:51
     **/
    private Specification<Column> createSpecification(Map searchMap) {
        return new Specification<Column>() {
            @Override
            public Predicate toPredicate(Root<Column> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 专栏名称
                if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
                }
                // 专栏简介
                if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
                    predicateList.add(cb.like(root.get("summary").as(String.class), "%" + (String) searchMap.get("summary") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
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
