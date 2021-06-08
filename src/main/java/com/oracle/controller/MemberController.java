package com.oracle.controller;

import com.oracle.entity.Contract;
import com.oracle.entity.Member;
import com.oracle.service.MemberService;
import com.oracle.service.PositionService;
import com.oracle.util.MD5Util;
import com.oracle.entity.Postion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    PositionService positionService;

    //新增用户
    @PostMapping("/addMem")
    @ResponseBody
    public String addMem(@RequestBody Member member){
        String info = "";
        List<Member> memberList = memberService.getMemBy(member.getMemname(), null, -1);
        if (!Objects.isNull(memberList) && memberList.size() > 0){
            //用户存在则回到当前页面
            info =  "该用户已经存在";
        }else{
            String memPwdJm = MD5Util.getMd5Str(member.getMempwd());
            member.setMempwd(memPwdJm);
            int res = memberService.addMem(member);
            if (res == 1){
                //TODO 这个方法能执行成功，但是竟然返回乱码？
                info = "新增用户成功";
            }else{
                info = "新增用户失败";
            }
        }
        //成功返回查询页
        return info;
    }

    //通过条件查询用户
    @PostMapping("/getMemBy")
    @ResponseBody
    public Map<String, Object> getMemBy(String memName, String memPwd, int memIsInner){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        List<Member> memberList = memberService.getMemBy(memName, memPwd, memIsInner);
        if (Objects.isNull(memberList)){
            info = "没有符合条件的用户";
        }else{
            info = "Success";
            res.put("info", info);
        }
        res.put("memberList", memberList);
        return res;
    }

    //修改用户信息
    @PostMapping("/updMem")
    @ResponseBody
    public String updMem(@RequestBody Member member){
        String info = "";
        String memPwdJm = MD5Util.getMd5Str(member.getMempwd());
        member.setMempwd(memPwdJm);
        if (memberService.updMem(member) == 1){
            info = "更新用户信息成功";
        }else{
            info = "更新用户信息失败";
        }
        return info;
    }

    //删除用户信息
    @GetMapping("/delMemById")
    @ResponseBody
    public String delMemById(int memId){
        String info = "";
        if (memberService.delMemById(memId) == 1){
            info = "删除用户信息成功";
        }else{
            info = "删除用户信息失败";
        }
        return info;
    }

    //查询用户状态

    //用户登录
    @PostMapping("/memLogin")
    @ResponseBody
    public Map<String, Object> memLogin(String memName, String memPwd){
        String info = "";
        Map<String, Object> res = new HashMap<>();
        //密码加密
        String memPwdJm = MD5Util.getMd5Str(memPwd);
        List<Member> memberList = memberService.getMemBy(memName, memPwdJm, -1);
        if (Objects.isNull(memberList) || memberList.size() == 0){//需要加上memberList.size() == 0，因为不加会indexoutofException,原因应该是null和size=0是不一样的
            //登陆失败给出失败信息
            info =  "账号或密码错误";
        }else{
            info = "Success";
            //如果memberList不为空，则同账号的用户应该只有一个
            Postion postion = positionService.getPosById(memberList.get(0).getMemposition().intValue());
            //传给前端以供后续调用，前端将这些常用数据存到session
            res.put("memName", memName);
            res.put("memPos", postion.getPosname());
            res.put("memId", memberList.get(0).getMemid().intValue());
            List<HashMap<String, Object>> posResp=positionService.getPosResp(postion.getPosresp());
            res.put("posResp", posResp);

        }
        res.put("info", info);
        return res;
    }

}
