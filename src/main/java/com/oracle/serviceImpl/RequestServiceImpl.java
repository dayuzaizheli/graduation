package com.oracle.serviceImpl;

import com.oracle.entity.Member;
import com.oracle.entity.Request;
import com.oracle.entity.RequestCategory;
import com.oracle.mapper.MemberMapper;
import com.oracle.mapper.RequestCategoryMapper;
import com.oracle.mapper.RequestMapper;
import com.oracle.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RequestCategoryMapper requestCategoryMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<HashMap<String, Object>> getAllReq(int memId) {
        return requestMapper.getAllReq(memId);
    }

    @Override
    public int addReq(int memId) {
        Member member = memberMapper.getMemById(memId);
        String reqName = "请购单_" + member.getMemrealname() + "_";
        return requestMapper.addReq(memId, 0, reqName);
    }

    @Override
    public int delReq(int reqId) {
        int res = 0;
        int reqRes = requestMapper.delReq(reqId);
        int reqCatRes = requestCategoryMapper.delReqCat(reqId);
        if (reqCatRes == 1 && reqRes == 1){
            res = 1;
        }
        return res;
    }

    @Override
    public Request getReqByReqId(int reqId) {
        return requestMapper.getReqByReqId(reqId);
    }

    @Override
    public List<RequestCategory> getReqCatByReqId(int reqId) {
        return requestCategoryMapper.getReqCatByReqId(reqId);
    }

    @Override
    public int addReqCat(int reqId, int catId, Double catNum) {
        return requestCategoryMapper.addReqCat(reqId, catId, catNum);
    }

    @Override
    public int updateReqCat(int reqCatId, Double catNum) {
        return requestCategoryMapper.updateReqCat(reqCatId, catNum);
    }

    @Override
    public int delReqCatById(int reqCatId) {
        return requestCategoryMapper.delReqCatById(reqCatId);
    }

    @Override
    public int updateReqStatus(int reqId, int reqStatus, String memPos) {
        if("cgjl".equals(memPos)){
            return requestMapper.updateReqStatus(reqId, reqStatus+2);
        }else{
            return requestMapper.updateReqStatus(reqId, reqStatus);
        }
    }

    @Override
    public List<HashMap<String, Object>> getAllReqNoPas() {
        return requestMapper.getAllReqNoPas();
    }

    @Override
    public int colReq() {
        //查询有没有存在但没有确认采购方案的请购单，有的话，就将未被汇总的请购单数据插入，没有的话就直接汇总所有请购数据，即只能存在一个未被处理的汇总单
        //查询有没有需要被汇总的请购单
        //TODO 被纳入总单的这些请购单的状态应该更改
        List<HashMap<String, Object>> reqNoPurCatCol = requestMapper.colreqNoPurCat();
        if(Objects.isNull(reqNoPurCatCol) || reqNoPurCatCol.size()==0){
            Request request = requestMapper.getColReqNoPur();
            if(Objects.isNull(request)){
                return 0;//没有则不需要执行后续的操作
            }else{
                return 1;
            }
        }else{
            //查询有没有存在但没有确认采购方案的请购单
            Request request = requestMapper.getColReqNoPass();
            if(Objects.isNull(request)) {//没有未被处理的汇总单，即没有level为1，status为0或者1的单
                //创建新的汇总请购单
                String reqName = "总单_";
                requestMapper.addReq(0, 1, reqName);
                request = requestMapper.getColReqNoPass();
            }
            //将数据存进
            List<RequestCategory> reqCatLists = requestCategoryMapper.getReqCatByReqId(request.getReqid());
            for(HashMap<String, Object> reqNoPurCat : reqNoPurCatCol){
                int flag = 0;
                for(RequestCategory reqCat : reqCatLists){
                    if(reqNoPurCat.get("catId").equals(reqCat.getCatid())){
                        Double noPurNum = Double.parseDouble(reqNoPurCat.get("catNum").toString());
                        requestCategoryMapper.updateReqCat(reqCat.getReqcatid(), reqCat.getCatnum()+noPurNum);
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0){
                    Integer noPurId = Integer.parseInt(reqNoPurCat.get("catId").toString());
                    Double noPurNum = Double.parseDouble(reqNoPurCat.get("catNum").toString());
                    requestCategoryMapper.addReqCat(request.getReqid(), noPurId, noPurNum);
                }
            }
            //改变此次被汇总的请购单状态
            //获得此次所有被汇总的请购单
            List<Request> requestList = requestMapper.getReqNoPur();
            //改变请购单的状态
            for(Request request1 : requestList){
                requestMapper.updateReqStatus(request1.getReqid(), 5);
            }
            return 1;
        }
    }

    @Override
    public List<HashMap<String, Object>> colreqNoPurCat() {
        return requestMapper.colreqNoPurCat();
    }
}
