<template>
  <el-container>
    <el-page-header @back="goBack" content="方案详情" >

    </el-page-header>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addBefore" v-if="updFlag">添加品类</el-button>
    </el-header>

    <el-dialog title="添加/修改品类信息" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="purCat" :rules="rules" ref="purCat" label-width="100px" class="demo-ruleForm">
        <el-form-item label="品类" prop="catid" v-if="addOrUpdFlag">
          <el-select v-model="purCat.catid" filterable placeholder="请选择你所需要的品类" @change="getAllSupCan">
            <el-option
              v-for="item in catList"
              :key="item.catId"
              :label="item.catName+'('+item.catUnit+')'"
              :value="item.catId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="catsupplier">
          <el-select v-model="purCat.catsupplier" filterable placeholder="请选择该品类供应商" @change="setPrice">
            <el-option
              v-for="item in supList"
              :key="item.supId"
              :label="item.supName"
              :value="item">
              <span style="float: left">{{ item.supName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.price }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="catnum">
        <el-input v-model="purCat.catnum" type="number" step="0.001"></el-input>
      </el-form-item>
        <el-form-item label="总计" prop="total">
          <span style="float: left">{{ purCat.catnum*this.price }}</span>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('purCat')">提交</el-button>
          <el-button @click="resetForm('purCat')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-main>
      <el-table :data="purCatList">
        <el-table-column prop="catid" label="品类" :formatter="catFormat">
        </el-table-column>
        <el-table-column prop="catsupplier" label="供应商" :formatter="supFormat">
        </el-table-column>
        <el-table-column prop="catnum" label="数量" >
        </el-table-column>
        <el-table-column prop="total" label="总额" >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="300" v-if="updFlag">
          <template slot-scope="scope">
            <el-button @click="updBefore(scope.row)" size="small" type="primary" plain>编辑</el-button>
            <el-button size="small" type="warning" plain @click="delPurCatByID(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'purCategory',
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
        purCatList:[],
        test:this.$route.params.purId,
        updFlag:this.$route.params.updFlag,
        ArlFlag:this.$route.params.ArlFlag,
        dialogAddFormVisible:false,
        addOrUpdFlag:true,
        price:0,
        sup:'',
        pur:{},
        purCat:{
          purcatid:'',
          purid:'',
          catid:'',
          catnum:'',
          catsupplier:'',
          total:''
        },
        catList:[],
        catPassList:[],
        supList:[],
        supPassList:[],
        rules: {
          catid: [
            {required: true, message: '请选择你所需要的品类', trigger: 'blur'}
          ],
          catnum: [
            {required:true,message:'数量为正数且最多三位小数',trigger:'change'},
          ],
          catsupplier: [
            {required: true, message: '请选择该品类供应商', trigger: 'blur'}
          ]
        }
      };
    },
    //生命周期函数，好像没用，后面测试一下项目第一次加载有没有用
    created(){
      let purId=this.test;
      console.log("updFlag:"+this.updFlag);
      axios.get('http://localhost:8888/testMaven/getAllPurCatByPurId',
        {
          params:{
            purId:purId
          }
        }
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.purCatList=res.data.purCatList;
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
      setPrice(prams){
        this.price=prams.price;
        this.purCat.catsupplier=prams.supName;
        this.sup=prams.supId;
      },
      //页面回转
      goBack() {
        if(this.ArlFlag){
          //console.log("ArlFlag:"+this.ArlFlag);
          this.$router.push('/purApproval').catch(err=>{});
        }else{
          //console.log("ArlFlag:"+this.ArlFlag);
          this.$router.push('/purchase').catch(err=>{});
        }
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('purCat');
        done();
      },
      //重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //获取页面品类信息的封装
      getAllPurCat(){
        let purId=this.test;
        axios.get('http://localhost:8888/testMaven/getAllPurCatByPurId',
          {
            params:{
              purId:purId
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.purCatList=res.data.purCatList;
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //添加时获得品类信息的封装
      getAllCatCan(){
        axios.get('http://localhost:8888/testMaven/getColReqCatInfo',
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
      //选择品类后获得品类供应商信息
      getAllSupCan(){
        //应该数量同步变化
        for(let i in this.catList){
          if(this.catList[i].catId===this.purCat.catid){
            this.purCat.catnum=this.catList[i].catNum;
          }
        }
        //获取供应商信息
        let purid=this.test;
        let catid=this.purCat.catid;
        var that=this;
        axios.get('http://localhost:8888/testMaven/getCatSup',
          {
            params:{
              purId:purid,
              catId:catid
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.supList=res.data.supList;
            }else{
              this.$alert('该品类已没有可供选择的供应商', '提示', {
                confirmButtonText: '确定'
              });
              this.dialogAddFormVisible=false;
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
      supFormat(row, index){
        console.log(row);
        for(let i in this.supPassList){
          if(this.supPassList[i].supid==row.catsupplier){
            return this.supPassList[i].supname;
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
      addPurCat(formName){
        this.purCat.purid=this.test;
        this.purCat.catsupplier=this.sup;
        this.purCat.total=this.purCat.catnum*this.price;
        let te=JSON.stringify(this.purCat);
        axios.post('http://localhost:8888/testMaven/addPurCat',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllPurCat();
            this.resetForm('purCat');
            this.dialogAddFormVisible=false;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //修改前
      updBefore(row, index){
        this.purCat=row;
        this.price=row.total/row.catnum;
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=false;
      },
      //修改
      updPurCat(formName){
        let te=JSON.stringify(this.purCat);
        axios.post('http://localhost:8888/testMaven/updPurCat',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllPurCat();
            this.resetForm('purCat');
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
              this.addPurCat('purCat');
            }else{
              this.updPurCat('purCat');
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //删除
      delPurCatByID(row, index){
        axios.get('http://localhost:8888/testMaven/delPurCat',
          {
            params:{
              purCatId:row.purcatid
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='Success'){
              this.getAllPurCat();
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

