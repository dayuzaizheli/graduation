package com.oracle.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entity.PageBean;
import com.oracle.entity.Users;
import com.oracle.mapper.UsersMapper;
import com.oracle.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    //调用数据访问层
    @Autowired
    private UsersMapper usersMapper;

    //用户登录
    @Override
    public HashMap<String, Object> userLogin(Users user) {
        // TODO Auto-generated method stub

        return usersMapper.userLogin(user);
    }

    //分页实现
    @Override
    public PageBean<HashMap<String, Object>> getUserByPage(int page, int pagesize, String uname, int roleid) {
        // TODO Auto-generated method stub

        //调用数据访问层获取条件查询结果
        List<HashMap<String, Object>> list = usersMapper.getUserByPage(page, pagesize, uname, roleid);
        //实体化一个pagebean用来封装结果
        PageBean<HashMap<String, Object>> pagebean = new PageBean();
        //封装当前页查询数据结果list
        pagebean.setList(list);
        //封装当前页页码page
        pagebean.setPage(page);
        //pagebean.setPagesize(pagesize);
        //调用数据访问层获取总行数
        int rowcount = getTotalRow(uname, roleid);
        //为了Ajax分页设置rowCount
        pagebean.setRowcount(rowcount);
        if(rowcount%pagesize == 0){
            pagebean.setPages(rowcount/pagesize);
        }else{
            pagebean.setPages(rowcount/pagesize + 1);
        }

        return pagebean;
    }

    //按条件获取符合条件的总行数
    private int getTotalRow(String uname, int roleid) {
        // TODO Auto-generated method stub
        return usersMapper.getTotalRow(uname, roleid);
    }

    //通过id删除一条数据
    @Override
    public int delUserById(int id) {
        // TODO Auto-generated method stub
        return  usersMapper.delUserById(id);
    }

    //批量删除
    @Override
    public int batchDelUsers(int[] ids) {
        // TODO Auto-generated method stub
        return usersMapper.batchDelUsers(ids);
    }

    //添加新员工
    @Override
    public int addUsers(Users user) {
        // TODO Auto-generated method stub
        return usersMapper.addUsers(user);
    }

    //通过ID获取员工信息
    @Override
    public Users getUserById(int id) {
        // TODO Auto-generated method stub
        return usersMapper.getUserById(id);
    }

    //通过id修改该员工数据
    @Override
    public int updateUsers(Users user) {
        // TODO Auto-generated method stub
        return usersMapper.updateUsers(user);
    }

    //获得Ajax分页数据
//	@Override
//	public PageBean<HashMap<String, Object>> getAjaxPageData(int currentPage, int pagesize, int roleid, String uname) {
//		// TODO Auto-generated method stub
//		//调用数据访问层获取条件查询结果
//		List<HashMap<String, Object>> list = usersMapper.getAjaxPageData(currentPage, pagesize, roleid, uname);
//		//实体化一个pagebean用来封装结果
//		PageBean<HashMap<String, Object>> pagebean = new PageBean();
//		//封装当前页查询数据结果list
//		pagebean.setList(list);
//		//封装当前页页码page
//		pagebean.setPage(currentPage);
//		//pagebean.setPagesize(pagesize);
//		//调用数据访问层获取总行数
//		int rowcount = getTotalRow(uname, roleid);
//		if(rowcount%pagesize == 0){
//			pagebean.setPages(rowcount/pagesize);
//		}else{
//			pagebean.setPages(rowcount/pagesize + 1);
//		}
//
//		return pagebean;
//	}



}
