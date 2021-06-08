package com.oracle.mapper;

import com.oracle.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderMapper {
    int addOrder(Order order);

    List<HashMap<String, Object>> getAllOrdByCgjl(@Param("memId") int memId);

    List<HashMap<String, Object>> getAllOrdByCgjkr(@Param("memId") int memId);

    int delOrdById(@Param("ordId")int ordId);

    int updOrdStatus(@Param("status")int status, @Param("ordId") int ordId);

    Order getOrdById(@Param("ordId")int ordId);

    int updOrd(Order order);

    List<Order> getOrdOnAPurPass(@Param("purId")int purId);

    List<HashMap<String, Object>> staAnaByTime(@Param("sta")Date sta,@Param("end") Date end);
}
