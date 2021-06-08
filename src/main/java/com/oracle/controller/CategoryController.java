package com.oracle.controller;

import com.oracle.entity.Category;
import com.oracle.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //用户新增品类信息
    @PostMapping("/addCat")
    @ResponseBody
    public String addCat(String catName, String catUnit){
        String info = "";
        Category category = categoryService.getAllCatByName(catName);
        if(Objects.isNull(category)){
            if (categoryService.addCat(catName, catUnit) == 1){
                info = "新增品类信息成功";
            }else{
                info = "新增品类信息失败";
            }
        }else{
            info = "该品类已经存在";
        }
        return info;
    }

    //修改品类信息状态，0为未提交，1为审核中，2为审核通过，3为驳回
    @GetMapping("/updateCatStatus")
    @ResponseBody
    public String updateCatStatus(int catId, int catStatus, String memPos){
        String info = "";
        if (categoryService.updateCatStatus(catId, catStatus, memPos) == 1){
            info = "更新品类信息状态成功";
        }else{
            info = "更新品类信息状态失败";
        }
        return info;
    }

    //用户修改品类信息
    @PostMapping("/updateCat")
    @ResponseBody
    public String updateCat(int catId, String catName, String catUnit){
        String info = "";
        Category category = categoryService.getAllCatByName(catName);
        if(Objects.isNull(category)){
            if (categoryService.updateCat(catId, catName, catUnit) == 1){
                info = "更新品类信息成功";
            }else{
                info = "更新品类信息失败";
            }
        }else{
            info = "该品类已经存在";
        }
        return info;
    }

    //用户删除品类信息
    @GetMapping("/delCat")
    @ResponseBody
    public String delCat(int catId){
        String info = "";
        if (categoryService.delCat(catId) == 1){
            info = "删除品类信息成功";
        }else{
            info = "删除品类信息失败";
        }
        return info;
    }

    //查询所有的品类信息，用SQL判断有没有条件
    @GetMapping("/getAllCatBy")
    @ResponseBody
    public Map<String, Object> getAllCatBy(String catName){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Category> catList = categoryService.getAllCatBy(catName);
        if (Objects.isNull(catList)){
            info = "没有查询到该品类信息";
        }else{
            info = "Success";
            res.put("catList", catList);
        }
        res.put("info", info);
        return res;
    }

    //查询所有未审核的品类信息
    @GetMapping("/getAllCatNoPas")
    @ResponseBody
    public Map<String, Object> getAllCatNoPas(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Category> catList = categoryService.getAllCatNoPas();
        if (Objects.isNull(catList)){
            info = "没有未审核的品类信息";
        }else{
            info = "Success";
            res.put("catList", catList);
        }
        res.put("info", info);
        return res;
    }

    //查询所有审核通过的品类信息
    @GetMapping("/getAllCatPass")
    @ResponseBody
    public Map<String, Object> getAllCatPass(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Category> catList = categoryService.getAllCatPass();
        if (Objects.isNull(catList)){
            info = "没有可选择品类信息";
        }else{
            info = "Success";
            res.put("catList", catList);
        }
        res.put("info", info);
        return res;
    }
}
