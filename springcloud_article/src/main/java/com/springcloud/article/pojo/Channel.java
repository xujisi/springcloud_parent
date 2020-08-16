package com.springcloud.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 频道实体类
 *
 * @author: 许集思
 * @date: 2020/5/23 23:43
 */
@Entity
@Table(name = "tb_channel")
public class Channel implements Serializable {

    @Id
    private String id;//ID


    private String name;//频道名称
    private String state;//状态


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
