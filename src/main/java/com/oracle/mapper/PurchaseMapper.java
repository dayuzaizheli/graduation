package com.oracle.mapper;

import com.oracle.entity.Contract;
import com.oracle.entity.Purchase;
import com.oracle.entity.Supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PurchaseMapper {
    Purchase getPurNoPass(@Param("purMem") int purMem);

    int addPur(Purchase pur);

    List<Purchase> getAllPur(@Param("purMem") int purMem);

    List<Purchase> getAllPurNoPass();

    List<HashMap<String, Object>> getColReqCatInfo(@Param("reqId") int reqid);

    //List<Supplier> getSupHaveCon(@Param("catId") int catId);

    List<Contract> getSupConHaveSur(@Param("supId") int supId);

    List<Supplier> getCatSup(@Param("catId")int catId);

    int delPurById(@Param("purId") int purId);

    int updPurStatus(@Param("purId") int purId, @Param("status") int status);

    Purchase getPurById(@Param("purId") int purId);

    List<Purchase> ordGetPurPass();

    int updPur(Purchase purchase);

    List<HashMap<String, Object>> getCatSupCan(@Param("catId")int catId,@Param("supIds") List supIds);
}
