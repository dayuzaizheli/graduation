package com.oracle.serviceImpl;

import com.oracle.entity.Category;
import com.oracle.mapper.CategoryMapper;
import com.oracle.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCat() {

        return categoryMapper.getAllCat();
    }

    @Override
    public int addCat(String catName, String catUnit) {
        return categoryMapper.addCat(catName, catUnit);
    }

    @Override
    public int updateCatStatus(int catId, int catStatus, String memPos) {
        int res = 0;
        if ("cgjl".equals(memPos)){
            res = categoryMapper.updateCatStatus(catId, catStatus+2);
        } else{
            res = categoryMapper.updateCatStatus(catId, catStatus);
        }
        return res;
    }

    @Override
    public int updateCat(int catId, String catName, String catUnit) {
        return categoryMapper.updateCat(catId, catName, catUnit);
    }

    @Override
    public int delCat(int catId) {
        return categoryMapper.delCat(catId);
    }

    @Override
    public List<Category> getAllCatBy(String catName) {
        return categoryMapper.getAllCatBy(catName);
    }

    @Override
    public List<Category> getAllCatNoPas() {
        return categoryMapper.getAllCatNoPas();
    }

    @Override
    public List<Category> getAllCatCan(int reqId) {
        return categoryMapper.getAllCatCan(reqId);
    }

    @Override
    public Category getAllCatByName(String catName) {
        return categoryMapper.getAllCatByName(catName);
    }

    @Override
    public List<Category> getAllCatPass() {
        return categoryMapper.getAllCatPass();
    }
}
