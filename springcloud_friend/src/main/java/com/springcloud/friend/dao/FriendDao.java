package com.springcloud.friend.dao;

import com.springcloud.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {


    public Friend findByUseridAndFriendid(String userId, String friendId);


    @Modifying
    @Query(value = "UPDATE tb_friend SET islike=?1 WHERE userid = ?2 AND friendid = ?3", nativeQuery = true)
    public void updateIsLike(String isLike, String userId, String friendId);

    @Modifying
    @Query(value = "DELETE  FROM tb_friend  WHERE userid = ? AND friendid = ?", nativeQuery = true)
    void deleteFriend(String userId, String friendId);
}
