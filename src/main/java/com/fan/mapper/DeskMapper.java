package com.fan.mapper;

import com.fan.pojo.Desk;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeskMapper {

    List<Desk> queryDeskList();

    Desk queryDeskById(int id);

    Desk queryDeskByUid(int uid);

    int addDesk(Desk desk);

    int updateDesk(Desk desk);

    int deleteDesk(int id);

    List<Desk> queryFreeDeskList();

    List<Desk> queryFullDeskList();
}
