package com.oracle.service;

import com.oracle.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCat();

    int addCat(String catName, String catUnit);

    int updateCatStatus(int catId, int catStatus, String memPos);

    int updateCat(int catId, String catName, String catUnit);

    int delCat(int catId);

    List<Category> getAllCatBy(String catName);

    List<Category> getAllCatNoPas();

    List<Category> getAllCatCan(int reqId);

    Category getAllCatByName(String catName);

    List<Category> getAllCatPass();
}
