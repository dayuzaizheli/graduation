<template>
  <el-container>
    <el-header style="height: 25px">
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
            <el-button @click="goto(scope.row, 'reqCategory')" size="small" type="primary" plain>查看</el-button>
            <el-button size="small" type="warning" plain @click="updStatus(scope.row,0)">驳回</el-button>
            <el-button size="small" type="success" plain @click="updStatus(scope.row,1)">通过</el-button>
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
    name: 'reqApproval',
    data(){
      return{
        reqList:[]
      };
    },
    created() {
      axios.get('http://localhost:8888/testMaven/getAllReqNoPas',
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.reqList=res.data.reqList;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods:{
      //查询数据的封装
      getAllReq(){
        axios.get('http://localhost:8888/testMaven/getAllReqNoPas',
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.reqList=res.data.reqList;
            }
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
      //修改状态
      updStatus(row, sta){
        axios.get('http://localhost:8888/testMaven/updateReqStatus',
          {
            params:{
              reqId:row.reqId,
              reqStatus:sta,
              memPos:'cgjl'
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
      },
      goto(row,path){
        this.$router.push({ name: path, params: {reqId:row.reqId, updFlag:false, ArlFlag:true}});
      }
    }
  }
</script>
