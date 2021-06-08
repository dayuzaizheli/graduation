package com.oracle.serviceImpl;

import com.oracle.entity.*;
import com.oracle.mapper.*;
import com.oracle.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderCategoryMapper orderCategoryMapper;

    @Autowired
    PurchaseMapper purchaseMapper;

    @Autowired
    SupplierMapper supplierMapper;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ResultMapper resultMapper;

    @Override
    public int addOrder(Order order) {
        order.setOrdname(order.getOrdpurid()+"_"+order.getOrdsupplier()+"_"+order.getOrdcon());
        Supplier supplier=supplierMapper.getSupBySupId(order.getOrdsupplier());
        order.setOrdmemsup(supplier.getSupagent());
        return orderMapper.addOrder(order);
    }

    @Override
    public List<HashMap<String, Object>> getAllOrdByMem(int memId, String memPos) {
        //只有采购经理和采购接口人能访问该功能
        if("cgjl".equals(memPos)){
            return orderMapper.getAllOrdByCgjl(memId);
        }else{
            return orderMapper.getAllOrdByCgjkr(memId);
        }
    }

    @Override
    public List<OrderCategory> getAllOrdCatBy(int ordId) {
        return orderCategoryMapper.getAllOrdCatBy(ordId);
    }

    @Override
    public List<Purchase> getPurPass() {
        return purchaseMapper.ordGetPurPass();
    }

    @Override
    public List<Supplier> getPurSup(int purId) {
        return supplierMapper.getPurSup(purId);
    }

    @Override
    public List<Contract> getSupCon(int supId) {
        return contractMapper.getAllConBy(null, supId, 1);
    }

    @Override
    public List<HashMap<String, Object>> getPurSupCat(int purId, int supId, int ordId) {
//        List<HashMap<String, Object>> cats=categoryMapper.getPurSupCat(purId, supId);
//        List<OrderCategory> ordCats=orderCategoryMapper.getAllOrdCatBy(ordId);
//        for(HashMap<String, Object> cat : cats){
//            int catId=Integer.parseInt(cat.get("catId").toString());
//            for(OrderCategory ordCat : ordCats){
//                if(ordCat.getCatid()==catId){
//                    cats.remove(cat);
//                }
//            }
//        }
        return categoryMapper.getPurSupCat(purId, supId, ordId);
    }

    @Override
    public int delOrdById(int ordId) {
        //int ord = orderMapper.delOrdById(ordId);
        int cat = orderCategoryMapper.delCatByOrdId(ordId);
        return orderMapper.delOrdById(ordId);
    }

    @Override
    public int delOrdCatById(int catId) {
        return orderCategoryMapper.delOrdCatById(catId);
    }

    @Override
    public int updOrdStatus(String memPos, int status, int ordId) {
        //根据状态修改返回相应信息，修改合同内容
        if(status == 1){
            Order order = orderMapper.getOrdById(ordId);
            Contract contract = contractMapper.getConById(order.getOrdcon());
            if(order.getTotal() > contract.getConsurquota()){
                return 2;
            }else{
                contract.setConsurquota(contract.getConsurquota()-order.getTotal());
                contractMapper.updCon(contract);
            }
        }
        //当是最终确认状态，如果该订单是第一个该方案的订单，则新建采购结果，最后将结果品类存进结果品类
        //TODO 同一品类个供应商的数量没有合并
        if(status == 4){
            Order order = orderMapper.getOrdById(ordId);
            //查询该方案是否已有最终状态的订单存在
            List<Order> ordOnThisPur = orderMapper.getOrdOnAPurPass(order.getOrdpurid());
            //不存在新建采购结果
            if(Objects.isNull(ordOnThisPur) || ordOnThisPur.size()==0){
                //不存在新建采购结果
                Result result = new Result();
                Purchase purchase = purchaseMapper.getPurById(order.getOrdpurid());
                result.setPurid(order.getOrdpurid());
                result.setResname(purchase.getPurname()+"_采购结果");
                resultMapper.addRes(result);
            }
            //将订单品类信息存进结果品类信息
            //查询该订单采购方案对应的采购结果
            Result result = resultMapper.getResByPurId(order.getOrdpurid());
            //查询该订单的订单品类信息列表
            List<OrderCategory> orderCategoryList = orderCategoryMapper.getAllOrdCatBy(order.getOrdid());
            //查询结果品类列表，同一供应商同一品类的应该合并
            List<ResultInfo> resultInfos = resultMapper.getResInfoBy(result.getResid());
            if(Objects.isNull(resultInfos) || resultInfos.size()==0){
                //将订单品类列表插入结果品类表
                for(OrderCategory orderCategory : orderCategoryList){
                    ResultInfo resultInfo = new ResultInfo();
                    resultInfo.setCatid(orderCategory.getCatid());
                    resultInfo.setCatnum(orderCategory.getCatnum());
                    resultInfo.setResid(result.getResid());
                    resultInfo.setSupplier(order.getOrdsupplier());
                    resultMapper.addResInfo(resultInfo);
                }
            }else{
                for(OrderCategory orderCategory : orderCategoryList){
                    //同一供应商和同一品类则合并，否则则插入
                    boolean flag=true;
                    for(ResultInfo resultInfo : resultInfos){
                        if(resultInfo.getSupplier()==order.getOrdsupplier() && resultInfo.getCatid()==orderCategory.getCatid()){
                            resultMapper.updResInfoNum(resultInfo.getResinfid(), resultInfo.getCatnum()+orderCategory.getCatnum());
                            flag=false;
                        }
                    }
                    if(flag){
                        ResultInfo resultInfo = new ResultInfo();
                        resultInfo.setCatid(orderCategory.getCatid());
                        resultInfo.setCatnum(orderCategory.getCatnum());
                        resultInfo.setResid(result.getResid());
                        resultInfo.setSupplier(order.getOrdsupplier());
                        resultMapper.addResInfo(resultInfo);
                    }
                }
            }

        }
        //TODO根据不同状态更新相应时间，Mapper.xml加if
        return orderMapper.updOrdStatus(status, ordId);
    }

    @Override
    public int addOrdCat(OrderCategory orderCategory) {
        //增加新品类
        List<OrderCategory> ordCats = orderCategoryMapper.getAllOrdCatBy(orderCategory.getOrdid());
        for(OrderCategory ordCat : ordCats){
            if(orderCategory.getCatid().equals(ordCat.getCatid())){
                //已经存在同品类，则不允许存，理应由前端判断，放置漏掉
                return 0;
            }
        }
        int addFlag = orderCategoryMapper.addOrdCat(orderCategory);
        //更新订单总额
        Order order = orderMapper.getOrdById(orderCategory.getOrdid());
        order.setTotal(order.getTotal() + orderCategory.getTotal());
        int updOrdTotalFlag = orderMapper.updOrd(order);
        return addFlag&updOrdTotalFlag;
    }

    @Override
    public int updOrdCat(OrderCategory orderCategory) {
        Order order = orderMapper.getOrdById(orderCategory.getOrdid());
        order.setTotal(order.getTotal() + orderCategory.getTotal());
        orderMapper.updOrd(order);
        return orderCategoryMapper.updOrdCat(orderCategory);
    }

    @Override
    public Order getOrderByID(int ordId) {
        return orderMapper.getOrdById(ordId);
    }

    @Override
    public List<HashMap<String, Object>> staAnaByTime(Date sta, Date end) {
        return orderMapper.staAnaByTime(sta, end);
    }
}
