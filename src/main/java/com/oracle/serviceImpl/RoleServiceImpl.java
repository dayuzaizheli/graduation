package com.oracle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entity.Role;
import com.oracle.mapper.RoleMapper;
import com.oracle.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

    //调用数据访问层
    @Autowired
    private RoleMapper roleMapper;

    //实现获取所有role角色类型
    @Override
    public List<Role> getAllRole() {
        // TODO Auto-generated method stub
        return roleMapper.getAllRole();
    }

}
