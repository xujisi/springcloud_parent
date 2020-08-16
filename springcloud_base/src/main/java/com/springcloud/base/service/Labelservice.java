package com.springcloud.base.service;

import com.springcloud.base.dao.LabelDao;
import com.springcloud.base.pojo.Label;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class Labelservice {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有标签
     *
     * @param
     * @return entity.Result
     * @author: 许集思
     * @date: 2020/5/24 15:33
     **/
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查找标签
     *
     * @param id
     * @return com.springcloud.base.pojo.Label
     * @author: 许集思
     * @date: 2020/5/24 15:36
     **/
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 新增标签
     *
     * @param label
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 15:36
     **/
    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 15:36
     **/
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 15:36
     **/
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }


    /**
     * 根据条件查询标签
     *
     * @param label
     * @return java.util.List<com.springcloud.base.pojo.Label>
     * @author: 许集思
     * @date: 2020/5/24 15:36
     **/
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象，也就是要把条件封装到哪个对象中，where 类名= label.getid
             * @param query 封装的都是查询关键字，比如group by  order by
             * @param cb 用来封装条件的
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //先写个List存放所有条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                //new 一个数组作为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                //把List转为数组
                parr = list.toArray(parr);
                return cb.and(parr);
            }
        });
    }

    /**
     * 根据条件+分页查询标签
     *
     * @param label,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.base.pojo.Label>
     * @author: 许集思
     * @date: 2020/5/24 15:36
     **/
    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象，也就是要把条件封装到哪个对象中，where 类名= label.getid
             * @param query 封装的都是查询关键字，比如group by  order by
             * @param cb 用来封装条件的
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //先写个List存放所有条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                //new 一个数组作为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                //把List转为数组
                parr = list.toArray(parr);
                return cb.and(parr);
            }
        }, pageable);
    }
}
