<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addReq">新增请购单</el-button>
    </el-header>

    <el-main>
      <el-table :data="reqList">
        <el-table-column prop="reqName" label="单名" width="250">
        </el-table-column>
        <el-table-column prop="memRealName" label="创建人" width="140">
        </el-table-column>
        <el-table-column prop="creTime" label="创建时间" width="250" :formatter="dateFormat">
        </el-table-column>
        <el-table-column prop="reqStatus" label="请购单状态" :formatter="reqStatusFormat" >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="500">
          <template slot-scope="scope">
            <el-button @click="goto(scope.row, 'reqCategory')" size="small" type="primary" plain>查看/编辑</el-button>
            <el-button size="small" type="warning" plain @click="delReqById(scope.row)">删除</el-button>
            <el-button size="small" type="success" plain @click="updStatus(scope.row)">提交审批</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios';
  import moment from 'moment';
  export default {
    name: 'request',
    data(){
      return{
        reqList:[]
      };
    },
    created() {
      let memId=sessionStorage.getItem("memId");
      axios.get('http://localhost:8888/testMaven/getAllReq',
        {
          params:{
            memId:memId
          }
        }
      ).then(res=>{
          if(res.status == 200){
            //console.log(res.data);
            this.reqList=res.data.reqLists;
          }
        }).catch(err=>{
          console.log(err);
      });
    },
    methods:{
      //查询数据的封装
      getAllReq(){
        let memId=sessionStorage.getItem("memId");
        axios.get('http://localhost:8888/testMaven/getAllReq',
          {
            params:{
              memId:memId
            }
          }
        ).then(res=>{
          if(res.status == 200){
            //console.log(res.data);
            this.reqList=res.data.reqLists;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //reqStatus格式化显示
      reqStatusFormat(row, index){
        if(row.reqStatus==0){
          return '未提交';
        }else if(row.reqStatus==1){
          return '提交待审批';
        }else if(row.reqStatus==2){
          return '驳回';
        }else if(row.reqStatus==3){
          return '审核通过';
        }else{
          return '被纳入总单';
        }
      },
      //时间格式化显示
      dateFormat(row, index){
        return moment(row.creTime).format('YYYY-MM-DD HH:mm:ss');
      },
      //添加请购单
      addReq(){
        //调用接口添加新数据
        let memId=sessionStorage.getItem("memId");
        axios.get('http://localhost:8888/testMaven/addReq',
          {
            params:{
              memId:memId
            }
          }).then(res=>{
            if(res.status == 200){
              //插入数据后再次查询数据
              this.getAllReq();
            }
        }).catch(err=>{
          console.log(err);
        });
      },
      //删除请购单
      delReqById(row, index) {
        //某些状态不可删除并给出提示
        if(row.reqStatus==0 || row.reqStatus==2){
          //调用删除接口删除数据
          axios.get('http://localhost:8888/testMaven/delReq',
            {
              params:{
                reqId:row.reqId
              }
            }).then(res=>{
            if(res.status == 200){
              this.$alert('删除成功', '提示', {
                confirmButtonText: '确定'
              });
              this.getAllReq();
            }
          }).catch(err=>{
            console.log(err);
          })
        }else{
          this.$alert('状态为未提交和驳回的请购单才能删除', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      //修改状态
      updStatus(row, index){
        let memPos=sessionStorage.getItem("memPos");
        if(row.reqStatus==0 || row.reqStatus==2){
          axios.get('http://localhost:8888/testMaven/updateReqStatus',
            {
              params:{
                reqId:row.reqId,
                reqStatus:1,
                memPos:memPos
              }
            }
          ).then(res=>{
            if(res.status == 200){
              console.log(res.data);
              this.getAllReq();
            }
          }).catch(err=>{
            console.log(err);
          });
        }else{
          this.$alert('只有未提交或被驳回的请购单能提交', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      //跳转请购品类页面
      goto(row,path){
        if(row.reqStatus==0 || row.reqStatus==2){
          this.$router.push({ name: path, params: {reqId:row.reqId, updFlag:true, ArlFlag:false}});
          //this.$router.push({name: path, query: {reqId: row.reqId}});
        }else{
          this.$router.push({ name: path, params: {reqId:row.reqId, updFlag:false, ArlFlag:false}});
        }
      }
    }
  }
</script>
