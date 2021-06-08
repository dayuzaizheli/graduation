<template>
  <el-container>
    <el-page-header @back="goBack" content="结果详情" >

    </el-page-header>
    <el-header style="height: 40px; padding-top: 20px">
    </el-header>

    <el-main>
      <el-table :data="resCatList">
        <el-table-column prop="catid" label="品类" :formatter="catFormat">
        </el-table-column>
        <el-table-column prop="supplier" label="供应商" :formatter="supFormat">
        </el-table-column>
        <el-table-column prop="catnum" label="数量" >
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'resCategory',
    //inject: ['reload'],
    data() {
      return {
        resId:this.$route.params.resdId,
        resCatList:[],
        catPassList:[],
        supPassList:[]
      };
    },
    //生命周期函数，好像没用，后面测试一下项目第一次加载有没有用
    created(){
      let resId=this.resId;
      axios.get('http://localhost:8888/testMaven/getResInfoBy',
        {
          params:{
            resId:resId
          }
        }
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.resCatList=res.data.catList;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
      //获取审核通过的品类信息
      axios.get('http://localhost:8888/testMaven/getAllCatPass',
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.catPassList=res.data.catList;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
      //获取审核通过的供应商信息
      axios.get('http://localhost:8888/testMaven/getAllSupPass',
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.supPassList=res.data.supList;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods: {
      //页面回转
      goBack() {
          this.$router.push('/result').catch(err=>{});
      },
      //格式化
      catFormat(row, index){
        for(let i in this.catPassList){
          if(this.catPassList[i].catid==row.catid){
            return this.catPassList[i].catname+'('+this.catPassList[i].catunit+')';
          }
        }
        return "异常";
      },
      supFormat(row, index){
        console.log(row);
        for(let i in this.supPassList){
          if(this.supPassList[i].supid==row.supplier){
            return this.supPassList[i].supname;
          }
        }
        return "异常";
      },
    }
  }

</script>
<style>

</style>
