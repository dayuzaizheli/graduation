<template>
  <el-container>
    <el-header style="height: 25px; padding-left: 100px;">
      <el-input style="width:80%;" v-model="purName" placeholder="请输入方案名">
        <template slot="append">
          <el-button icon="el-icon-search" circle @click="getResByPurName"></el-button>
        </template>
      </el-input>
    </el-header>

    <el-main>
      <el-table :data="resList">
        <el-table-column prop="resname" label="采购结果">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="500">
          <template slot-scope="scope">
            <el-button @click="goto(scope.row, 'resCategory')" size="small" type="primary" plain>查看</el-button>
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
    name: 'result',
    data(){
      return{
        resList:[],
        purName:''
      };
    },
    created() {
      axios.get('http://localhost:8888/testMaven/getAllRes',
      ).then(res=>{
        if(res.status == 200){
          console.log(res.data);
          if(res.data.info=='Success'){
            this.resList=res.data.resList;
          }else{
            console.log(res.data.info);
          }
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods: {
      //跳转品类页面
      goto(row, path) {
        this.$router.push({name: path, params: {resdId: row.resid}});
      },
      //根据采购方案名获得采购结果
      getResByPurName(){
        let purName=this.purName;
        axios.get('http://localhost:8888/testMaven/getResByPurName',
          {
            params:{
              purName:purName
            }
          }
        ).then(res=>{
          if(res.status == 200){
            console.log(res.data);
            if(res.data.info=='Success'){
              this.resList=res.data.resList;
            }else{
              console.log(res.data.info);
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      }
    }
  }
</script>
