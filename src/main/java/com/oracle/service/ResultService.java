package com.oracle.service;

import com.oracle.entity.Result;
import com.oracle.entity.ResultInfo;

import java.util.List;

public interface ResultService {
    List<Result> getAllRes();

    List<ResultInfo> getResInfoBy(int resId);

    List<Result> getResByPurName(String purName);
}
