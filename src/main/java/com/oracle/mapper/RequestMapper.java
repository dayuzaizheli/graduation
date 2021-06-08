package com.oracle.mapper;

import com.oracle.entity.Request;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface RequestMapper {
    List<HashMap<String, Object>> getAllReq(@Param("memId") int memId);

    int addReq(@Param("memId") int memId, @Param("reqLevel") int reqLevel,@Param("reqName") String reqName);

    int delReq(@Param("reqId") int reqId);

    Request getReqByReqId(@Param("reqId") int reqId);

    int updateReqStatus(@Param("reqId")int reqId, @Param("reqStatus")int reqStatus);

    List<HashMap<String, Object>> getAllReqNoPas();

    List<HashMap<String, Object>> colreqNoPurCat();

    Request getColReqNoPur();

    Request getColReqNoPass();

    List<Request> getReqNoPur();
}
