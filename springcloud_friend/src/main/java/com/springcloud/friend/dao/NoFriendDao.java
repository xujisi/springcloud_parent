package com.springcloud.friend.dao;

import com.springcloud.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {


    public NoFriend findByUseridAndFriendid(String userId, String friendId);


}
