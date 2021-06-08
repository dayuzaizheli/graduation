package com.oracle.controller;

import com.oracle.entity.Contract;
import com.oracle.entity.Purchase;
import com.oracle.entity.PurchaseCategory;
import com.oracle.entity.Supplier;
import com.oracle.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    //TODO addpur返回逻辑或者返回值有问题
    //新建采购方案，方案和汇总请购单是一对一的关系，所以未确认的采购方案应该只有一个,0草稿1采购经理确认CEO审批中，2CEO退回3CEO审核通过
    //前端点击新建采购方案的时候，会先调用汇总请购的接口，如果没有需要汇总的数据就提示不能建立采购方案，否则才调用该接口
    @PostMapping("/addPur")
    @ResponseBody
    public String addPur(@RequestBody Purchase pur){
        String info= "";
        Purchase purchase = purchaseService.getPurNoPass(pur.getPurmember());
        if(Objects.isNull(purchase)){
            //查询有没有
            if(purchaseService.addPur(pur) == 0){
                info = "目前没有未被纳入采购方案的请购需求,不能建立采购方案";
            }else{
                info = "Success";
            }
        }else{
            info = "已经存在未确认的采购方案";
        }
        return info;
    }

    //添加方案品类供应商信息，总额在前端完成计算
    @PostMapping("/addPurCat")
    @ResponseBody
    public String addPurCat(@RequestBody PurchaseCategory purCat){
        String info = "";
        if(purchaseService.addPurCat(purCat) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //修改方案信息，应该修改的是该方案绑定的品类供应商信息，修改时应该具备和添加时一样的基本原则，已经确定的方案不可修改（采购经理或者CEO确定）
    @PostMapping("/updPurCat")
    @ResponseBody
    public String updPurCat(PurchaseCategory purCat){
        String info = "";
        if(purchaseService.updPurCat(purCat) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //TODO 最终确认的时候，应该把对应的汇总请购单的状态更新一下
    //修改采购方案状态，此处主要是采购经理和CEO会执行该操作，需要注意的是，采购经理确认后，如果方案总额大于10000则需要交给CEO审批，否则状态直接改为最终的确认
    @PostMapping("/updPurStatus")
    @ResponseBody
    public String updPurStatus(String memPos, int purId, int status){
        String info = "";
        if(purchaseService.updPurStatus(memPos, purId, status) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //查询采购方案，查询所有采购方案信息，主要是采购经理会使用
    @GetMapping("/getAllPur")
    @ResponseBody
    public Map<String, Object> getAllPur(int purMem){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Purchase> purchaseList = purchaseService.getAllPur(purMem);
        if(Objects.isNull(purchaseList) || purchaseList.size()==0){
            info = "该用户没有创建采购方案";
        }else{
            info = "Success";
            res.put("purList", purchaseList);
        }
        res.put("info", info);
        return res;
    }

    //查询所有待审核的采购方案，主要是CEO会使用
    @GetMapping("/getAllPurNoPass")
    @ResponseBody
    public Map<String, Object> getAllPurNoPass(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Purchase> purchaseList = purchaseService.getAllPurNoPass();
        if(Objects.isNull(purchaseList) || purchaseList.size()==0){
            info = "没有未审核的采购方案";
        }else{
            info = "Success";
            res.put("purList", purchaseList);
        }
        res.put("info", info);
        return res;
    }

    //查询所有该方案绑定的品类供应商信息
    //TODO 应该将purchase一起返回回去，显示总额的变化
    @GetMapping("/getAllPurCatByPurId")
    @ResponseBody
    public Map<String, Object> getAllPurCatByPurId(int purId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<PurchaseCategory> purCatList = purchaseService.getAllPurCatByPurId(purId);
        if(Objects.isNull(purCatList) || purCatList.size()==0){
            info = "该方案没有品类供应商信息";
        }else{
            info = "Success";
            res.put("purCatList", purCatList);
        }
        res.put("info", info);
        return res;
    }

    //查询对应请购单中所有品类信息：品类Id，品类名，品类单位，品类应购数量
    @GetMapping("/getColReqCatInfo")
    @ResponseBody
    public Map<String, Object> getColReqCatInfo(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<HashMap<String, Object>> catList = purchaseService.getColReqCatInfo();
        if(Objects.isNull(catList) || catList.size()==0){
            info = "最近没有需要购买的品类";
        }else{
            info = "Success";
            res.put("catList", catList);
        }
        res.put("info", info);
        return res;
    }


    //查询一品类对应的供应商信息，应该除去在该方案中同一品类已选的供应商信息
    @GetMapping("/getCatSup")
    @ResponseBody
    public Map<String, Object> getCatSup(int purId, int catId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<HashMap<String, Object>> supList = purchaseService.getCatSup(purId, catId);
        if(Objects.isNull(supList) || supList.size()==0){
            info = "该品类没有供应商";
        }else{
            info = "Success";
            res.put("supList", supList);
        }
        res.put("info", info);
        return res;
    }

    //查询供应商有余额的合同信息，让用户选择绑定的合同
    //TODO 方案应该确定由哪些公司确定提供多少数量的东西，合同方面应该在下订单的时候解决
//    @GetMapping("/getSupConHaveSur")
//    @ResponseBody
//    public Map<String, Object> getSupConHaveSur(int supId){
//        String info = "";
//        Map<String, Object> res = new HashMap<>();
//        List<Contract> contractList = purchaseService.getSupConHaveSur(supId);
//        if(Objects.isNull(contractList) || contractList.size()==0){
//            info = "该供应商没有有余额的合同";
//        }else{
//            info = "Success";
//            res.put("conList", contractList);
//        }
//        res.put("info", info);
//        return res;
//    }

    //计算用户填写该品类数量有没有超过合同所能提供的数量，前端判断
//    @GetMapping("/isNumOutCan")
//    @ResponseBody
//    public String isNumOutCan(int conId, float catNum){
//        String info = "";
//        Boolean IsNumOut = purchaseService.isNumOutCan(conId, catNum);
//        return info;
//    }

    //删除方案信息，应该同步删除该方案下面所有的品类供应商信息，某些状态下不可删除：提交待审批/已确定
    //逻辑删除，将状态更改为4表示已逻辑删除
    @GetMapping("/delPur")
    @ResponseBody
    public String delPur(int purId){
        String info = "";
        if(purchaseService.delPur(purId) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //删除方案品类
    @GetMapping("/delPurCat")
    @ResponseBody
    public String delPurCat(int purCatId){
        String info = "";
        if(purchaseService.delPurCat(purCatId) == 1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }

    //逻辑删除方案品类
    @GetMapping("/delPurLogic")
    @ResponseBody
    public String delPurLogic(int purId){
        String info = "";
        if(purchaseService.updPurStatus("cgjl", purId, 4)==1){
            info = "Success";
        }else{
            info = "failed";
        }
        return info;
    }
}
