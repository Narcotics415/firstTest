package com.fan.service;

import com.fan.mapper.DeskMapper;
import com.fan.mapper.QueueMapper;
import com.fan.mapper.UserMapper;
import com.fan.pojo.Desk;
import com.fan.pojo.Queue;
import com.fan.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private DeskMapper deskMapper;

    @Resource
    private QueueMapper queueMapper;

    //添加用户信息
    public int addUser(User user){
        return userMapper.addUser(user);
    }

    //用name找user
    public User queryUserByName(String name){
        return userMapper.queryUserByName(name);
    }



    //查找空闲桌子
    public List<Desk> queryFreeDeskList(){
        return deskMapper.queryFreeDeskList();
    }

    //判断当前情况是否可以立即入座
    public int goSeatedOrNot(int num){
        int flag =0;
        List<Desk> deskList = deskMapper.queryFreeDeskList();
        for(int i=0; i<deskList.size(); i++){
            if(num <= deskList.get(i).getCapacity()) {
                flag=1;
                break;
            }
        }
        return flag;
    }

    //入座
    public int goSeated(int num, int uid){
        List<Desk> deskList = deskMapper.queryFreeDeskList();
        for(int i=0; i<deskList.size(); i++){
            if(num <= deskList.get(i).getCapacity()) {
                Desk desk = deskList.get(i);
                desk.setFlag(1);
                desk.setUid(uid);
                deskMapper.updateDesk(desk);
                break;
            }
        }
        return 0;
    }

    public int addQueue(Queue queue){
        return queueMapper.addQueue(queue);
    }

    public List<Queue> queryQueueList(){
        return queueMapper.queryQueueList();
    }

    //查找该id的用户，队列前有几个人  返回-1表示该用户没有在排队
    public int countBeforeQueue(int uid){
        List<Queue> queueList = queueMapper.queryQueueList();
        int a = 0;
        for (int i=0;i<queueList.size();i++){
            if(uid == queueList.get(i).getUid()) break;
            a++;
        }

        if (a == queueList.size()){
            return -1;
        }else {
            return a;
        }
    }

    //该id的用户是否正在就餐  0：没  1;有
    public int eatOrNot(int uid){
        List<Desk> deskList = deskMapper.queryFullDeskList();
        int a =0;
        for (int i=0;i<deskList.size();i++){
            if(uid == deskList.get(i).getUid()){
                a=1;
                break;
            }
        }
        return a;
    }

    //查找桌子使用
    public Desk queryDeskByUid(int uid){
        return deskMapper.queryDeskByUid(uid);
    }

    //更新桌子
    public int updateDesk(Desk desk){
        return deskMapper.updateDesk(desk);
    }

    //根据uid查询排队队列
    public Queue queryQueueByUid(int uid){
        return queueMapper.queryQueueByUid(uid);
    }

    //根据uid删除排队队列
    public int deleteQueueByUid(int uid){
        return queueMapper.deleteQueueByUid(uid);
    }



}
