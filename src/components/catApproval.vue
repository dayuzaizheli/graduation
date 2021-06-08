<template>
  <el-container>
    <el-header style="height: 25px">

    </el-header>

    <el-main>
      <el-table :data="catList">
        <el-table-column prop="catname" label="品类名" width="250">
        </el-table-column>
        <el-table-column prop="catunit" label="品类单位" width="250">
        </el-table-column>
        <el-table-column prop="catlevel" label="品类等级" width="250" :formatter="levFormat">
        </el-table-column>
        <el-table-column prop="catsuper" label="品类父类" :formatter="supFormat">
        </el-table-column>
        <el-table-column prop="catstatus" label="品类状态" :formatter="staFormat">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="250">
          <template slot-scope="scope">
            <el-button size="small" type="warning" plain @click="updCatStatus(scope.row,0)">驳回</el-button>
            <el-button size="small" type="success" plain @click="updCatStatus(scope.row,1)">审核通过</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'catApproval',
    data(){
      return{
        catList:[],
        catName:''
      };
    },
    created() {
      axios.get('http://localhost:8888/testMaven/getAllCatNoPas',
      ).then(res=>{
        if(res.status == 200){
          this.catList=res.data.catList;
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods:{
      //TODO 应该显示品类状态
      //查询数据的封装
      getAllCat(){
        axios.get('http://localhost:8888/testMaven/getAllCatNoPas',
        ).then(res=>{
          if(res.status == 200){
            this.catList=res.data.catList;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //等级格式化
      levFormat(row, index){
        if(row.catlevel==0){
          return "默认0级品类";
        }else{
          return "异常";
        }
      },
      //父类格式化
      supFormat(row, index){
        if(row.catsuper==''){
          return "暂不启用";
        }else{
          return "异常";
        }
      },
      //状态格式化
      staFormat(row, index){
        if(row.catstatus==0){
          return "未提交";
        }else if(row.catstatus==1){
          return "审核中";
        }else if(row.catstatus==2){
          return "驳回";
        }else if(row.catstatus==3){
          return "审核通过";
        }else{
          return "异常";
        }
      },
      //通过名字搜索品类
      //修改状态
      updCatStatus(row, sta){
        let mempos=sessionStorage.getItem("memPos");
        axios.get('http://localhost:8888/testMaven/updateCatStatus',
          {
            params:{
              catId:row.catid,
              catStatus:sta,
              memPos:mempos
            }
          },
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllCat();
          }
        }).catch(err=>{
          console.log(err);
        });
      }
    }
  }
</script>
