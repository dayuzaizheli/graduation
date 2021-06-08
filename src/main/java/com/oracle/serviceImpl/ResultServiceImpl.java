package com.oracle.serviceImpl;

import com.oracle.entity.Result;
import com.oracle.entity.ResultInfo;
import com.oracle.mapper.ResultMapper;
import com.oracle.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    ResultMapper resultMapper;

    @Override
    public List<Result> getAllRes() {
        return resultMapper.getAllRes();
    }

    @Override
    public List<ResultInfo> getResInfoBy(int resId) {
        return resultMapper.getResInfoBy(resId);
    }

    @Override
    public List<Result> getResByPurName(String purName) {
        return resultMapper.getResByPurName(purName);
    }
}
