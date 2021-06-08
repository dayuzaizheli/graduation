package com.oracle.serviceImpl;

import com.oracle.entity.Member;
import com.oracle.mapper.MemberMapper;
import com.oracle.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public List<Member> getMemBy(String memName, String memPwd, int memIsInner) {
        return memberMapper.getMemBy(memName, memPwd, memIsInner);
    }

    @Override
    public int addMem(Member member) {
        return memberMapper.addMem(member);
    }

    @Override
    public int updMem(Member member) {
        return memberMapper.updMem(member);
    }

    @Override
    public int delMemById(int memId) {
        return memberMapper.delMemById(memId);
    }
}
