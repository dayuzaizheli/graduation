package com.oracle.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.entity.PageBean;
import com.oracle.entity.Role;
import com.oracle.entity.Users;
import com.oracle.service.RoleService;
import com.oracle.service.UsersService;
import com.oracle.util.MD5Util;

@Controller
public class UsersController {

    //spring注入@Autowired
    @Autowired
    private  UsersService usersService;

    //注入role业务层
    @Autowired
    private RoleService roleService;


    //实现加入后台的登录页面/WEB_INF/jsp/login.jsp
    //@RequestMapping(value="/login", method=RequestMethod.GET)
    @GetMapping("/login")//请求get：http：//localhost...../login
    public String toLoginPage(){
        return "login";//去login.jsp，给jsp提供逻辑名，配置文件里面有提到
    }

    /*
     * 实现实际登录业务
     * 1.获取输入参数uname和密码upass
     * 2.提交请求/login,请求的方法post
     * 3.需要将参数给业务层
     * 4.需要将参数给数据访问层
     * 5.需要查询数据库
     */
    @PostMapping("/login")
    public String userLogin(Users user, Model model, HttpSession session){//这个参数解决了servlet的获取参数多行重复。
        //密码加密
        String upass=user.getUpass();
        user.setUpass(MD5Util.getMd5Str(upass));
        //调用业务层
        HashMap<String, Object> urs=usersService.userLogin(user);
        //返回的结果
        if(Objects.isNull(urs)){
            //账号和密码错误，重新回到登录页面，重新登录，需要提示账号或密码错误
            model.addAttribute("info", "账号或密码错误");
            return "login";
        }
        //登录成功，进入后台的界面
        session.setAttribute("users", urs);
        return "main";
    }

    //退出
    @GetMapping("/logout")
    public String userLogout(HttpSession session){
        //退出清除session数据
        session.invalidate();
        //退出后回到登录页面重新登录
        return "login";
    }



    //员工管理模块
    //分页显示
    @GetMapping("/getusersbypage")
    public String getUsersByPage(Model model, @RequestParam(name="page", defaultValue="1") int page,
                                 @RequestParam(name="uname", defaultValue="") String uname,
                                 @RequestParam(name="roleid", defaultValue="-1") int roleid){
        //设置pagesize大小
        int pagesize = 5;
        //根据参数获得分页结果，因为查询结果要包含user的roleid对应的rolename,联合查询，所以用HashMap
        PageBean<HashMap<String, Object>> pagebean = usersService.getUserByPage(page, pagesize, uname, roleid);
        //保存查询结果，供前端调用
        model.addAttribute("pagebean", pagebean);
        //查询所有role并保存下来，以供前端查询使用
        List<Role> rolelist = roleService.getAllRole();
        //保存查询结果和查询条件
        model.addAttribute("rolelist", rolelist);
        model.addAttribute("uname", uname);
        model.addAttribute("roleid", roleid);

        //取得结果去分页显示界面
        return "usersbypageajax";
    }

    //通过id删除一条数据
    @GetMapping("/deluser")
    public String delUserById(int id){
        //调用业务层
        usersService.delUserById(id);
        //return "redirect:/getusersbypage";
        return "forward:WEB-INF/jsp/usersbypageajax.html";
    }

    //批量删除
    @GetMapping("/batchdelusers")
    public String batchDelUsers(int[] ids){
        //调用业务层批量删除方法
        usersService.batchDelUsers(ids);
        //删除成功拿取最新数据
        //return "redirect:/getusersbypage";
        return "forward:WEB-INF/jsp/usersbypageajax.jsp";
    }

    //新增用户
    //第一步：进入添加用户界面
    @GetMapping("/adduser")
    public String toAddUserPage(Model model){
        //调用业务层获取所有角色
        List<Role> rolelist = roleService.getAllRole();
        //保存所有角色以供前端调用
        model.addAttribute("rolelist", rolelist);

        //进入WEB_INF/jsp/addusers.jsp
        return "addusers";
    }

