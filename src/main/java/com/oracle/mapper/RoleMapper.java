package com.oracle.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.entity.Role;

//...数据访问层
@Repository
public interface RoleMapper {

    //获取所有role数据
    List<Role> getAllRole();



}
