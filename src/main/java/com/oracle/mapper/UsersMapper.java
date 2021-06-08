package com.oracle.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oracle.entity.Users;

//...数据访问层
@Repository
public interface UsersMapper {

    HashMap<String, Object> userLogin(Users user);

    //按条件获取当前页数据
    List<HashMap<String, Object>> getUserByPage(@Param("page")int page, @Param("pagesize")int pagesize, @Param("uname")String uname, @Param("roleid")int roleid);

    //按条件获得符合条件的数据条数
    int getTotalRow(@Param("uname")String uname, @Param("roleid")int roleid);

    //通过id删除数据
    int delUserById(@Param("uid")int id);

    //批量删除
    int batchDelUsers(int[] ids);

    //添加员工
    int addUsers(Users user);

    //通过ID获取员工信息
    Users getUserById(@Param("id")int id);

    //通过id修改该员工数据
    int updateUsers(Users user);

    //获取Ajax分页数据
    //List<HashMap<String, Object>> getAjaxPageData(@Param("page")int currentPage, @Param("pagesize")int pagesize, @Param("roleid")int roleid, @Param("uname")String uname);



}
