package com.springcloud.friend.service;


import com.springcloud.friend.dao.FriendDao;
import com.springcloud.friend.dao.NoFriendDao;
import com.springcloud.friend.pojo.Friend;
import com.springcloud.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 添加好友服务层
 *
 * @author: 许集思
 * @date: 2020/5/1 20:15
 */

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * 添加好友的服务层
     *
     * @param userId,@param friendId
     * @return int
     * @author: 许集思
     * @date: 2020/5/1 20:20
     **/
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

    /**
     * 添加非好友的服务层
     *
     * @param userId,@param friendId
     * @return int
     * @author: 许集思
     * @date: 2020/5/1 20:21
     **/
    public int addNoFriend(String userId, String friendId) {

        //先判断是不是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (noFriend != null) {
            //标识已经是非好友
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     * 删除好友服务层
     *
     * @param userId,@param friendId
     * @return void
     * @author: 许集思
     * @date: 2020/5/1 21:08
     **/
    public void deleteFriend(String userId, String friendId) {

        //先删除好友表中UserId 到 FriendId 中的数据
        friendDao.deleteFriend(userId, friendId);

        //更新FriendId到UserID islike 为0
        friendDao.updateIsLike("0", friendId, userId);

        //非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setFriendid(friendId);
        noFriend.setUserid(userId);
        noFriendDao.save(noFriend);
    }
}
