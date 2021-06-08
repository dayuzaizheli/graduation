package com.oracle.controller;

import com.oracle.entity.Category;
import com.oracle.entity.Contract;
import com.oracle.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class ContractController {

    @Autowired
    ContractService contractService;

    //新增合同
    //TODO 此处是录入所有合同信息，即录入原有合同，不需要供应商确认。线上签订合同需要增加供应商的确认，线上签订合同视情况做不做
    //TODO 合同需不需要CEO的审批没有做
    //TODO 删除合同时，当该合同下有正在履行的订单则不应该删除并给出提示
    //TODO 删除供应商或者变更供应商时，合同应该是不可用的
    //TODO 线上录入应该上传合同附件，合同应该可以查看详情
    @PostMapping("/addCon")
    @ResponseBody
    public String addCon(@RequestBody Contract contract){
        String info = "";
        if (contractService.addCon(contract) == 1){
            info = "新增合同信息成功";
        }else{
            info = "新增合同信息失败";
        }
        return info;
    }

    //删除合同
    @GetMapping("/delCon")
    @ResponseBody
    public String delCon(int conId){
        String info = "";
        if (contractService.delCon(conId) == 1){
            info = "删除合同信息成功";
        }else{
            info = "删除合同信息失败";
        }
        return info;
    }

    //修改合同
    @PostMapping("/updCon")
    @ResponseBody
    public String updCon(@RequestBody Contract contract){
        String info = "";
        if (contractService.updCon(contract) == 1){
            info = "更新合同信息成功";
        }else{
            info = "更新合同信息失败";
        }
        return info;
    }

    //查询所有状态正常的合同
    @GetMapping("/getAllCon")
    @ResponseBody
    public Map<String, Object> getAllCon(){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Contract> conList = contractService.getAllCon();
        if (Objects.isNull(conList)){
            info = "没有状态正常的合同";
        }else{
            info = "Success";
            res.put("conList", conList);
        }
        res.put("info", info);
        return res;
    }

    //查询所有合同，对于项目经理和供应商和CEO是不一样的，但暂时不做供应商的
    //TODO 合同应该是有附件的，线上签订应该提供模板，但太复杂了，主要业务不在于此，看情况再做
    //按条件查询合同，由SQL判断有没有条件
    @PostMapping("/getAllConBy")
    @ResponseBody
    public Map<String, Object> getAllConBy(String conName, int conSupplier, int conStatus){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Contract> conList = contractService.getAllConBy(conName, conSupplier, conStatus);
        if (Objects.isNull(conList)){
            info = "没有待审批的合同";
        }else{
            info = "Success";
            res.put("conList", conList);

        }
        res.put("info", info);
        return res;
    }

    //修改合同状态：0为草稿，1为采购经理确认（此时不能修改），2为CEO审批中，3为CEO审批完毕即状态正常
    @PostMapping("/updateConStatus")
    @ResponseBody
    public String updateConStatus(String memPos, int conStatus, int conId){
        //前端将登陆用户的岗位和姓名等信息存储到session，调用接口时将状态和岗位传过来
        String info = "";
        if (contractService.updateConStatus(memPos, conStatus, conId) == 1){
            info = "修改合同状态成功";
        }else{
            info = "修改合同状态失败";
        }
        return info;
    }

}
