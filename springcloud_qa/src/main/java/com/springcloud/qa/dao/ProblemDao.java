package com.springcloud.qa.dao;

import com.springcloud.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    @Query(value = "SELECT * FROM tb_problem , tb_pl where id = problemid AND labelid = ? ORDER BY replyTime DESC", nativeQuery = true)
    public Page<Problem> newList(String labelId, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem , tb_pl where id = problemid AND labelid = ? ORDER BY reply DESC", nativeQuery = true)
    public Page<Problem> hotList(String labelId, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem , tb_pl where id = problemid AND labelid = ? AND REPLY = 0 ORDER BY CREATETIME DESC", nativeQuery = true)
    public Page<Problem> waitList(String labelId, Pageable pageable);
}
