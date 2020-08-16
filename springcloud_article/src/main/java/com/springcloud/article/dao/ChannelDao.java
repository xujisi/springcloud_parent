package com.springcloud.article.dao;

import com.springcloud.article.pojo.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 频道数据访问接口
 *
 * @author: 许集思
 * @date: 2020/5/23 23:42
 */
public interface ChannelDao extends JpaRepository<Channel, String>, JpaSpecificationExecutor<Channel> {

}
