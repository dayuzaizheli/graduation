package com.oracle.mapper;

import com.oracle.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMapper {
    List<Member> getMemBy(@Param("memName") String memName,@Param("memPwd") String memPwd,@Param("memIsInner") int memIsInner);

    int addMem(Member member);

    int updMem(Member member);

    int delMemById(@Param("memId") int memId);

    Member getMemById(@Param("memId")int memId);
}
