package com.oracle.controller;

import com.oracle.entity.Result;
import com.oracle.entity.ResultInfo;
import com.oracle.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class ResultController {

    @Autowired
    ResultService resultService;

    //查询采购结果
    @GetMapping("/getAllRes")
    @ResponseBody
    public Map<String, Object> getAllRes(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Result> resultList = resultService.getAllRes();
        if(Objects.isNull(resultList) || resultList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("resList", resultList);
        }
        res.put("info", info);
        return res;
    }

    //查询采购结果品类信息
    @GetMapping("/getResInfoBy")
    @ResponseBody
    public Map<String, Object> getResInfoBy(int resId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<ResultInfo> resultList = resultService.getResInfoBy(resId);
        if(Objects.isNull(resultList) || resultList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("catList", resultList);
        }
        res.put("info", info);
        return res;
    }

    //通过方案名获得采购结果
    @GetMapping("/getResByPurName")
    @ResponseBody
    public Map<String, Object> getResByPurName(String purName){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Result> resultList = resultService.getResByPurName(purName);
        if(Objects.isNull(resultList) || resultList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("resList", resultList);
        }
        res.put("info", info);
        return res;
    }
}
