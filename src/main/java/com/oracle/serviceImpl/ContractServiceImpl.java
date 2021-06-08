package com.oracle.serviceImpl;

import com.oracle.entity.Contract;
import com.oracle.mapper.ContractMapper;
import com.oracle.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractMapper contractMapper;

    @Override
    public int addCon(Contract contract) {
        //contractMapper.addCon(contract);
        return contractMapper.addCon(contract);
    }

    @Override
    public int delCon(int conId) {
        //TODO 合同下面有正在进行的订单则不能删除
        return contractMapper.delCon(conId);
    }

    @Override
    public int updCon(Contract contract) {
        return contractMapper.updCon(contract);
    }

    @Override
    public List<Contract> getAllCon() {
        return contractMapper.getAllCon();
    }

    @Override
    public List<Contract> getAllConBy(String conName, int conSupplier, int conStatus) {
        return contractMapper.getAllConBy(conName, conSupplier, conStatus);
    }

    @Override
    public int updateConStatus(String memPos, int conStatus, int conId) {
        //如果岗位是项目经理，那么修改状态值为0/1，CEO则2/3
        int res = 0;
        if ("cgjl".equals(memPos)){
            res = contractMapper.updateConStatus(conStatus, conId);
        }else{
            res = contractMapper.updateConStatus(conStatus+2, conId);
        }
        return res;
    }
}
