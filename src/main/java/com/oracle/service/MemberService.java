package com.oracle.service;

import com.oracle.entity.Member;

import java.util.List;

public interface MemberService {
    List<Member> getMemBy(String memName, String memPwd, int memIsInner);

    int addMem(Member member);

    int updMem(Member member);

    int delMemById(int memId);
}
