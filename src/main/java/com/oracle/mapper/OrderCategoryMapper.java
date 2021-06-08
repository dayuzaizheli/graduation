package com.oracle.mapper;

import com.oracle.entity.OrderCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCategoryMapper {
    List<OrderCategory> getAllOrdCatBy(@Param("ordId") int ordId);

    int delCatByOrdId(@Param("ordId")int ordId);

    int delOrdCatById(@Param("catId")int catId);

    int addOrdCat(OrderCategory orderCategory);

    int updOrdCat(OrderCategory orderCategory);
}
