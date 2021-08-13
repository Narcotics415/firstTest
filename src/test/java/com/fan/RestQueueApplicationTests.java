package com.fan;

import com.fan.mapper.DeskMapper;
import com.fan.mapper.QueueMapper;
import com.fan.mapper.UserMapper;
import com.fan.mapper.WaiterMapper;
import com.fan.pojo.Desk;
import com.fan.pojo.Queue;
import com.fan.pojo.User;
import com.fan.pojo.Waiter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RestQueueApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WaiterMapper waiterMapper;

    @Autowired
    private QueueMapper queueMapper;

    @Autowired
    private DeskMapper deskMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());

    }
    @Test
    public void queryUserListTest(){
//        int uid =(int)null;

    }



}
