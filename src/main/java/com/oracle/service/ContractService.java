package com.oracle.service;

import com.oracle.entity.Contract;

import java.util.List;

public interface ContractService {

    int addCon(Contract contract);

    int delCon(int conId);

    int updCon(Contract contract);

    List<Contract> getAllCon();

    List<Contract> getAllConBy(String conName, int conSupplier, int conStatus);

    int updateConStatus(String memPos, int conStatus, int conId);
}
