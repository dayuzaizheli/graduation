package com.oracle.serviceImpl;

import com.oracle.entity.*;
import com.oracle.mapper.PurchaseCategoryMapper;
import com.oracle.mapper.PurchaseMapper;
import com.oracle.mapper.RequestMapper;
import com.oracle.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseMapper purchaseMapper;

    @Autowired
    RequestMapper requestMapper;

    @Autowired
    PurchaseCategoryMapper purchaseCategoryMapper;

    @Override
    public Purchase getPurNoPass(int purMem) {
        return purchaseMapper.getPurNoPass(purMem);
    }

    @Override
    public int addPur(Purchase pur) {
        Request request = requestMapper.getColReqNoPur();
        if(Objects.isNull(request)){
            //没有未绑定采购方案的汇总单
            return 0;
        }else{
            //更新汇总请购单状态表明该单已有采购方案
            int purFlag = purchaseMapper.addPur(pur);
            int reqFlag = requestMapper.updateReqStatus(request.getReqid(), 1);
            return purFlag&reqFlag;
        }
    }

    @Override
    public List<Purchase> getAllPur(int purMem) {
        return purchaseMapper.getAllPur(purMem);
    }

    @Override
    public List<Purchase> getAllPurNoPass() {
        return purchaseMapper.getAllPurNoPass();
    }

    @Override
    public List<PurchaseCategory> getAllPurCatByPurId(int purId) {
        return purchaseCategoryMapper.getAllPurCatByPurId(purId);
    }

    @Override
    public List<HashMap<String, Object>> getColReqCatInfo() {
        Request req = requestMapper.getColReqNoPass();
        if(Objects.isNull(req)){
            return null;
        }else{
            return purchaseMapper.getColReqCatInfo(req.getReqid());
        }
    }

//    @Override
//    public List<Supplier> getSupHaveCon(int purId, int catId) {
//        //得到所有该品类有合同余额的供应商信息
//        List<Supplier> supplierList = purchaseMapper.getSupHaveCon(catId);
//        if(Objects.isNull(supplierList) || supplierList.size()==0){
//            //如果没有满足条件的供应商信息就返回空
//            return null;
//        }else{
//            //有满足条件的供应商信息，则去除已经被选过的该品类供应商
//            List<PurchaseCategory> purCatList = purchaseCategoryMapper.getCatByPurAndCat(purId, catId);
//            if(Objects.isNull(purCatList) || purCatList.size()==0){
//                //该方案该品类没有被选择过的供应商，则直接返回查询出的供应商信息
//                return supplierList;
//            }else{
//                //否则剔除已被选择过的供应商
//                for(PurchaseCategory purCat : purCatList){
//                    for(Supplier sup : supplierList){
//                        if(purCat.getCatsupplier().equals(sup.getSupid())){
//                            supplierList.remove(sup);
//                            break;
//                        }
//                    }
//                }
//            }
//            return supplierList;
//        }
//    }

    @Override
    public List<Contract> getSupConHaveSur(int supId) {
        return purchaseMapper.getSupConHaveSur(supId);
    }

    @Override
    public int addPurCat(PurchaseCategory purCat) {
        purchaseCategoryMapper.addPurCat(purCat);
        Purchase purchase = purchaseMapper.getPurById(purCat.getPurid());
        Object obj = purchaseCategoryMapper.sumTotalByPurId(purCat.getPurid());
        //TODO 事实上将add提前后，可以不必obj判断为空了
        if(Objects.isNull(obj)){
            purchase.setTotal(purCat.getTotal());
        }else{
            Double total = purchaseCategoryMapper.sumTotalByPurId(purCat.getPurid());
            purchase.setTotal(total);
        }
        purchaseMapper.updPur(purchase);
        return purchaseMapper.updPur(purchase);
    }

    @Override
    public int updPurCat(PurchaseCategory purCat) {
        Purchase purchase = purchaseMapper.getPurById(purCat.getPurid());
        Double total = purchaseCategoryMapper.sumTotalByPurId(purCat.getPurid());
        purchase.setTotal(total);
        purchaseMapper.updPur(purchase);
        return purchaseCategoryMapper.updPurCat(purCat);
    }

    @Override
    public List<HashMap<String, Object>> getCatSup(int purId, int catId) {
        //得到所有该品类的供应商信息
        List<Supplier> supplierList = purchaseMapper.getCatSup(catId);
        if(Objects.isNull(supplierList) || supplierList.size()==0){
            //如果没有满足条件的供应商信息就返回空
            return null;
        }else{
            //有满足条件的供应商信息，则去除已经被选过的该品类供应商
            List<PurchaseCategory> purCatList = purchaseCategoryMapper.getCatByPurAndCat(purId, catId);
            if(Objects.isNull(purCatList) || purCatList.size()==0){
                //该方案该品类没有被选择过的供应商，则直接返回查询出的供应商信息
                List supIds = new ArrayList();
                for(Supplier sup : supplierList){
                    supIds.add(sup.getSupid());
                }
                List<HashMap<String, Object>> sups = purchaseMapper.getCatSupCan(catId, supIds);
                return sups;
            }else{
                //否则剔除已被选择过的供应商
                for(PurchaseCategory purCat : purCatList){
                    for(Supplier sup : supplierList){
                        if(purCat.getCatsupplier().equals(sup.getSupid())){
                            supplierList.remove(sup);
                            break;
                        }
                    }
                }
            }
            List supIds = new ArrayList();
            for(Supplier sup : supplierList){
                supIds.add(sup.getSupid());
            }
            if(Objects.isNull(supIds) || supIds.size()==0){
                return null;
            }else{
                List<HashMap<String, Object>> sups = purchaseMapper.getCatSupCan(catId, supIds);
                return sups;
            }
        }
    }

    @Override
    public int delPur(int purId) {
        int purFlag = purchaseMapper.delPurById(purId);
        int purCatFlag = purchaseCategoryMapper.delPurCatByPurId(purId);
        return purFlag&purCatFlag;
    }

    @Override
    public int updPurStatus(String memPos, int purId, int status) {
        //如果是CEO执行的操作，则0为退回2，1为审核通过3
        if("ceo".equals(memPos)){
            //确认时应该将请购总单确定
            if(status==1){
                Request request = requestMapper.getColReqNoPass();
                requestMapper.updateReqStatus(request.getReqid(),2);
            }
            return purchaseMapper.updPurStatus(purId, status+2);
        }else{
            Purchase purchase = purchaseMapper.getPurById(purId);
            //计算方案总额并放进方案，之前前端试试计算显示总额，但不存进数据库
            //Double total = purchaseCategoryMapper.sumTotalByPurId(purId);
            //purchase.setTotal(total);
            //采购经理执行的操作，如果总额没有超过10000，则直接更改状态，1的时候改为3
            if(purchase.getTotal() <= 10000){
                if(status == 1){
                    //确认时应该将请购总单确定
                    if(status==1){
                        Request request = requestMapper.getColReqNoPass();
                        requestMapper.updateReqStatus(request.getReqid(),2);
                    }
                    return purchaseMapper.updPurStatus(purId, status+2);
                }else{
                    return purchaseMapper.updPurStatus(purId, status);
                }
            }else{
                //如果金额大于10000，则直接更改状态即可，1会自动汇总给CEO
                return purchaseMapper.updPurStatus(purId, status);
            }
        }
    }

    @Override
    public int delPurCat(int purCatId) {
        return purchaseCategoryMapper.delPurCat(purCatId);
    }
}
