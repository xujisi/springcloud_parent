package com.springcloud.recurit.service;

import com.springcloud.recurit.dao.EnterpriseDao;
import com.springcloud.recurit.pojo.Enterprise;
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
 * 服务层
 *
 * @author Administrator
 */
@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询热门企业
     *
     * @param ishot
     * @return java.util.List<com.springcloud.recurit.pojo.Enterprise>
     * @author: 许集思
     * @date: 2020/5/24 16:50
     **/
    public List<Enterprise> hotList(String ishot) {
        return enterpriseDao.findByIshot(ishot);
    }

    /**
     * 查询全部企业
     *
     * @param
     * @return java.util.List<com.springcloud.recurit.pojo.Enterprise>
     * @author: 许集思
     * @date: 2020/5/24 16:50
     **/
    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }


    /**
     * 分页+多条件查询企业
     *
     * @param whereMap,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.recurit.pojo.Enterprise>
     * @author: 许集思
     * @date: 2020/5/24 16:49
     **/
    public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return enterpriseDao.findAll(specification, pageRequest);
    }


    /**
     * 根据条件查询企业
     *
     * @param whereMap
     * @return java.util.List<com.springcloud.recurit.pojo.Enterprise>
     * @author: 许集思
     * @date: 2020/5/24 16:49
     **/
    public List<Enterprise> findSearch(Map whereMap) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        return enterpriseDao.findAll(specification);
    }

    /**
     * 根据ID查询企业
     *
     * @param id
     * @return com.springcloud.recurit.pojo.Enterprise
     * @author: 许集思
     * @date: 2020/5/24 16:50
     **/
    public Enterprise findById(String id) {
        return enterpriseDao.findById(id).get();
    }

    /**
     * 新增企业
     *
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 16:49
     **/
    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseDao.save(enterprise);
    }

    /**
     * 修改企业信息
     *
     * @param enterprise
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 16:49
     **/
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    /**
     * 删除企业
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 16:49
     **/
    public void deleteById(String id) {
        enterpriseDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Enterprise> createSpecification(Map searchMap) {

        return new Specification<Enterprise>() {

            @Override
            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 企业名称
                if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
                }
                // 企业简介
                if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
                    predicateList.add(cb.like(root.get("summary").as(String.class), "%" + (String) searchMap.get("summary") + "%"));
                }
                // 企业地址
                if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
                    predicateList.add(cb.like(root.get("address").as(String.class), "%" + (String) searchMap.get("address") + "%"));
                }
                // 标签列表
                if (searchMap.get("labels") != null && !"".equals(searchMap.get("labels"))) {
                    predicateList.add(cb.like(root.get("labels").as(String.class), "%" + (String) searchMap.get("labels") + "%"));
                }
                // 坐标
                if (searchMap.get("coordinate") != null && !"".equals(searchMap.get("coordinate"))) {
                    predicateList.add(cb.like(root.get("coordinate").as(String.class), "%" + (String) searchMap.get("coordinate") + "%"));
                }
                // 是否热门
                if (searchMap.get("ishot") != null && !"".equals(searchMap.get("ishot"))) {
                    predicateList.add(cb.like(root.get("ishot").as(String.class), "%" + (String) searchMap.get("ishot") + "%"));
                }
                // LOGO
                if (searchMap.get("logo") != null && !"".equals(searchMap.get("logo"))) {
                    predicateList.add(cb.like(root.get("logo").as(String.class), "%" + (String) searchMap.get("logo") + "%"));
                }
                // URL
                if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
