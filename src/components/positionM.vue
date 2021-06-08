<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addBefore">新增岗位</el-button>
    </el-header>

    <el-dialog title="新增岗位" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="posForm" :rules="rules" ref="posForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="职位名" prop="pospower">
          <el-input v-model="posForm.pospower"></el-input>
        </el-form-item>
        <el-form-item label="职位简称" prop="posname">
          <el-input v-model="posForm.posname"></el-input>
        </el-form-item>
        <el-form-item label="职位备注" prop="posremark">
          <el-input v-model="posForm.posremark"></el-input>
        </el-form-item>
        <el-form-item label="职位权限" prop="posresp">
          <el-cascader
            v-model="posForm.posresp"
            :options="respOptions"
            :props="{ expandTrigger:'hover', multiple:true }"
            @change="changeAfter"></el-cascader>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('posForm')">提交</el-button>
          <el-button @click="resetForm('posForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-main>
      <el-table :data="posList">
        <el-table-column prop="pospower" label="职位名" width="250">
        </el-table-column>
        <el-table-column prop="posname" label="职位简称" width="140">
        </el-table-column>
        <el-table-column prop="posremark" label="职位备注" width="250">
        </el-table-column>
        <el-table-column prop="posresp" label="职位权限" >
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'positionM',
    data(){
      return{
        dialogAddFormVisible:false,
        posList:[],
        posForm:{
          posid:'',
          posname:'',
          posresp:'',
          pospower:'',
          posremark:''
        },
        rules: {
          pospower:[
            {required: true, message: '请输入岗位名', trigger: 'blur'},
            {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
          ],
          posname:[
            {required: true, message: '请输入岗位简称，建议名字的首字母缩写，例如：采购经理为cgjl', trigger: 'blur'},
            {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
          ],
          posremark:[
            {required: true, message: '请输入岗位备注', trigger: 'blur'},
            {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur'}
          ],
          posresp:[
            {required: true, message: '请选择岗位权限', trigger: 'blur'}
          ]
        },
        respOptions: [
          {
            value: '请购管理',
            label: '请购管理',
            children:[
              {
                value: '我的请购',
                label: '我的请购',
              },
              {
                value: '请购审批',
                label: '请购审批',
              }
            ]
          },
          {
            value: '采购方案',
            label: '采购方案',
            children:[
              {
                value: '我的方案',
                label: '我的方案',
              },
              {
                value: '方案审批',
                label: '方案审批',
              }
            ]
          },
          {
            value: '采购结果',
            label: '采购结果',
          },
          {
            value: '订单管理',
            label: '订单管理',
          },
          {
            value: '合同管理',
            label: '合同管理',
          },
          {
            value: '品类管理',
            label: '品类管理',
            children:[
              {
                value: '我的品类',
                label: '我的品类',
              },
              {
                value: '品类审批',
                label: '品类审批',
              }
            ]
          },
          {
            value: '供应商管理',
            label: '供应商管理',
            children:[
              {
                value: '我的供应商',
                label: '我的供应商',
              },
              {
                value: '供应商审批',
                label: '供应商审批',
              }
            ]
          },
          {
            value: '统计分析',
            label: '统计分析',
          },
          {
            value: '系统管理',
            label: '系统管理',
            children:[
              {
                value: '成员管理',
                label: '成员管理',
              },
              {
                value: '岗位管理',
                label: '岗位管理',
              }
            ]
          }
        ]
      };
    },
    created() {
      axios.get('http://localhost:8888/testMaven/getAllPos'
      ).then(res=>{
        if(res.status == 200){
          this.posList=res.data.postionList;
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods:{
      changeAfter(){
        //console.log(this.posForm.posresp);
        //console.log(JSON.stringify(this.posForm.posresp));
      },
      //重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('posForm');
        done();
      },
      //查询数据的封装
      getAllPos(){
        axios.get('http://localhost:8888/testMaven/getAllPos'
        ).then(res=>{
          if(res.status == 200){
            this.posList=res.data.postionList;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //添加岗位前
      addBefore(){
        //this.resetForm('posForm');
        this.dialogAddFormVisible=true;
      },
      addPos(){
        this.posForm.posresp=JSON.stringify(this.posForm.posresp);
        let pos = JSON.stringify(this.posForm);
        axios.post('http://localhost:8888/testMaven/addPos', pos,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            //console.log(res.data);
            if(res.data.info=="Success"){
              this.getAllPos();
              this.dialogAddFormVisible=false;
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
            this.addPos();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>
