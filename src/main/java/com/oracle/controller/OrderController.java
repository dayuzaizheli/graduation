package com.oracle.controller;

import com.oracle.entity.*;
import com.oracle.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    //新建订单的时候，应该绑定方案，选择供应商，选择合同，之后在详细界面编辑订单具体品类及数量信息，选择某一品类时，应该显示该品类计划数量，确认订单时提示是否超出合同余额，没有时存进数据库
    //订单状态：0草稿 1采购经理确认待采购接口人履行 2采购接口人确认即履行中 3采购接口人履行完毕 4采购经理最终确认
    //新增订单，订单应该选择是哪个方案的订单，并且确定订单后，方案该品类的已完成数量和可供选择数量也应该变化，此时该订单状态为草稿0
    @PostMapping("/addOrder")
    @ResponseBody
    public String addOrder(@RequestBody Order order){
        String info = "";
        if(orderService.addOrder(order)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //查询可选择的采购方案
    @GetMapping("/getPurPass")
    @ResponseBody
    public Map<String, Object> getPurPass(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Purchase> purchaseList = orderService.getPurPass();
        if(Objects.isNull(purchaseList) || purchaseList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("purList", purchaseList);
        }
        res.put("info", info);
        return res;
    }

    //查询该方案可供选择的供应商
    @GetMapping("/getPurSup")
    @ResponseBody
    public Map<String, Object> getPurSup(int purId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Supplier> supplierList = orderService.getPurSup(purId);
        if(Objects.isNull(supplierList) || supplierList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("supList", supplierList);
        }
        res.put("info", info);
        return res;
    }

    //查询该供应商和自己的合同
    @GetMapping("/getSupCon")
    @ResponseBody
    public Map<String, Object> getSupCon(int supId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Contract> contractList = orderService.getSupCon(supId);
        if(Objects.isNull(contractList) || contractList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("conList", contractList);
        }
        res.put("info", info);
        return res;
    }

    //查询可供选择的该方案该供应商品类信息
    @GetMapping("/getPurSupCat")
    @ResponseBody
    public Map<String, Object> getPurSupCat(int purId, int supId, int ordId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<HashMap<String, Object>> categoryList = orderService.getPurSupCat(purId, supId, ordId);
        if(Objects.isNull(categoryList) || categoryList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("catList", categoryList);
        }
        res.put("info", info);
        return res;
    }

    //新增订单品类信息，该品类为选择方案里的品类，供应商为方案品类里的供应商，且品类数量不可多于方案数量，一种品类被选则下次不应该出现在被选品类选项里
    //且订单确定后，方案对应品类可选数量一起变化
    @PostMapping("/addOrdCat")
    @ResponseBody
    public String addOrdCat(@RequestBody OrderCategory orderCategory){
        String info = "";
        if(orderService.addOrdCat(orderCategory)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //修改订单，应该修改的是该订单绑定的订单品类信息。若订单绑定的供应商信息更改，则订单同步更改，优先级低
    @PostMapping("/updOrdCat")
    @ResponseBody
    public String updOrdCat(@RequestBody OrderCategory orderCategory){
        String info = "";
        if(orderService.updOrdCat(orderCategory) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //查询订单，查询该用户名下所有订单，根据身份不同，查询结果不同，在serviceImpl中处理，在订单页展示
    @GetMapping("/getAllOrdByMem")
    @ResponseBody
    public Map<String, Object> getAllOrdByMem(int memId, String memPos){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<HashMap<String, Object>> orderList = orderService.getAllOrdByMem(memId, memPos);
        if(Objects.isNull(orderList) || orderList.size() == 0){
            info = "failed";
        }else{
            info = "Success";
            res.put("ordList", orderList);
        }
        res.put("info", info);
        return res;
    }

    //查询订单品类信息，查询该订单对应的详细品类信息，在新的子页面展示，且展示绑定的订单基本信息
    @GetMapping("/getAllOrdCatBy")
    @ResponseBody
    public Map<String, Object> getAllOrdCatBy(int ordId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<OrderCategory> orderCategoryList= orderService.getAllOrdCatBy(ordId);
        if(Objects.isNull(orderCategoryList) || orderCategoryList.size()==0){
            info = "failed";
        }else{
            info = "Success";
            res.put("catList", orderCategoryList);
        }
        res.put("info", info);
        return res;
    }

    //删除订单，删除该订单的同时应该删除对应的订单品类信息，但某些状态下不可删除：该订单已在履行中2
    @GetMapping("/delOrdById")
    @ResponseBody
    public String delOrdById(int ordId){
        String info = "";
        if(orderService.delOrdById(ordId)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //删除订单品类信息
    @GetMapping("/delOrdCatById")
    @ResponseBody
    public String delOrdCatById(int catId){
        String info = "";
        if(orderService.delOrdCatById(catId)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //TODO 前端传过来1234
    //TODO 采购经理确认合同应该判断合同余额是否足够，采购经理接收合同应该把金额从合同中划去,此处应该考虑冻结金额
    //修改订单状态，采购经理提交订单1，采购接口人确定收到订单则为履行中2，采购接口人送达3为已送达，采购经理确认订单则该订单履行完毕4
    @PostMapping("/updOrdStatus")
    @ResponseBody
    public String updOrdStatus(String memPos, int status, int ordId) {
        String info = "";
        int updFlag = orderService.updOrdStatus(memPos, status, ordId);
        if (updFlag == 1) {
            info = "Success";
        } else if (updFlag == 2){
            info = "订单金额超过合同余额";
        }else{
            info = "failed";
        }
        return info;
    }

    //删除订单品类信息
    @GetMapping("/getOrderByID")
    @ResponseBody
    public Map<String, Object> getOrderByID(int ordId){
        String info = "";
        Map<String, Object> res=new HashMap<>();
        Order order = orderService.getOrderByID(ordId);
        if(Objects.isNull(order)){
            info = "failed";
        }else{
            info = "Success";
            res.put("order", order);
        }
        res.put("info", info);
        return res;
    }

    //统计分析模块
    //根据时间范围获得统计信息
    @PostMapping("/staAnaByTime")
    @ResponseBody
    public Map<String, Object> staAnaByTime(java.util.Date sta, java.util.Date end){
        String info="";
        Map<String, Object> res=new HashMap<>();
        List<HashMap<String, Object>> catNumTotals=orderService.staAnaByTime(sta, end);
        if(Objects.isNull(catNumTotals) || catNumTotals.size()==0){
            info="该时间段内没有结束的购买记录";
        }else{
            info="Success";
            res.put("myData", catNumTotals);
        }
        res.put("info", info);
        return res;
    }
}
