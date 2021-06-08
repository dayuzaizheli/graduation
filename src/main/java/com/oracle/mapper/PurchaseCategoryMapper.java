package com.oracle.mapper;

import com.oracle.entity.PurchaseCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseCategoryMapper {
    List<PurchaseCategory> getAllPurCatByPurId(@Param("purId") int purId);

    List<PurchaseCategory> getCatByPurAndCat(@Param("purId")int purId, @Param("catId")int catId);

    int addPurCat(PurchaseCategory purCat);

    int updPurCat(PurchaseCategory purCat);

    int delPurCatByPurId(@Param("purId") int purId);

    Double sumTotalByPurId(@Param("purId") int purId);

    int delPurCat(@Param("purCatId")int purCatId);
}
