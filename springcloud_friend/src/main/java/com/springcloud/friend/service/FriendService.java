package com.springcloud.friend.service;


import com.springcloud.friend.dao.FriendDao;
import com.springcloud.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    public int addFriend(String userId, String friendId) {
        //先判断userId到friendId是否有数据，有就是重复添加，返回0
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend != null) {
            return 0;
        }

        //直接添加好友，让userid到friendid的type为0
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断firend 到userid是否有数据，有把双方状态改为1

        if (friendDao.findByUseridAndFriendid(friendId, userId) != null) {
            //如果对方也喜欢你
            friendDao.updateIsLike("1", friendId, userId);
            friendDao.updateIsLike("1", userId, friendId);
        }
        return 1;
    }

}
