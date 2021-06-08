package com.oracle.serviceImpl;

import com.oracle.entity.CategorySupplier;
import com.oracle.entity.Supplier;
import com.oracle.mapper.CategorySupplierMapper;
import com.oracle.mapper.SupplierMapper;
import com.oracle.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService{

    @Autowired
    SupplierMapper supplierMapper;

    @Autowired
    CategorySupplierMapper categorySupplierMapper;

    @Override
    public List<Supplier> getAllSupBy(String memPos, int memId) {
        if ("cgjkr".equals(memPos)){
            //采购接口人只看到自己的供应商信息
            return supplierMapper.getSupById(memId);
        }else {
            //除了采购接口人只有采购经理和CEO能看到此页面，所以全部查出
            return supplierMapper.getAllSup();
        }
    }

    @Override
    public int addSup(Supplier supplier) {
        return supplierMapper.addSup(supplier);
    }

    @Override
    public int addCatSup(CategorySupplier categorySupplier) {
        return categorySupplierMapper.addCatSup(categorySupplier);
    }

    @Override
    public int updateSupStatus(String memPos, int supStatus, int supId) {
        //Supplier supplier = (Supplier) supplierMapper.getSupById(supId);
        if("cgjkr".equals(memPos)){
            return supplierMapper.updateSupStatus(supStatus, supId);
        }else {
            return supplierMapper.updateSupStatus(supStatus+2, supId);
        }
    }

    @Override
    public int delSup(int supId) {
        int sup = supplierMapper.delSup(supId);
        int catSup = categorySupplierMapper.delCatSupBy(supId);
//        if (sup == 1 && catSup == 1){
//            return 1;
//        }
        return sup;
    }

    @Override
    public int updSup(Supplier supplier) {
        return supplierMapper.updSup(supplier);
    }

    @Override
    public int updCatSup(Double price, int catSupId) {
        return categorySupplierMapper.updCatSup(price, catSupId);
    }

    @Override
    public List<Supplier> getAllSupNoPass() {
        return supplierMapper.getAllSupNoPass();
    }

    @Override
    public List<CategorySupplier> getAllcatSupBy(int supId) {
        return categorySupplierMapper.getAllcatSupBy(supId);
    }

    @Override
    public Supplier getSupByName(String supname) {
        return supplierMapper.getSupByName(supname);
    }

    @Override
    public int delCatSupById(int catSupId) {
        //CategorySupplier categorySupplier=categorySupplierMapper.getCatSupByID(catSupId);
        //supplierMapper.updateSupStatus(0, categorySupplier.getSupid());
        return categorySupplierMapper.delCatSupById(catSupId);
    }

    @Override
    public List<Supplier> getAllSupPass() {
        return supplierMapper.getAllSupPass();
    }

}
