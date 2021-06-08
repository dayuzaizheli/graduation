package com.oracle.mapper;

import com.oracle.entity.RequestCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
public interface RequestCategoryMapper {
    int delReqCat(@Param("reqId") int reqId);

    List<RequestCategory> getReqCatByReqId(@Param("reqId") int reqId);

    int addReqCat(@Param("reqId") int reqId,@Param("catId") int catId,@Param("catNum") Double catNum);

    int updateReqCat(@Param("reqCatId")int reqCatId, @Param("catNum") double catNum);

    int delReqCatById(@Param("reqCatId") int reqCatId);
}
