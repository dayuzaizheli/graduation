package com.oracle.service;

import com.oracle.entity.CategorySupplier;
import com.oracle.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSupBy(String memPos, int memId);

    int addSup(Supplier supplier);

    int addCatSup(CategorySupplier categorySupplier);

    int updateSupStatus(String memPos, int supStatus, int supId);

    int delSup(int supId);

    int updSup(Supplier supplier);

    int updCatSup(Double price, int catSupId);

    List<Supplier> getAllSupNoPass();

    List<CategorySupplier> getAllcatSupBy(int supId);

    Supplier getSupByName(String supname);

    int delCatSupById(int catSupId);

    List<Supplier> getAllSupPass();
}
