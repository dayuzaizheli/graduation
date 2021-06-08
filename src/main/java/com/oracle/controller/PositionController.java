package com.oracle.controller;

import com.oracle.service.PositionService;
import com.oracle.entity.Postion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@Controller
public class PositionController {
    @Autowired
    PositionService positionService;

    @GetMapping("/getAllPos")
    @ResponseBody
    public Map<String, Object> getAllPos(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Postion> postionList = positionService.getAllPos();
        if (Objects.isNull(postionList) || postionList.size() == 0){
            //失败给出提示信息返回当前页
            info = "查询岗位信息失败";
        }else{
            res.put("postionList", postionList);
            info = "Success";
        }
        res.put("info", info);
        return res;
    }

    @PostMapping("/addPos")
    @ResponseBody
    public Map<String, Object> addPos(@RequestBody Postion postion){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        if(positionService.addPos(postion)==0){
            info = "failed";
        }else{
            info = "Success";
        }
        res.put("info", info);
        return res;
    }
}
