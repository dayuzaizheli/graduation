<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addSupBefore">新增供应商</el-button>
    </el-header>

    <el-dialog title="新增/编辑供应商" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="sup" :rules="rules" ref="sup" label-width="100px" class="demo-ruleForm">
        <el-form-item label="供应商名" prop="supname">
          <el-input v-model="sup.supname"></el-input>
        </el-form-item>
        <el-form-item label="采购接口人" prop="supagent">
          <el-select v-model="sup.supagent" filterable placeholder="请选择采购接口人">
            <el-option
              v-for="item in memList"
              :key="item.memid"
              :label="item.memrealname"
              :value="item.memid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('sup')">提交</el-button>
          <el-button @click="resetForm('sup')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-main>
      <el-table :data="supList">
        <el-table-column prop="supname" label="供应商名" width="350">
        </el-table-column>
        <el-table-column prop="supagent" label="供应商接口人" width="250" :formatter="supAgeFormat">
        </el-table-column>
        <el-table-column prop="supnumber" label="供应商评分" width="250">
        </el-table-column>
        <el-table-column prop="supstatus" label="供应商状态" :formatter="staFormat">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="400">
          <template slot-scope="scope">
            <el-button @click="updSupBefore(scope.row)" size="small" type="primary" plain>编辑</el-button>
            <el-button size="small" type="warning" plain @click="delSupById(scope.row)">删除</el-button>
            <el-button size="small" type="success" plain @click="updSupStatus(scope.row)">提交审核</el-button>
            <el-button size="small" type="info" plain @click="goto(scope.row,'catSupplier')">品类编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'supplier',
    data(){
      return{
        supList:[],
        memList:[],
        dialogAddFormVisible:false,
        addOrUpdFlag:0,
        catName:'',
        sup:{
          supid:0,
          supname:'',
          supagent:'',
          supnumber:0,
          supregtime:'',
          supstatus:0
        },
        rules: {
          supname: [
            {required: true, message: '请输入公司名', trigger: 'blur'},
            {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
          ],
          supagent: [
            {required: true, message: '请选择采购接口人', trigger: 'blur'}
          ]
        }
      };
    },
    created() {
      let memId=sessionStorage.getItem("memId");
      let memPos=sessionStorage.getItem("memPos");
      axios.get('http://localhost:8888/testMaven/getAllSupBy',
        {
          params:{
            memPos:memPos,
            memId:memId
          }
        }
      ).then(res=>{
        if(res.status == 200){
          this.supList=res.data.supList;
          let te = new FormData();
          te.append('memName','');
          te.append('memPwd','');
          te.append('memIsInner',0);
          let that = this;
          axios.post('http://localhost:8888/testMaven/getMemBy',te
          ).then(res=>{
            if(res.status == 200){
              that.memList=res.data.memberList;
            }
          }).catch(err=>{
            console.log(err);
          });
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods:{
      //TODO 应该显示品类状态
      //查询数据的封装
      getAllSup(){
        axios.get('http://localhost:8888/testMaven/getAllSupBy',
          {
            params:{
              memPos:'cgjl',
              memId:2
            }
          }
        ).then(res=>{
          if(res.status == 200){
            this.supList=res.data.supList;
            this.getAllMemSup();
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //状态格式化
      staFormat(row, index){
        if(row.supstatus==0){
          return "未提交";
        }else if(row.supstatus==1){
          return "审核中";
        }else if(row.supstatus==2){
          return "驳回";
        }else if(row.supstatus==3){
          return "审核通过";
        }else{
          return "异常";
        }
      },
      //采购接口人格式化
      supAgeFormat(row, index){
        let mem={};
        for(let i in this.memList){
          mem=this.memList[i];
          if(row.supagent==mem.memid){
            return mem.memrealname;
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
        this.resetForm('sup');
        done();
      },
      //获取所有采购接口人
      getAllMemSup(){
        let te = new FormData();
        te.append('memName','');
        te.append('memPwd','');
        te.append('memIsInner',0);
        axios.post('http://localhost:8888/testMaven/getMemBy',te
        ).then(res=>{
          if(res.status == 200){
            this.memList=res.data.memberList;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //新增供应商前工作
      addSupBefore(){
        this.getAllMemSup();
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=0;
      },
      //添加供应商
      addSup(formName){
        let te = JSON.stringify(this.sup);
        axios.post('http://localhost:8888/testMaven/addSup', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='该供应商已存在'){
              this.$alert('该供应商已存在', '提示', {
                confirmButtonText: '确定'
              });
            }else{
              this.resetForm('sup');
              this.dialogAddFormVisible=false;
              this.getAllSup();
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //编辑供应商前工作
      updSupBefore(row, index){
        if(row.supstatus==0 || row.supstatus==2){
          this.getAllMemSup();
          this.dialogAddFormVisible=true;
          this.addOrUpdFlag=1;
          this.sup=row;
        }else{
          this.$alert('只有状态为未提交和驳回的供应商可以编辑', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      //编辑供应商
      updSup(formName){
        let te=JSON.stringify(this.sup);
        axios.post('http://localhost:8888/testMaven/updSup', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='该供应商已存在'){
              this.$alert('该供应商已存在', '提示', {
                confirmButtonText: '确定'
              });
            }else{
              this.resetForm('sup');
              this.dialogAddFormVisible=false;
              this.getAllSup();
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
              this.addSup('sup');
            }else if(this.addOrUpdFlag==1){
              this.updSup('sup');
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //删除供应商
      delSupById(row, index){
        axios.get('http://localhost:8888/testMaven/delSup',
          {
            params:{
              supId:row.supid
            }
          },
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data=='Success'){
              this.$alert('名为'+row.supname+'的供应商已删除', '提示', {
                confirmButtonText: '确定'
              });
              this.getAllSup();
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
      //修改状态
      updSupStatus(row, index){
        if(row.supstatus==0 || row.supstatus==2){
          let memPos=sessionStorage.getItem("memPos");
          let te=new FormData();
          te.append("memPos",memPos);
          te.append("supStatus",1);
          te.append("supId",row.supid);
          axios.post('http://localhost:8888/testMaven/updateSupStatus',te,
            {
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
            }
          ).then(res=>{
            if(res.status == 200){
              this.getAllSup();
            }
          }).catch(err=>{
            console.log(err);
          });
        }else{
          this.$alert('只有状态为未提交或驳回才能提交', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      //页面跳转
      goto(row,path){
        //this.$router.push({ name: path, query: { supid: row.supid} });
        this.$router.push({name: path, params: {supid: row.supid}});
      },
    }
  }
</script>
