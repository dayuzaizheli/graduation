package com.oracle.mapper;

import com.oracle.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@ResponseBody
public interface CategoryMapper {
    List<Category> getAllCat();

    int addCat(@Param("catName") String catName,@Param("catUnit") String catUnit);

    int updateCatStatus(@Param("catId")int catId, @Param("catStatus") int catStatus);

    int updateCat(@Param("catId")int catId, @Param("catName") String catName, @Param("catUnit") String catUnit);

    int delCat(@Param("catId")int catId);

    List<Category> getAllCatBy(@Param("catName")String catName);

    List<Category> getAllCatNoPas();

    List<Category> getAllCatCan(@Param("reqId") int reqId);

    List<HashMap<String, Object>> getPurSupCat(@Param("purId") int purId, @Param("supId") int supId,@Param("ordId") int ordId);

    Category getAllCatByName(@Param("catName")String catName);

    List<Category> getAllCatPass();
}
