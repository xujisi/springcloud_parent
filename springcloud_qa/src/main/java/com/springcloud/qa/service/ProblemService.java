package com.springcloud.qa.service;

import com.springcloud.qa.dao.ProblemDao;
import com.springcloud.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 根据回复时间排序查询问题
     *
     * @param labelId,@param page,@param rows
     * @return org.springframework.data.domain.Page<com.springcloud.qa.pojo.Problem>
     * @author: 许集思
     * @date: 2020/5/24 16:38
     **/
    public Page<Problem> newList(String labelId, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.newList(labelId, pageable);
    }

    /**
     * 根据回复数量排序查询问题
     *
     * @param labelId,@param page,@param rows
     * @return org.springframework.data.domain.Page<com.springcloud.qa.pojo.Problem>
     * @author: 许集思
     * @date: 2020/5/24 16:38
     **/
    public Page<Problem> hotList(String labelId, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.hotList(labelId, pageable);
    }

    /**
     * 查出回复为0的问题
     *
     * @param labelId,@param page,@param rows
     * @return org.springframework.data.domain.Page<com.springcloud.qa.pojo.Problem>
     * @author: 许集思
     * @date: 2020/5/24 16:38
     **/
    public Page<Problem> waitList(String labelId, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.waitList(labelId, pageable);
    }


    /**
     * 查询全部问题
     *
     * @param
     * @return java.util.List<com.springcloud.qa.pojo.Problem>
     * @author: 许集思
     * @date: 2020/5/24 16:38
     **/
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 分页+多条件查询问题
     *
     * @param whereMap,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.qa.pojo.Problem>
     * @author: 许集思
     * @date: 2020/5/24 16:37
     **/
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 根据条件查询问题
     *
     * @param whereMap
     * @return java.util.List<com.springcloud.qa.pojo.Problem>
     * @author: 许集思
     * @date: 2020/5/24 16:37
     **/
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询问题
     *
     * @param id
     * @return com.springcloud.qa.pojo.Problem
     * @author: 许集思
     * @date: 2020/5/24 16:37
     **/
    public Problem findById(String id) {
        return problemDao.findById(id).get();
    }

    /**
     * 新增问题
     *
     * @param problem
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 16:37
     **/
    public void add(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }

    /**
     * 修改问题
     *
     * @param problem
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 16:37
     **/
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除问题
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 16:37
     **/
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Problem> createSpecification(Map searchMap) {

        return new Specification<Problem>() {

            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                // 内容
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 是否解决
                if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve"))) {
                    predicateList.add(cb.like(root.get("solve").as(String.class), "%" + (String) searchMap.get("solve") + "%"));
                }
                // 回复人昵称
                if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname"))) {
                    predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + (String) searchMap.get("replyname") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
