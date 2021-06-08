package com.oracle.mapper;

import com.oracle.entity.Result;
import com.oracle.entity.ResultInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultMapper {
    //Result和ResultInfo的操作都放在这里
    int addRes(Result result);

    Result getResByPurId(@Param("purId") int purId);

    int addResInfo(ResultInfo resultInfo);

    List<Result> getAllRes();

    List<ResultInfo> getResInfoBy(@Param("resId")int resId);

    int updResInfoNum(@Param("id")int id,@Param("catNum") double catNum);

    List<Result> getResByPurName(@Param("purName")String purName);
}
