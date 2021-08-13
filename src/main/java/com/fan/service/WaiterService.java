package com.fan.service;

import com.fan.mapper.DeskMapper;
import com.fan.mapper.QueueMapper;
import com.fan.mapper.UserMapper;
import com.fan.mapper.WaiterMapper;
import com.fan.pojo.Desk;
import com.fan.pojo.Queue;
import com.fan.pojo.User;
import com.fan.pojo.Waiter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class WaiterService {
    @Resource
    private DeskMapper deskMapper;

    @Resource
    private WaiterMapper waiterMapper;

    @Resource
    private QueueMapper queueMapper;

    @Resource
    private UserMapper userMapper;


    //admin分页查询所有服务生信息
    public PageInfo<Waiter> queryWaiterList(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Waiter> waiterList = waiterMapper.queryWaiterList();
        PageInfo<Waiter> waiterPageInfo = new PageInfo<>(waiterList,pageSize);
        return waiterPageInfo;
    }

    public Waiter queryWaiterById(int id){
        Waiter waiter = waiterMapper.queryWaiterById(id);
        return waiter;
    }

    public Waiter queryWaiterByName(String name){
        return waiterMapper.queryWaiterByName(name);
    }

    //分页查询所有餐桌信息
    public PageInfo<Desk> queryDeskList(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Desk> deskList = deskMapper.queryDeskList();
        PageInfo<Desk> deskPageInfo = new PageInfo<>(deskList,pageSize);
        return deskPageInfo;
    }

    public Desk queryDeskById(int id){
        return deskMapper.queryDeskById(id);
    }

    public int updateDesk(Desk desk){
        return deskMapper.updateDesk(desk);
    }

    //分页查询所有餐桌信息
        public PageInfo<Queue> queryQueueList(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Queue> queueList = queueMapper.queryQueueList();
        PageInfo<Queue> queuePageInfo = new PageInfo<>(queueList,pageSize);
        return queuePageInfo;
    }

    public int addQueue(Queue queue){
        return queueMapper.addQueue(queue);

    }

    public int deleteQueue(int id){
        return queueMapper.deleteQueue(id);
    }

    //按等候序列入座 并删除该条排队记录
    public int beSeated(){
        List<Queue> queueList = queueMapper.queryQueueList();
        List<Integer> s = new ArrayList();
        for (int i=0; i<queueList.size(); i++){
            List<Desk> desks = deskMapper.queryFreeDeskList();
            for (int j=0; j<desks.size(); j++){
                if(queueList.get(i).getNum() <= desks.get(j).getCapacity()){
                    Desk desk = desks.get(j);
                    desk.setFlag(1);
                    desk.setUid(queueList.get(i).getUid());
                    deskMapper.updateDesk(desk);
                    s.add(queueList.get(i).getId());
                    break;
                }
            }
        }
        System.out.println(s);
        for(int i =0;i<s.size();i++){
            queueMapper.deleteQueue(s.get(i));
        }
        System.out.println("操作完成");
        return 1;
    }

    //由uid找user
    public User queryUserByUid(int uid){
        return userMapper.queryUserById(uid);
    }




}
