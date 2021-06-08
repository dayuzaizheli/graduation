<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addCatBefore">新增品类</el-button>
      <el-input style="width:80%; float:right;" v-model="catName" placeholder="请输入搜索的关键字">
        <template slot="append">
          <el-button icon="el-icon-search" circle @click="getCatByName"></el-button>
        </template>
      </el-input>
    </el-header>

    <el-dialog title="新增/编辑品类" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="cat" :rules="rules" ref="cat" label-width="100px" class="demo-ruleForm">
        <el-form-item label="品类名" prop="catname">
          <el-input v-model="cat.catname"></el-input>
        </el-form-item>
        <el-form-item label="品类单位" prop="catunit">
          <el-input v-model="cat.catunit"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('cat')">提交</el-button>
          <el-button @click="resetForm('cat')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

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
            <el-button @click="updCatBefore(scope.row)" size="small" type="primary" plain>编辑</el-button>
            <el-button size="small" type="warning" plain @click="delCatyId(scope.row)">删除</el-button>
            <el-button size="small" type="success" plain @click="updCatStatus(scope.row)">提交审核</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'category',
    data(){
      return{
        catList:[],
        dialogAddFormVisible:false,
        addOrUpdFlag:0,
        catName:'',
        cat:{
          catid:0,
          catname:'',
          catlevel:0,
          catsuper:'',
          catunit:'',
          catstatus:0
        },
        rules: {
          catname: [
            {required: true, message: '请输入品类名称', trigger: 'blur'},
            {min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur'}
          ],
          catunit: [
            {required: true, message: '请输入品类单位，建议：个、台、米、m......', trigger: 'blur'},
            {min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur'}
          ]
        }
      };
    },
    created() {
      axios.get('http://localhost:8888/testMaven/getAllCatBy',
        {
          params:{
            catName:''
          }
        }
      ).then(res=>{
        if(res.status == 200){
          //console.log(res.data);
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
        axios.get('http://localhost:8888/testMaven/getAllCatBy',
          {
            params:{
              catName:''
            }
          }
        ).then(res=>{
          if(res.status == 200){
            //console.log(res.data);
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
      //重置
      //TODO reset有bug
      resetForm(formName) {
        this.$refs[formName].resetFields();
        //this.getAllCat();
        console.log("reset运行了");
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('cat');
        done();
      },
      //新增品类前工作
      addCatBefore(){
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=0;
      },
      //添加品类
      addCat(formName){
        let te=new FormData();
        te.append("catName", this.cat.catname);
        te.append("catUnit", this.cat.catunit);
        axios.post('http://localhost:8888/testMaven/addCat', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='该品类已经存在'){
              this.$alert('该品类已存在', '提示', {
                confirmButtonText: '确定'
              });
            }else{
              this.resetForm('cat');
              this.dialogAddFormVisible=false;
              this.getAllCat();
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //编辑品类前工作
      updCatBefore(row, index){
        if(row.catstatus==0 || row.catstatus==2){
          this.dialogAddFormVisible=true;
          this.addOrUpdFlag=1;
          this.cat=row;
        }else{
          this.$alert('只有状态为未提交和驳回的品类可以编辑', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      //编辑品类
      updCat(formName){
        let te=new FormData();
        te.append("catId", this.cat.catid);
        te.append("catName", this.cat.catname);
        te.append("catUnit", this.cat.catunit);
        axios.post('http://localhost:8888/testMaven/updateCat', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='该品类已经存在'){
              this.$alert('该品类已存在', '提示', {
                confirmButtonText: '确定'
              });
            }else{
              this.resetForm('cat');
              this.dialogAddFormVisible=false;
              this.getAllCat();

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
            if(this.addOrUpdFlag==0){
              this.addCat('cat');
            }else if(this.addOrUpdFlag==1){
              this.updCat('cat');
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //删除品类
      delCatyId(row, index){
        axios.get('http://localhost:8888/testMaven/delCat',
          {
            params:{
              catId:row.catid
            }
          },
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='删除品类信息成功'){
              this.$alert('名为'+row.catname+'的品类已删除', '提示', {
                confirmButtonText: '确定'
              });
              this.getAllCat();
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
      //通过名字搜索品类
      getCatByName(){
        let te=this.catName;
        axios.get('http://localhost:8888/testMaven/getAllCatBy',
          {
            params:{
              catName:te
            }
          }
        ).then(res=>{
          if(res.status == 200){
            //console.log(res.data);
            this.catList=res.data.catList;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //修改状态
      updCatStatus(row, index){
        if(row.catstatus==0 || row.catstatus==2){
          axios.get('http://localhost:8888/testMaven/updateCatStatus',
            {
              params:{
                catId:row.catid,
                catStatus:1,
                memPos:'ptyg'
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
        }else{
          this.$alert('只有状态为未提交或驳回的品类能够提交', '提示', {
            confirmButtonText: '确定'
          });
        }
      }
    }
  }
</script>