    //新增用户
    //文件上传改一下路径
    @PostMapping("/userupload")
    @ResponseBody
    public Map<String,String> uploadFile(MultipartFile upimage, HttpServletRequest request){
        //获取文件名
        String fileName=upimage.getOriginalFilename();

        //自：判断是否有更新img?

        //上传文件名称sssfs--hdkjhkj.jpg
        String realName=UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));
        //服务器路径
        String serverPath = request.getServletContext().getRealPath("/")+"/resources/image_user/";
        Map<String, String> result = new HashMap<>();
        File file = new File(serverPath+realName);
        try {
            upimage.transferTo(file);//上传
            //返回结果
            result.put("imgurl", request.getContextPath()+"/resources/image_user/"+realName);
            //上传后文件名称
            result.put("imgName", realName);
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;//json{"imgurl":"","imgName":""}@ResponseBody的功能，将转成json.


    }

    //新增用户
    //第二步：存储用户数据，文件上传已经解决了
    @PostMapping("/addusers")
    public String addUsers(Users user){
        //密码加密
        String upass=user.getUpass();
        user.setUpass(MD5Util.getMd5Str(upass));
        //调用业务层
        usersService.addUsers(user);
        //添加成功显示最新数据
        //return "redirect:/getusersbypage";
        return "forward:WEB-INF/jsp/usersbypageajax.jsp";
    }

//    //修改员工数据
//    //第一步：获取该用户数据返回修改员工界面
//    @GetMapping("/getuserbyid")
//    public String getUserById(Model model, int id){
//        //调用业务层通过id获取该员工数据
//        Users user = usersService.getUserById(id);
//        //获取所有角色类型
//        List<Role> rolelist = roleService.getAllRole();
//        //保存结果以供前端调用
//        model.addAttribute("users", user);
//        model.addAttribute("rolelist", rolelist);
//        model.addAttribute("id",id);
//        //取得数据去修改员工信息页面
//        return "updateusers";//WEB-INF/jsp/updateusers.jsp
//    }

    //修改员工数据
    //第一步：获取该用户数据返回修改员工界面
    @GetMapping("/getuserbyid")
    @ResponseBody
    public List<Role> getUserById(Model model, int id){
        //调用业务层通过id获取该员工数据
        Users user = usersService.getUserById(id);
        //获取所有角色类型
        List<Role> rolelist = roleService.getAllRole();
        //保存结果以供前端调用
        model.addAttribute("users", user);
        model.addAttribute("rolelist", rolelist);
        model.addAttribute("id",id);
        //取得数据去修改员工信息页面
        return rolelist;//WEB-INF/jsp/updateusers.jsp
    }

    //修改员工数据
    //第二步：得到数据后修改该用户数据
    @PostMapping("/updateusers")
    public String updateUsers(Users user){
        //调用业务层修改数据
        usersService.updateUsers(user);
        //拿取最新数据去分页显示界面
        return "forward:WEB-INF/jsp/usersbypageajax.jsp";
    }

    //去ajax分页页面
    @GetMapping("/tousersajaxpage")
    public String toUsersAjaxPage(Model model, HttpSession session){
        List<Role> rolelist = roleService.getAllRole();
        //model.addAttribute("rolelist", rolelist);
        session.setAttribute("rolelist", rolelist);
        return "usersbypageajax";
    }

    //ajax分页
    @PostMapping("/userslistajax")
    @ResponseBody
    public Map<String, Object> getAjaxPageData(int currentPage, Integer roleid, String uname, Model model){
        Map<String, Object> data = new HashMap<>();
        int pagesize = 5;
        //PageBean<Users> pb = usersService.getUserByPage(currentPage, pagesize, uname, roleid);
        PageBean<HashMap<String, Object>> pb = usersService.getUserByPage(currentPage, pagesize, uname, roleid);
        data.put("list", pb.getList());
        data.put("pageSize", pagesize);
        data.put("pageCount", pb.getPages());
        data.put("rowCount", pb.getRowcount());
        //List<Role> rolelist = roleService.getAllRole();
        //model.addAttribute("rolelist", rolelist);
        return data;
    }

}
