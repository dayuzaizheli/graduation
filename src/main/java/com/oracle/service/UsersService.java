package com.oracle.service;

import java.util.HashMap;
import com.oracle.entity.PageBean;
import com.oracle.entity.Users;

//业务层
public interface UsersService {

    //登录方法
    HashMap<String, Object> userLogin(Users user);

    //分页方法
    PageBean<HashMap<String, Object>> getUserByPage(int page, int pagesize, String uname, int roleid);

    //通过id删除一条数据
    int delUserById(int id);

    //批量删除
    int batchDelUsers(int[] ids);

    //添加员工数据
    int addUsers(Users user);

    //通过ID获取员工信息
    Users getUserById(int id);

    //通过id修改该员工数据
    int updateUsers(Users user);

    //获得Ajax分页数据
    //PageBean<HashMap<String, Object>> getAjaxPageData(int currentPage, int pagesize, int roleid, String uname);




}
