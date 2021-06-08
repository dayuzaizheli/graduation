package com.oracle.controller;

import com.oracle.entity.CategorySupplier;
import com.oracle.entity.Supplier;
import com.oracle.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    //TODO 供应商品类删除没有写
    //TODO 添加供应商品类对的时候，已经被添加过的品类不应该出现在被选项中

    //供应商申请注册，和新增供应商是一样的
    @PostMapping("/addSup")
    @ResponseBody
    public String addSup(@RequestBody Supplier supplier){
        String info = "";
        Supplier sup = supplierService.getSupByName(supplier.getSupname());
        if(Objects.isNull(sup)){
            if(supplierService.addSup(supplier) == 1){
                //成功去往添加供应商品类类信息页面，该页面调用查询所有供应商品类接口，以供后续添加供应商品类信息
                info = "Success";
            }else{
                info = "failed";
            }
        }else{
            info = "该供应商已存在";
        }
        return info;
    }

    //添加供应商品类信息
    @PostMapping("/addCatSup")
    @ResponseBody
    public String addCatSup(@RequestBody CategorySupplier categorySupplier){
        String info = "";
        if(supplierService.addCatSup(categorySupplier)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //修改供应商状态:0供应商申请未确认或者采购经理申请未确认，1供应商确认未审批或者采购经理，3采购经理退回，4采购经理通过
    //TODO因为采购经理也会执行供应商新增操作，所以serviceImpl中的逻辑有问题，应该后台判断原来状态是什么，或者前端直接传数据1234而不是01
    @PostMapping("/updateSupStatus")
    @ResponseBody
    public String updateSupStatus(String memPos, int supStatus, int supId){
        String info = "";
        if(supplierService.updateSupStatus(memPos, supStatus, supId)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //删除供应商，应该把供应商相关品类信息一起删除，某些状态的供应商不能删除
    @GetMapping("/delSup")
    @ResponseBody
    public String delSup(int supId){
        String info = "";
        if(supplierService.delSup(supId)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //修改供应商基本信息，某些状态的供应商信息不能修改
    @PostMapping("/updSup")
    @ResponseBody
    public String updSup(@RequestBody Supplier supplier){
        String info = "";
        Supplier sup = supplierService.getSupByName(supplier.getSupname());
        if(Objects.isNull(sup)){
            if(supplierService.updSup(supplier)==1){
                info = "Success";
            }else{
                info = "failed";
            }
        }else{
            info = "该供应商已存在";
        }
        return info;
    }

    //修改供应商品类信息
    @PostMapping("/updCatSup")
    @ResponseBody
    public String updCatSup(Double price, int catSupId){
        String info = "";
        if (supplierService.updCatSup(price, catSupId) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //查询供应商信息，根据用户身份有所不同查询结果不同
    @GetMapping("/getAllSupBy")
    @ResponseBody
    public Map<String, Object> getAllSupBy(String memPos, int memId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Supplier> supplierList = supplierService.getAllSupBy(memPos, memId);
        if(Objects.isNull(supplierList) || supplierList.size()==0){
            info = "没有符合条件的供应商信息";
        }else{
            info = "Success";
            res.put("supList", supplierList);
        }
        res.put("info", info);
        return res;
    }

    //获取所有需要审批的供应商信息
    @GetMapping("/getAllSup")
    @ResponseBody
    public Map<String, Object> getAllSup(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Supplier> supplierList = supplierService.getAllSupNoPass();
        if(Objects.isNull(supplierList) || supplierList.size()==0){
            info = "没有需要审核的供应商信息";
        }else{
            info = "Success";
            res.put("supList", supplierList);
        }
        res.put("info", info);
        return res;
    }

    //查询供应商品类信息
    @GetMapping("/getAllCatSupBy")
    @ResponseBody
    public Map<String, Object> getAllCatSupBy(int supId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<CategorySupplier> categorySupplierList = supplierService.getAllcatSupBy(supId);
        if(Objects.isNull(categorySupplierList) || categorySupplierList.size()==0){
            info = "该供应商没有品类信息";
        }else{
            info = "Success";
            res.put("catList", categorySupplierList);
        }
        res.put("info", info);
        return res;
    }

    //删除供应商品类信息
    @GetMapping("/delCatSupById")
    @ResponseBody
    public String delCatSupById(int catSupId){
        String info = "";
        if(supplierService.delCatSupById(catSupId) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //获取所有审核通过的供应商信息
    @GetMapping("/getAllSupPass")
    @ResponseBody
    public Map<String, Object> getAllSupPass(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Supplier> supplierList=supplierService.getAllSupPass();
        if(Objects.isNull(supplierList) || supplierList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("supList", supplierList);
        }
        res.put("info", info);
        return res;
    }
}
