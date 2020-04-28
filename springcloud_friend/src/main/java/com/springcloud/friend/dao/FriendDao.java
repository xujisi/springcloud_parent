package com.springcloud.friend.dao;

import com.springcloud.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {


    public Friend findByUseridAndFriendid(String userId, String friendId);


    @Query(value = "UPDATE TB_FRIEND SET ISLIKE=? WHERE USERID = ? AND FRIENDID = ?", nativeQuery = true)
    public void updateIsLike(String isLike, String userId, String friendId);
}
