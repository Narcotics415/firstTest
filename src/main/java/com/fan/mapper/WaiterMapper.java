package com.fan.mapper;

import com.fan.pojo.Waiter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WaiterMapper {
    List<Waiter> queryWaiterList();

    Waiter queryWaiterById(int id);

    int addWaiter(Waiter waiter);

    int updateWaiter(Waiter waiter);

    int deleteWaiter(int id);

    Waiter queryWaiterByName(String name);
}
