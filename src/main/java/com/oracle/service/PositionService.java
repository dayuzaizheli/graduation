package com.oracle.service;

import com.oracle.entity.Postion;

import java.util.HashMap;
import java.util.List;

public interface PositionService {
    Postion getPosById(int memPos);

    List<Postion> getAllPos();

    int addPos(Postion postion);

    List<HashMap<String, Object>> getPosResp(String posresp);
}
