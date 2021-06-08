<template>
  <el-container>
    <el-page-header @back="goBack" content="请购单详情" >

    </el-page-header>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addBefore" v-if="updFlag">添加品类</el-button>
    </el-header>

    <el-dialog title="添加/修改品类信息" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="reqCat" :rules="rules" ref="reqCat" label-width="100px" class="demo-ruleForm">
        <el-form-item label="品类" prop="catid" v-if="addOrUpdFlag">
          <el-select v-model="reqCat.catid" filterable placeholder="请选择你所需要的品类">
            <el-option
              v-for="item in catList"
              :key="item.catid"
              :label="item.catname+'('+item.catunit+')'"
              :value="item.catid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="catnum">
          <el-input v-model="reqCat.catnum" type="number" step="0.001"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('reqCat')">提交</el-button>
          <el-button @click="resetForm('reqCat')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-main>
      <el-table :data="reqCatList">
        <el-table-column prop="catid" label="品类" :formatter="catFormat">
        </el-table-column>
        <el-table-column prop="catnum" label="数量" >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="300" v-if="updFlag">
          <template slot-scope="scope">
            <el-button @click="updBefore(scope.row)" size="small" type="primary" plain>编辑</el-button>
            <el-button size="small" type="warning" plain @click="delReqCatByID(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'reqCategory',
    //inject: ['reload'],
    data() {
      let checkNum = (rule,value,callback)=>{
        let val=value.class;
        if(value){
          let rgx = /^\d+(\.\d{1,3})?$/;
          if(value.match(rgx)==null){
            return callback(new Error('数量为正数且最多三位小数'))
          }else{
            callback();
          }
        }
      };
      return {
        reqCatList:[],
        test:this.$route.params.reqId,
        updFlag:this.$route.params.updFlag,
        ArlFlag:this.$route.params.ArlFlag,
        dialogAddFormVisible:false,
        addOrUpdFlag:true,
        req:{},
        reqCat:{
          reqcatid:'',
          reqid:'',
          catid:'',
          catnum:'',
          catpurchasenum:''
        },
        catList:[],
        catPassList:[],
        rules: {
          catid: [
            {required: true, message: '请选择你所需要的品类', trigger: 'blur'}
          ],
          catnum: [
            {required:true,message:'数量为正数且最多三位小数',trigger:'change'},
            {validator:checkNum, trigger:'change'}
          ]
        }
      };
    },
    //生命周期函数，好像没用，后面测试一下项目第一次加载有没有用
    created(){
      let reqId=this.test;
      axios.get('http://localhost:8888/testMaven/getReqByReqId',
        {
          params:{
            reqId:reqId
          }
        }
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='查询请购单品类信息成功'){
            this.reqCatList=res.data.reqCatList;
            this.req=res.data.req;
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
    },
    methods: {
      //页面回转
      goBack() {
        if(this.ArlFlag){
          console.log("ArlFlag:"+this.ArlFlag);
          this.$router.push('/reqApproval').catch(err=>{});
        }else{
          console.log("ArlFlag:"+this.ArlFlag);
          this.$router.push('/request').catch(err=>{});
        }
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('reqCat');
        done();
      },
      //重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //获取页面品类信息的封装
      getAllReqCat(){
        let reqId=this.test;
        axios.get('http://localhost:8888/testMaven/getReqByReqId',
          {
            params:{
              reqId:reqId
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='查询请购单品类信息成功'){
              this.reqCatList=res.data.reqCatList;
              this.req=res.data.req;
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //添加时获得品类信息的封装
      getAllCatCan(){
        let reqId=this.test;
        axios.get('http://localhost:8888/testMaven/getAllCatCan',
          {
            params:{
              reqId:reqId
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.catList=res.data.catList;
            }
          }
        }).catch(err=>{
          console.log(err);
        });
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
      //添加前
      addBefore(){
        this.getAllCatCan();
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=true;
      },
      //添加
      addReqCat(formName){
        let te=new FormData();
        te.append("reqId",this.test);
        te.append("catId",this.reqCat.catid);
        te.append("catNum",this.reqCat.catnum);
        axios.post('http://localhost:8888/testMaven/addReqCat',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllReqCat();
            this.resetForm('reqCat');
            this.dialogAddFormVisible=false;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //修改前
      updBefore(row, index){
        this.reqCat=row;
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=false;
      },
      //修改
      updReqCat(formName){
        let te=new FormData();
        te.append("reqCatId",this.reqCat.reqcatid);
        te.append("catNum",this.reqCat.catnum);
        axios.post('http://localhost:8888/testMaven/updateReqCat',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllReqCat();
            this.resetForm('reqCat');
            this.dialogAddFormVisible=false;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //提交按钮，调用add或者update
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.addOrUpdFlag){
              this.addReqCat('reqCat');
            }else{
              this.updReqCat('reqCat');
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //删除
      delReqCatByID(row, index){
        console.log(row.reqcatid);
        axios.get('http://localhost:8888/testMaven/delReqCat',
          {
            params:{
              reqCatId:row.reqcatid
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='Success'){
              this.getAllReqCat();
              this.$alert('已删除', '提示', {
                confirmButtonText: '确定'
              });
            }else{
              this.$alert('删除异常', '提示', {
                confirmButtonText: '确定'
              });
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      }

    }
  }

</script>
<style>

</style>
