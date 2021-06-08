<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addConBefore">新增合同</el-button>
      <el-input style="width:80%; float:right;" v-model="conName" placeholder="请输入合同名">
        <template slot="append">
          <el-button icon="el-icon-search" circle @click="getConByName"></el-button>
        </template>
      </el-input>
    </el-header>

    <el-dialog title="新增/编辑品类" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="con" :rules="rules" ref="con" label-width="100px" class="demo-ruleForm">
        <el-form-item label="合同名" prop="conname">
          <el-input v-model="con.conname"></el-input>
        </el-form-item>
        <el-form-item label="合同供应商" prop="consupplier">
          <el-select v-model="con.consupplier" filterable placeholder="请选择合同供应商">
            <el-option
              v-for="item in supList"
              :key="item.supid"
              :label="item.supname"
              :value="item.supid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同内容" prop="concontent">
          <el-input type="textarea" v-model="con.concontent"></el-input>
        </el-form-item>
        <el-form-item label="原有额度(元)" prop="conoriquota">
          <el-input v-model="con.conoriquota" type="number" step="0.001"></el-input>
        </el-form-item>
        <el-form-item label="剩余额度(元)" prop="consurquota">
          <el-input v-model="con.consurquota" type="number" step="0.001"></el-input>
        </el-form-item>
        <el-form-item label="签订时间" prop="consgintime">
          <el-date-picker
            v-model="con.consgintime"
            type="datetime"
            placeholder="选择日期时间"
            default-time="00:00:00">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开始时间" prop="conbegintime">
          <el-date-picker
            v-model="con.conbegintime"
            type="datetime"
            placeholder="选择日期时间"
            default-time="00:00:00">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="conendtime">
          <el-date-picker
            v-model="con.conendtime"
            type="datetime"
            placeholder="选择日期时间"
            default-time="00:00:00">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('con')">提交</el-button>
          <el-button @click="resetForm('con')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-main>
      <el-table :data="conList">
        <el-table-column prop="conname" label="合同名" >
        </el-table-column>
        <el-table-column prop="consupplier" label="合同供应商" :formatter="supFormat">
        </el-table-column>
        <el-table-column prop="concontent" label="合同内容" >
        </el-table-column>
        <el-table-column prop="conoriquota" label="原有额度(元)" >
        </el-table-column>
        <el-table-column prop="consurquota" label="剩余额度(元)" >
        </el-table-column>
        <el-table-column prop="consgintime" label="签订时间" :formatter="timeFormat">
        </el-table-column>
        <el-table-column prop="conbegintime" label="开始时间" :formatter="timeFormat">
        </el-table-column>
        <el-table-column prop="conendtime" label="结束时间" :formatter="timeFormat">
        </el-table-column>
        <el-table-column prop="constatus" label="状态" :formatter="staFormat">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="250">
          <template slot-scope="scope">
            <el-button @click="updConBefore(scope.row)" size="small" type="primary" plain>编辑</el-button>
            <el-button size="small" type="warning" plain @click="delConById(scope.row)">删除</el-button>
            <el-button size="small" type="success" plain @click="updConStatus(scope.row)">确定</el-button>
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
    name: 'contract',
    data(){
      let checkQuota = (rule,value,callback)=>{
        if(value){
          let rgx = /^\d+(\.\d{1,3})?$/;
          if(value.match(rgx)==null){
            return callback(new Error('请检查输入格式，额度不能为空，正数且最多三位小数'))
          }else{
            callback();
          }
        }
      };
      return{
        conList:[],
        supList:[],
        dialogAddFormVisible:false,
        addOrUpdFlag:0,
        conName:'',
        con:{
          conid:'',
          conname:'',
          consupplier:'',
          concontent:'',
          conoriquota:'',
          consurquota:'',
          consgintime:'',
          conbegintime:'',
          conendtime:'',
          constatus:''
        },
        rules: {
          conname: [
            {required: true, message: '请输入合同名称', trigger: 'blur'},
            {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur'}
          ],
          consupplier: [
            {required: true, message: '请选择合同供应商', trigger: 'blur'}
          ],
          concontent: [
            {required: true, message: '请输入合同内容', trigger: 'blur'},
            {min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur'}
          ],
          conoriquota: [
            {required:true,message:'额度不能为空，正数且最多三位小数',trigger:'change'},
            {validator:checkQuota, trigger:'change'}
          ],
          consurquota: [
            {required:true,message:'额度不能为空，正数且最多三位小数',trigger:'change'},
            {validator:checkQuota, trigger:'change'}
          ],
          consgintime: [
            {required: true, message: '请选择合同签订时间', trigger: 'blur'}
          ],
          conbegintime: [
            {required: true, message: '请选择合同开始时间', trigger: 'blur'}
          ],
          conendtime: [
            {required: true, message: '请选择合同结束时间', trigger: 'blur'}
          ]
        }
      };
    },
    created() {
      let te=new FormData();
      te.append("conName",'');
      te.append("conSupplier",-1);
      te.append("conStatus",-1);
      axios.post('http://localhost:8888/testMaven/getAllConBy',te,
        {
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          }
        }
      ).then(res=>{
        if(res.status == 200){
          if(res.data.info='Success'){
            this.conList=res.data.conList;
          }
        }
      }).catch(err=>{
        console.log(err);
      });
      //供应商信息
      axios.get('http://localhost:8888/testMaven/getAllSupPass',
      ).then(res=>{
        if(res.status == 200){
          this.supList=res.data.supList;
          console.log(res.data.supList);
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods:{
      //TODO 应该显示品类状态
      //查询数据的封装
      getAllCon(){
        let te=new FormData();
        te.append("conName",'');
        te.append("conSupplier",-1);
        te.append("conStatus",-1);
        axios.post('http://localhost:8888/testMaven/getAllConBy',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            }
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.conList=res.data.conList;
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //查询供应商的封装
      getAllSup(){
        axios.get('http://localhost:8888/testMaven/getAllSupPass',
        ).then(res=>{
          if(res.status == 200){
            this.supList=res.data.supList;
            console.log(res.data.supList);
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //格式化
      timeFormat(row, col, cell, index){
        return moment(cell).format('YYYY-MM-DD');
      },
      staFormat(row, index){
        if(row.constatus==0){
          return "草稿";
        }else if(row.constatus==1){
          return "已确定";
        }else{
          return "异常";
        }
      },
      supFormat(row, index){
        for(let i in this.supList){
          if(this.supList[i].supid==row.consupplier){
            return this.supList[i].supname;
          }
        }
        return "供应商异常";
      },
      //重置
      //TODO reset有bug
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('con');
        done();
      },
      //新增品类前工作
      addConBefore(){
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=0;
      },
      //添加品类
      addCon(formName){
        this.con.constatus=0;
        let te=JSON.stringify(this.con);
        axios.post('http://localhost:8888/testMaven/addCon', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.resetForm('con');
            this.dialogAddFormVisible=false;
            this.getAllCon();
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //编辑品类前工作
      updConBefore(row, index){
        if(row.constatus==0){
          this.dialogAddFormVisible=true;
          this.addOrUpdFlag=1;
          this.con=row;
        }else{
          this.$alert('只有未确认的合同可以编辑', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      //编辑品类
      updCon(formName){
        this.con.constatus=0;
        let te=JSON.stringify(this.con);
        axios.post('http://localhost:8888/testMaven/updCon', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          this.resetForm('con');
          this.dialogAddFormVisible=false;
          this.getAllCon();
        }).catch(err=>{
          console.log(err);
        });
      },
      //提交按钮，调用add或者update
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.addOrUpdFlag==0){
              this.addCon('con');
            }else if(this.addOrUpdFlag==1){
              this.updCon('con');
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //删除品类
      delConById(row, index){
        axios.get('http://localhost:8888/testMaven/delCon',
          {
            params:{
              conId:row.conid
            }
          },
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllCon();
            this.$alert('名为'+row.conname+'的合同已删除', '提示', {
            confirmButtonText: '确定'
            });
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //通过名字搜索品类
      getConByName(){
        let te=new FormData();
        console.log(this.conName);
        te.append("conName",this.conName);
        te.append("conSupplier",-1);
        te.append("conStatus",-1);
        axios.post('http://localhost:8888/testMaven/getAllConBy',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info='Success'){
              this.conList=res.data.conList;
            }else{
              this.conList=[];
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //修改状态
      //TODO 在修改前给出提示：合同确认后将被使用且不能修改，是否确认
      updConStatus(row, index){
        if(row.constatus==0){
          let mempos=sessionStorage.getItem("memPos");
          let te=new FormData();
          te.append("memPos",mempos);
          te.append("conStatus",1);
          te.append("conId",row.conid);
          axios.post('http://localhost:8888/testMaven/updateConStatus',te,
            {
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
            }
          ).then(res=>{
            if(res.status == 200){
              this.getAllCon();
            }
          }).catch(err=>{
            console.log(err);
          });
        }else{
          this.$alert('该合同已确认', '提示', {
            confirmButtonText: '确定'
          });
        }
      }
    }
  }
</script>
