package com.oracle.controller;

import com.oracle.entity.Category;
import com.oracle.entity.Request;
import com.oracle.entity.RequestCategory;
import com.oracle.service.CategoryService;
import com.oracle.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private CategoryService categoryService;

    //TODO 驳回请购单时应该给出原因，可以给数据库新增一个字段

    //查询用户名下所有请购单
    @GetMapping("/getAllReq")
    @ResponseBody
    public Map<String, Object> getAllReq(int memId){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<HashMap<String, Object>> reqLists = requestService.getAllReq(memId);
        if (Objects.isNull(reqLists) || reqLists.size() == 0){
            //失败给出提示信息返回当前页
            info = "查询请购单信息失败";
        }else{
            res.put("reqLists", reqLists);
            info = "Success";
        }
        res.put("info", info);
        return res;
    }

    //查询查看单一请购单
    @GetMapping("/getReqByReqId")
    @ResponseBody
    public Map<String, Object> getReqByReqId(int reqId){
        //HashMap<String, Object> reqInfo = new HashMap<>();
        String info = "";
        Map<String, Object> res = new HashMap<>();

        //查询单一请购单信息
        Request req = requestService.getReqByReqId(reqId);
        //查询该请购单已选品类信息
        List<RequestCategory> reqCatList = requestService.getReqCatByReqId(reqId);
        if (Objects.isNull(reqCatList) || reqCatList.size() == 0){
            info = "该请购单无品类信息或者查询出错";
        }else{
            info = "查询请购单品类信息成功";
            res.put("reqCatList", reqCatList);
            res.put("req", req);
        }
        res.put("info", info);
        return res;
    }

    //新增请购单，用户点击创建就新增，但尚未有选择的品类，状态默认未审批，等级根据用户身份定义
    @GetMapping("/addReq")
    @ResponseBody
    public String addReq(int memId){
        //新增一条请购单
        String info = "";
        if (requestService.addReq(memId) == 1){
            info = "新增请购单信息成功";
        }else{
            info = "新增请购单信息失败";
        }
        //TODO 返回信息在postman里是乱码
        return info;
    }

    //删除一条请购单，有些状态不可删除，删除时该订单绑定的具体品类数量信息也删除
    @GetMapping("/delReq")
    @ResponseBody
    public String delReq(int reqId){
        //由前端判断该请购单能否删除，因此传过来的都是要删除的
        String info = "";
        if (requestService.delReq(reqId) == 1){
            info = "删除请购单信息成功";
        }else{
            info = "删除请购单信息失败";
        }
        //TODO 返回信息在postman里是乱码
        return info;
    }

    //编辑单一请购单,选中一条请购单数据点击编辑的时候，应该把该请购单数据和该单已有请购品类信息查询出来
    //TODO 该方法功能和查询查看功能getReqByReqId重复，某些状态不能修改：审批中和审批通过
    @GetMapping("/updateReq")
    @ResponseBody
    public Map<String, Object> updateReq( int reqId){
        //HashMap<String, Object> reqInfo = new HashMap<>();
        Map<String, Object> reqInfo = new HashMap<>();
        String info = "";
        //查询单一请购单信息
        Request req = requestService.getReqByReqId(reqId);
        //查询该请购单已选品类信息
        List<RequestCategory> reqCatList = requestService.getReqCatByReqId(reqId);
        if(Objects.isNull(req)){
            info = "查询该请购单详细信息失败";
        }else{
            info = "Success";
            reqInfo.put("req", req);
            reqInfo.put("reqCatList", reqCatList);
        }
        reqInfo.put("info", info);
        return reqInfo;
    }

    //查询所有在该请购单中没有被选择过的品类信息
    @GetMapping("/getAllCatCan")
    @ResponseBody
    public Map<String, Object> getAllCatCan(int reqId){
        Map<String, Object> res = new HashMap<>();
        String info = "";
        List<Category> categoryList = categoryService.getAllCatCan(reqId);
        if(Objects.isNull(categoryList) || categoryList.size() == 0){
            info = "没有未被选择过的品类信息";
            //TODO 当没有未被选择过的品类信息时，前端应该做出处理，例如不展示。。。
        }else{
            info = "Success";
            res.put("catList", categoryList);
        }
        res.put("info", info);
        return res;
    }

    //修改请购单状态：0为未提交，1为审批中，2为驳回，3为审核通过，4为生成了总的请购单
    //TODO 这是单一数据审核，待做批量审核
    @GetMapping("/updateReqStatus")
    @ResponseBody
    public String updateReqStatus(int reqId, int reqStatus, String memPos){
        String info = "";
        if(requestService.updateReqStatus(reqId, reqStatus, memPos) == 1){
            info = "Success";
        }else{
            info = "修改请购单状态失败";
        }
        return info;
    }

    //新增请购单选择品类信息
    @PostMapping("/addReqCat")
    @ResponseBody
    public String addReqCat(int reqId, int catId, Double catNum){
        String info = "";
        if(requestService.addReqCat(reqId, catId, catNum) == 1){
            info = "Success";
        }else{
            info = "新增请购品类信息失败";
        }
        return info;
    }

    //修改请购单已选品类信息
    @PostMapping("/updateReqCat")
    @ResponseBody
    public String updateReqCat(int reqCatId, Double catNum){
        String info = "";
        if(requestService.updateReqCat(reqCatId, catNum) == 1){
            info = "Success";
        }else{
            info = "修改请购品类信息失败";
        }
        return info;
    }

    //删除已购品类信息
    @GetMapping("/delReqCat")
    @ResponseBody
    public String delReqCat(int reqCatId){
        String info = "";
        if(requestService.delReqCatById(reqCatId) == 1){
            info = "Success";
        }else{
            info = "删除请购品类信息失败";
        }
        return info;
    }

    //获取所有未审核采购单信息，以供采购经理审核
    @GetMapping("/getAllReqNoPas")
    @ResponseBody
    public Map<String, Object> getAllReqNoPas(){
        Map<String, Object> res = new HashMap<>();
        String info = "";
        List<HashMap<String, Object>> reqLists = requestService.getAllReqNoPas();
        if(Objects.isNull(reqLists) || reqLists.size()==0){
            info = "没有未审核的请购单信息";
        }else{
            info = "Success";
            res.put("reqList", reqLists);
        }
        res.put("info", info);
        return res;
    }

    //TODO 汇总所有审核通过的请购单形成一张总需求单，请购单应该有个状态表示该请购单是否生成了采购方案？
    // 0没有采购方案，1有采购方案但不确定，2有确定了的采购方案，在1的时候，如果用户删除了采购方案，那么状态也要更改，
    // 且一个总的请购需求只能有一个采购方案，如果建立采购方案的过程中又审核通过了新的请购需求，
    // 新建采购方案的时候，生成一张总请购单，如果有新的审批通过的请购单，就将该请购单内容汇总进旧的未生成采购方案的汇总请购单里
    //汇总所有审核通过没有被汇总的请购单，即请购单状态为3的单，且汇总后将状态改为4
    @GetMapping("/colReq")
    @ResponseBody
    public String colReq(){
        String info = "";
        if(requestService.colReq() == 0){
            info = "没有需要汇总的数据";
        }else{
            info = "Success";
        }
        return info;
    }
}
