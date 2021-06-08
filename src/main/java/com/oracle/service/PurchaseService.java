package com.oracle.service;

import com.oracle.entity.Contract;
import com.oracle.entity.Purchase;
import com.oracle.entity.PurchaseCategory;

import java.util.HashMap;
import java.util.List;

public interface PurchaseService {
    Purchase getPurNoPass(int purMem);

    int addPur(Purchase purMem);

    List<Purchase> getAllPur(int purMem);

    List<Purchase> getAllPurNoPass();

    List<PurchaseCategory> getAllPurCatByPurId(int purId);

    List<HashMap<String, Object>> getColReqCatInfo();

    //List<Supplier> getSupHaveCon(int purId, int catId);

    List<Contract> getSupConHaveSur(int supId);

    int addPurCat(PurchaseCategory purCat);

    int updPurCat(PurchaseCategory purCat);

    List<HashMap<String, Object>> getCatSup(int purId, int catId);

    int delPur(int purId);

    int updPurStatus(String memPos, int purId, int status);

    int delPurCat(int purCatId);
}
