package com.oracle.mapper;

import com.oracle.entity.CategorySupplier;
import com.oracle.entity.Supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySupplierMapper {
    int addCatSup(CategorySupplier categorySupplier);

    int delCatSupBy(@Param("supId") int supId);

    int updCatSup(@Param("price") Double price, @Param("catSupId") int catSupId);

    List<CategorySupplier> getAllcatSupBy(@Param("supId") int supId);

    int delCatSupById(@Param("catSupId")int catSupId);

    CategorySupplier getCatSupByID(@Param("catSupId")int catSupId);
}
