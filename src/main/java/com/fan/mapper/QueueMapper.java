package com.fan.mapper;

import com.fan.pojo.Queue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QueueMapper {

    List<Queue> queryQueueList();

    Queue queryQueueById(int id);

    int addQueue(Queue queue);

    int deleteQueue(int id);

    int deleteQueueByUid(int uid);

    Queue queryQueueByUid(int uid);


}
