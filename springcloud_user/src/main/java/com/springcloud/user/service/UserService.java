package com.springcloud.user.service;

import com.springcloud.user.dao.UserDao;
import com.springcloud.user.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 用户登录
     *
     * @param mobile,@param password
     * @return com.springcloud.user.pojo.User
     * @author: 许集思
     * @date: 2020/5/24 17:11
     **/
    public User login(String mobile, String password) {
        User user = userDao.findByMobile(mobile);
        if (user != null && encoder.matches(password, user.getPassword())) {
            //登录成功
            return user;
        }
        return null;
    }


    /**
     * 查询全部用户
     *
     * @param
     * @return java.util.List<com.springcloud.user.pojo.User>
     * @author: 许集思
     * @date: 2020/5/24 17:09
     **/
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 分页+多条件查询User用户
     *
     * @param whereMap,@param page,@param size
     * @return org.springframework.data.domain.Page<com.springcloud.user.pojo.User>
     * @author: 许集思
     * @date: 2020/5/24 17:09
     **/
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 根据条件查询User用户
     *
     * @param whereMap
     * @return java.util.List<com.springcloud.user.pojo.User>
     * @author: 许集思
     * @date: 2020/5/24 17:09
     **/
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询User用户
     *
     * @param id
     * @return com.springcloud.user.pojo.User
     * @author: 许集思
     * @date: 2020/5/24 17:09
     **/
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 注册User用户
     *
     * @param user
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 17:09
     **/
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        //密码加密
        user.setPassword(encoder.encode(user.getPassword()));
        //初始化基础数据
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期
        userDao.save(user);
    }

    /**
     * 修改User用户
     *
     * @param user
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 17:09
     **/
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除User用户(必须有管理员权限才可以删除)
     *
     * @param id
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 17:09
     **/
    public void deleteById(String id) {
        String token = (String) request.getAttribute("claims_admin");
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("权限不足");
        }
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 手机号码
                if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return void
     * @author: 许集思
     * @date: 2020/5/24 17:10
     **/
    public void sendSms(String mobile) {
        //产生6位随机数
        String checkCode = RandomStringUtils.randomNumeric(6);
        //向缓存中放一份(6小时过期)
        redisTemplate.opsForValue().set("checkCode_" + mobile, checkCode, 6, TimeUnit.HOURS);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkCode", checkCode);
        //给用户发一份
        //rabbitTemplate.convertAndSend("sms", map);
        //在控制台显示一遍（方便测试）
        System.out.println("验证码为: " + checkCode);
    }


    /**
     * 更新粉丝数和关注数
     *
     * @param x,@param userId,@param friendId
     * @return void
     * @author: 许集思
     * @date: 2020/5/1 20:35
     **/
    @Transactional
    public void updateFansAndFollowCount(int x, String userId, String friendId) {
        //更新粉丝数
        userDao.updateFans(x, friendId);
        //更新关注数
        userDao.updateFollows(x, userId);

    }
}
