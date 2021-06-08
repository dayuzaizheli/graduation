package com.oracle.service;

import com.oracle.entity.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderService {
    int addOrder(Order order);

    List<HashMap<String, Object>> getAllOrdByMem(int memId, String memPos);

    List<OrderCategory> getAllOrdCatBy(int ordId);

    List<Purchase> getPurPass();

    List<Supplier> getPurSup(int purId);

    List<Contract> getSupCon(int supId);

    List<HashMap<String, Object>> getPurSupCat(int purId, int supId, int ordId);

    int delOrdById(int ordId);

    int delOrdCatById(int catId);

    int updOrdStatus(String memPos, int status, int ordId);

    int addOrdCat(OrderCategory orderCategory);

    int updOrdCat(OrderCategory orderCategory);

    Order getOrderByID(int ordId);

    List<HashMap<String, Object>> staAnaByTime(Date sta, Date end);
}
