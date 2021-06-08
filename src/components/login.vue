<template>

  <div id="login">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" prop="count">
        <el-input type="text" v-model="ruleForm.count" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')" style="margin-left: 145px;margin-top: 50px">登录</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
  import axios from "axios";
  export default {
    name: 'login',
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          power:[["请购管理","我的请购"],["请购管理","请购审批"],["采购结果"]],
          pass: '',
          checkPass: '',
          count:''
        },
        rules: {
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
          count: [
            {required: true, message: '请输入账号', trigger: 'blur'}
          ]
        },
        posResp:{
          qggl:false,
          wdqg:false,
          qgsp:false,
          cgfa:false,
          wdfa:false,
          fasp:false,
          cgjg:false,
          ddgl:false,
          htgl:false,
          plgl:false,
          wdpl:false,
          plsp:false,
          gysgl:false,
          wdgys:false,
          gyssp:false,
          tjfx:false,
          xtgl:false,
          cygl:false,
          gwgl:false,
        }
      };
    },
    mounted() {

    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.login();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //登录成功与否判断
      login(){
        let te=new FormData();
        te.append("memName", this.ruleForm.count);
        te.append("memPwd", this.ruleForm.pass);
        axios.post('http://localhost:8888/testMaven/memLogin',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            if(res.data.info==="Success"){
              //存进session
              sessionStorage.setItem("memId", res.data.memId);
              sessionStorage.setItem("memName", res.data.memName);
              sessionStorage.setItem("memPos", res.data.memPos);

              this.powerCon(res.data.posResp);
              //this.$router.push("/MyHome");
              let tes=this.posResp;
              sessionStorage.setItem("posResp", JSON.stringify(tes));
              this.$router.push({name: 'MyHome'});
              //this.$router.push({name: 'MyHome', params: {posResp: this.posResp}});
            }else{
              this.$alert("账号或密码错误，请重新输入","提示",{
                confirmButtonText: '确定'
              })
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //用户权限控制
      powerCon(posResp){
        for(let i in posResp){
          let powi=posResp[i];
          let powip=powi.powip;
          for(let j in powip){
            let powipi=powip[j];
            if(powipi==="请购管理"){
              this.posResp.qggl=true;
            }
            if(powipi==="我的请购"){
              this.posResp.wdqg=true;
            }
            if(powipi==="请购审批"){
              this.posResp.qgsp=true;
            }
            if(powipi==="采购方案"){
              this.posResp.cgfa=true;
            }
            if(powipi==="我的方案"){
              this.posResp.wdfa=true;
            }
            if(powipi==="方案审批"){
              this.posResp.fasp=true;
            }
            if(powipi==="采购结果"){
              this.posResp.cgjg=true;
            }
            if(powipi==="订单管理"){
              this.posResp.ddgl=true;
            }
            if(powipi==="合同管理"){
              this.posResp.htgl=true;
            }
            if(powipi==="品类管理"){
              this.posResp.plgl=true;
            }
            if(powipi==="我的品类"){
              this.posResp.wdpl=true;
            }
            if(powipi==="品类审批"){
              this.posResp.plsp=true;
            }
            if(powipi==="供应商管理"){
              this.posResp.gysgl=true;
            }
            if(powipi==="我的供应商"){
              this.posResp.wdgys=true;
            }
            if(powipi==="供应商审批"){
              this.posResp.gyssp=true;
            }
            if(powipi==="统计分析"){
              this.posResp.tjfx=true;
            }
            if(powipi==="系统管理"){
              this.posResp.xtgl=true;
            }
            if(powipi==="成员管理"){
              this.posResp.cygl=true;
            }
            if(powipi==="岗位管理"){
              this.posResp.gwgl=true;
            }
          }
        }
      }
    }
  }

</script>
<style>
  #login{
    margin:200px auto;
    width:500px;
    height:300px;
    padding-top: 100px;
    padding-right:30px;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }
</style>
