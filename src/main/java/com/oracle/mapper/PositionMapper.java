package com.oracle.mapper;

import com.oracle.entity.Postion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionMapper {
    Postion getPosById(@Param("memPos") int memPos);

    List<Postion> getAllPos();

    int addPos(Postion postion);
}
