package com.oracle.mapper;

import com.oracle.entity.Contract;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public interface ContractMapper {
    int addCon(Contract contract);

    int delCon(@Param("conId") int conId);

    int updCon(Contract contract);

    List<Contract> getAllCon();

    List<Contract> getAllConBy(@Param("conName")String conName, @Param("conSupplier")int conSupplier, @Param("conStatus")int conStatus);

    int updateConStatus(@Param("conStatus")int conStatus, @Param("conId")int conId);

    Contract getConById(@Param("conId")int conId);
}
