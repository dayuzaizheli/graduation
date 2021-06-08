package com.oracle.serviceImpl;

import com.oracle.entity.Postion;
import com.oracle.mapper.PositionMapper;
import com.oracle.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    PositionMapper positionMapper;

    @Override
    public Postion getPosById(int memPos) {
        return positionMapper.getPosById(memPos);
    }

    @Override
    public List<Postion> getAllPos() {
        return positionMapper.getAllPos();
    }

    @Override
    public int addPos(Postion postion) {
        return positionMapper.addPos(postion);
    }

    @Override
    public List<HashMap<String, Object>> getPosResp(String posresp) {
        String str=posresp;
        String[] strL=str.split("],");
        List<HashMap<String, Object>> pow =new ArrayList<>();
        for(String s:strL) {
            HashMap<String, Object> powi=new HashMap<>();
            String[] si=s.split(",");
            if(Objects.isNull(si)) {
            }else {
                String reg = "[^\u4e00-\u9fa5]";
                powi.put("length", si.length);
                List<String> powip=new ArrayList<>();
                for(String sii:si) {
                    powip.add(sii.replaceAll(reg, ""));
                }
                powi.put("powip",powip);
            }
            pow.add(powi);
        }

        return pow;
    }
}
