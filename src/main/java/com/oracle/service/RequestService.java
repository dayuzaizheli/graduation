package com.oracle.service;

import com.oracle.entity.Request;
import com.oracle.entity.RequestCategory;

import java.util.HashMap;
import java.util.List;

public interface RequestService {
    List<HashMap<String, Object>> getAllReq(int memId);

    int addReq(int memId);

    int delReq(int reqId);

    Request getReqByReqId(int reqId);

    List<RequestCategory> getReqCatByReqId(int reqId);

    int addReqCat(int reqId, int catId, Double catNum);

    int updateReqCat(int reqCatId, Double catNum);

    int delReqCatById(int reqCatId);

    int updateReqStatus(int reqId, int reqStatus, String memPos);

    List<HashMap<String, Object>> getAllReqNoPas();

    int colReq();

    List<HashMap<String, Object>> colreqNoPurCat();
}
