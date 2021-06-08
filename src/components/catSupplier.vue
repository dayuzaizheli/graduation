<template>
  <el-container>
    <el-page-header @back="goBack" content="供应商品类" >
      {{tet}}
    </el-page-header>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addCatSupBefore">添加品类</el-button>
    </el-header>

    <el-dialog title="添加品类信息" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="catSup" :rules="rules" ref="catSup" label-width="100px" class="demo-ruleForm">
        <el-form-item label="品类" prop="catid">
          <el-select v-model="catSup.catid" filterable placeholder="请选择公司所能提供的品类">
            <el-option
              v-for="item in catList"
              :key="item.catid"
              :label="item.catname"
              :value="item.catid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
        <el-input v-model="catSup.price" type="number" step="0.001"></el-input>
      </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('catSup')">提交</el-button>
          <el-button @click="resetForm('catSup')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-main>
      <el-table :data="catSupList">
        <el-table-column prop="catid" label="品类" width="350" :formatter="catFormat">
        </el-table-column>
        <el-table-column prop="price" label="价格" >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="300">
          <template slot-scope="scope">
            <el-button size="small" type="warning" plain @click="delCatSupById(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'catSupplier',
    //inject: ['reload'],
    data() {
      let checkPrice = (rule,value,callback)=>{
        if(value){
          let rgx = /^\d+(\.\d{1,3})?$/;
          if(value.match(rgx)==null){
            return callback(new Error('请检查输入格式，不能为空，正数且最多三位小数'))
          }else{
            callback();
          }
        }
      };
      return {
        catSupList:[],
        test:this.$route.params.supid,
        tet:'',
        dialogAddFormVisible:false,
        addOrUpdFlag:0,
        catList:[],
        catSup:{
          catsupid:'',
          catid:'',
          supid:'',
          price:''
        },
        computed: {
          tet: function () {
            this.getAllSupCat();
            this.getAllSupCat();
            return this.$route.params.supid;
          }
        },
        rules: {
          catid: [
            {required: true, message: '请选择公司所能提供的品类', trigger: 'blur'}
          ],
          price: [
            {required:true,message:'请检查输入格式，不能为空，正数且最多三位小数',trigger:'change'},
            {validator:checkPrice, trigger:'change'}
          ]
        }
      };
    },
    //生命周期函数，好像没用，后面测试一下项目第一次加载有没有用
    created(){
      let supid=this.test;
      axios.get('http://localhost:8888/testMaven/getAllCatSupBy',
        {
          params:{
            supId:supid
          }
        }
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.catSupList=res.data.catList;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
      axios.get('http://localhost:8888/testMaven/getAllCatPass',
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.catList=res.data.catList;
            console.log(res.data.catList);
          }else{
          }
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods: {
      //页面回转
      goBack() {
        this.$router.push('/supplier').catch(err=>{});
      },
      //根据供应商ID获取所有供应商品类
      getAllSupCat(){
        let supid=this.test;
        axios.get('http://localhost:8888/testMaven/getAllCatSupBy',
          {
            params:{
              supId:supid
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.catSupList=res.data.catList;
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //获取所有审核通过的品类
      getAllCatPass(){
        axios.get('http://localhost:8888/testMaven/getAllCatPass',
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.catList=res.data.catList;
              //console.log(res.data.catList);
            }else{
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //品类格式化显示
      catFormat(row, index){
        let cat={};
        for(let i in this.catList){
          cat=this.catList[i];
          if(row.catid==cat.catid){
            console.log(cat.catname);
            return cat.catname;
          }
        }
        return "异常";
      },
      //重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
        console.log("reset运行了");
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('catSup');
        done();
      },
      //新增品类供应商前工作
      addCatSupBefore(){
        this.getAllCatPass();
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=0;
      },
      //添加品类供应商
      addCatSup(formName){
        this.catSup.supid=this.test;
        let te = JSON.stringify(this.catSup);
        axios.post('http://localhost:8888/testMaven/addCatSup', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.resetForm('catSup');
            this.dialogAddFormVisible=false;
            this.getAllSupCat();
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //编辑前工作
      updCatSupBefore(row, index){
        this.getAllSupCat();
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=1;
        this.catSup=row;
      },
      //编辑供应商
      updCatSup(formName){
        let te=new FormData();
        te.append("catSupId", this.catSup.catsupid);
        te.append("price", this.catSup.price);
        axios.post('http://localhost:8888/testMaven/updCatSup', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.resetForm('catSup');
            this.dialogAddFormVisible=false;
            this.getAllSupCat();
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //提交按钮，调用add或者update
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.addCatSup('catSup');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //删除
      delCatSupById(row, index){
        axios.get('http://localhost:8888/testMaven/delCatSupById',
          {
            params:{
              catSupId:row.catsupid
            }
          },
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          this.getAllSupCat();
          if(res.status == 200){
            this.$alert('该品类已从公司列表里删除', '提示', {
              confirmButtonText: '确定'
            });
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
