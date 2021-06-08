<template>
  <el-container>
    <el-page-header @back="goBack" content="订单详情" >

    </el-page-header>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addBefore" v-if="updFlag">添加品类</el-button>
    </el-header>

    <el-dialog title="添加/修改品类信息" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="ordCat" :rules="rules" ref="ordCat" label-width="100px" class="demo-ruleForm">
        <el-form-item label="品类" prop="catid" v-if="addOrUpdFlag">
          <el-select v-model="ordCat.catid" filterable placeholder="请选择品类" @change="setPrice">
            <el-option
              v-for="item in catList"
              :key="item.catId"
              :label="item.catName+'('+item.catUnit+')'"
              :value="item">
              <span style="float: left">{{ item.catName+'('+item.catUnit+')'}}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.catNum }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="catnum">
          <el-input v-model="ordCat.catnum" type="number" step="0.001"></el-input>
        </el-form-item>
        <el-form-item label="总计" prop="total">
          <span style="float: left">{{ ordCat.catnum*this.price }}</span>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ordCat')">提交</el-button>
          <el-button @click="resetForm('ordCat')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <el-main>
      <el-table :data="ordCatList">
        <el-table-column prop="catid" label="品类" :formatter="catFormat">
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
            <el-button size="small" type="warning" plain @click="delOrdCatByID(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'ordCategory',
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
        ordId:this.$route.params.ordId,
        updFlag:this.$route.params.updFlag,
        dialogAddFormVisible:false,
        addOrUpdFlag:true,
        ordCatList:[],
        catPassList:[],
        catList:[],
        price:0,
        ord:{},
        ordCat:{
          ordcatid:'',
          ordid:'',
          catid:'',
          catnum:'',
          total:''
        },
        realOrdId:'',
        rules:{
          catid: [
            {required: true, message: '请选择品类', trigger: 'blur'}
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
      let ordId=this.ordId;
      console.log("updFlag:"+this.updFlag);
      axios.get('http://localhost:8888/testMaven/getAllOrdCatBy',
        {
          params:{
            ordId:ordId
          }
        }
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.ordCatList=res.data.catList;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
      //获得品类
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
      //获取order信息
      axios.get('http://localhost:8888/testMaven/getOrderByID',
        {
          params:{
            ordId:ordId
          }
        }
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.ord=res.data.order;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods: {
      //页面回转
      goBack() {
        this.$router.push('/order').catch(err=>{});
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('ordCat');
        done();
      },
      //重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
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
      //获取所有订单品类
      getAllOrdCat(){
        let ordId=this.ordId;
        console.log("updFlag:"+this.updFlag);
        axios.get('http://localhost:8888/testMaven/getAllOrdCatBy',
          {
            params:{
              ordId:ordId
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.ordCatList=res.data.catList;
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //获取方案该供应商品类
      getAllPurSupCat(){
        let purId=this.ord.ordpurid;
        let supId=this.ord.ordsupplier;
        let ordId=this.ordId;
        axios.get('http://localhost:8888/testMaven/getPurSupCat',
          {
            params:{
              purId:purId,
              supId:supId,
              ordId:ordId
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
      //设置price
      setPrice(item){
        this.price=item.total/item.catNum;
        this.ordCat.catid=item.catName;
        this.realOrdId=item.catId;
      },
      //添加前
      addBefore(){
        this.getAllPurSupCat();
        this.addOrUpdFlag=true;
        this.dialogAddFormVisible=true;
      },
      addOrdCat(){
        this.ordCat.ordid=this.ordId;
        this.ordCat.catid=this.realOrdId;
        this.ordCat.total=this.ordCat.catnum*this.price;
        let te=JSON.stringify(this.ordCat);
        axios.post('http://localhost:8888/testMaven/addOrdCat',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllOrdCat();
            this.resetForm('ordCat');
            this.dialogAddFormVisible=false;
            this.price=0;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //修改前
      updBefore(row, index){
        this.ordCat=row;
        this.price=row.total/row.catnum;
        this.addOrUpdFlag=false;
        this.dialogAddFormVisible=true;
      },
      updOrdCat(){
        this.ordCat.total=this.ordCat.catnum*this.price;
        let te=JSON.stringify(this.ordCat);
        axios.post('http://localhost:8888/testMaven/updOrdCat',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllOrdCat();
            this.resetForm('ordCat');
            this.dialogAddFormVisible=false;
            this.price=0;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //删除
      delOrdCatByID(row, index){
        axios.get('http://localhost:8888/testMaven/delOrdCatById',
          {
            params:{
              catId:row.ordcatid
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data='Success'){
              this.getAllOrdCat();
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
      },
      //提交按钮，调用add或者update
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.addOrUpdFlag){
              this.addOrdCat();
            }else{
              this.updOrdCat();
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

    }
  }
</script>
<style>

</style>

