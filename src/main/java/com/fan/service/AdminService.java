package com.fan.service;

import com.fan.mapper.DeskMapper;
import com.fan.mapper.UserMapper;
import com.fan.mapper.WaiterMapper;
import com.fan.pojo.Desk;
import com.fan.pojo.User;
import com.fan.pojo.Waiter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private WaiterMapper waiterMapper;

    @Resource
    private DeskMapper deskMapper;

    //分页获取所有用户信息
    public PageInfo<User> queryUserList(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.queryUserList();
        PageInfo<User> userPageInfo = new PageInfo<>(userList,pageSize);
        return userPageInfo;
    }

    //添加用户信息
    public int addUser(User user){
        int i = userMapper.addUser(user);
        return i;
    }

    //用id寻找user
    public User queryUserById(int id){
        User user = userMapper.queryUserById(id);
        return user;
    }

    //更新用户
    public int updateUser(User user){
        int i = userMapper.updateUser(user);
        return i;
    }

    //删除用户
    public  int deleteUser(int id){
        int i = userMapper.deleteUser(id);
        return i;
    }

    //admin分页查询所有服务生信息
    public PageInfo<Waiter> queryWaiterList(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Waiter> waiterList = waiterMapper.queryWaiterList();
        PageInfo<Waiter> waiterPageInfo = new PageInfo<>(waiterList,pageSize);
        return waiterPageInfo;
    }

    public Waiter queryWaiterById(int id){
        Waiter waiter = waiterMapper.queryWaiterById(id);
        return waiter;
    }

    public int addWaiter(Waiter waiter){
        return waiterMapper.addWaiter(waiter);
    }

    public int updateWaiter(Waiter waiter){
        return waiterMapper.updateWaiter(waiter);
    }

    public int deleteWaiter(int id){
        return waiterMapper.deleteWaiter(id);
    }


    //admin分页查询所有餐桌信息
    public PageInfo<Desk> queryDeskList(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Desk> deskList = deskMapper.queryDeskList();
        PageInfo<Desk> deskPageInfo = new PageInfo<>(deskList,pageSize);
        return deskPageInfo;
    }

    public Desk queryDeskById(int id){
        return deskMapper.queryDeskById(id);
    }

    public int addDesk(Desk desk){
        return deskMapper.addDesk(desk);
    }

    public int updateDesk(Desk desk){
        return deskMapper.updateDesk(desk);
    }

    public int deleteDesk(int id){
        return deskMapper.deleteDesk(id);
    }

}
