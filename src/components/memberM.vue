<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" v-on:click="addMemBefore">新增用户</el-button>
    </el-header>

    <el-dialog title="新增/编辑用户" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="member" :rules="rules" ref="member" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户账号" prop="memname">
          <el-input v-model="member.memname"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="memrealname">
          <el-input v-model="member.memrealname"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="mempwd">
          <el-input v-model="member.mempwd"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="memphone">
          <el-input v-model="member.memphone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="mememail">
          <el-input v-model="member.mememail"></el-input>
        </el-form-item>
        <el-form-item label="内部人员?" prop="memisinner">
          <el-radio-group v-model="member.memisinner">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="用户职位" prop="memposition">
          <el-select v-model="member.memposition" placeholder="请选择用户职位">
            <el-option
              v-for="item in posList"
              :key="item.posid"
              :label="item.pospower"
              :value="item.posid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('member')">提交</el-button>
          <el-button @click="resetForm('member')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-main>
      <el-table :data="memList">
        <el-table-column prop="memname" label="账号" width="140">
        </el-table-column>
        <el-table-column prop="memrealname" label="用户姓名" width="140">
        </el-table-column>
        <el-table-column prop="memphone" label="联系方式" width="140">
        </el-table-column>
        <el-table-column prop="mememail" label="邮箱" width="200">
        </el-table-column>
        <el-table-column prop="memposition" label="职位" :formatter="posFormat">
        </el-table-column>
        <el-table-column prop="memisinner" label="公司内人员" :formatter="innerFormat">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="500">
          <template slot-scope="scope">
            <el-button @click="updMemBefore(scope.row)" size="small" type="primary" plain>编辑</el-button>
            <el-button size="small" type="warning" plain @click="delMemById(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'memberM',
    data(){
      var checkPhone = function (rule, value, callback) {
        if (value === '') {
          callback(new Error('手机号不能为空'))
        } else if (!/^1\d{10}$/.test(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      };
      return{
        memList:[],
        dialogAddFormVisible:false,
        addOrUpdFlag:0,
        posList:[],
        member:{
          memid:0,
          memname:'',
          memrealname:'',
          mempwd:'123456',
          memphone:'',
          mememail:'',
          memisinner:'1',
          memposition:''
        },
        rules: {
          memname:[
            {required: true, message: '请输入用户账号', trigger: 'blur'},
            {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
          ],
          memrealname:[
            {required: true, message: '请输入用户真实姓名', trigger: 'blur'},
            {min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur'}
          ],
          mempwd:[
            {required: true, message: '请输入用户密码', trigger: 'blur'},
            {min: 6, max: 8, message: '长度在 6 到 8 个字符', trigger: 'blur'}
          ],
          memphone:[
            {required: true, message: '请输入用户手机号', trigger: 'blur'},
            { validator: checkPhone, trigger: ['blur', 'change'] }
          ],
          mememail:[
            {required: true, message: '请输入用户邮箱', trigger: 'blur'},
            {type:'email', message: '请输入正确的邮箱', trigger: ['blur', 'change'] }
          ],
          memisinner:[
            { required: true, message: '请选择用户是否内部人员', trigger: 'change' }
          ],
          memposition:[
            { required: true, message: '请选择用户职位信息', trigger: 'change' }
          ],
        }
      };
    },
    created() {
      let te = new FormData();
      te.append('memName','');
      te.append('memPwd','');
      te.append('memIsInner',-1);
      axios.post('http://localhost:8888/testMaven/getMemBy',te
      ).then(res=>{
        if(res.status == 200){
          console.log(res.data);
          this.memList=res.data.memberList;
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods:{
      //查询数据的封装
      getAllMem(){
        let te = new FormData();
        te.append('memName','');
        te.append('memPwd','');
        te.append('memIsInner',-1);
        axios.post('http://localhost:8888/testMaven/getMemBy',te
        ).then(res=>{
          if(res.status == 200){
            console.log(res.data);
            this.memList=res.data.memberList;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //职位信息格式化显示
      posFormat(row, index){
        if(row.memposition==7){
          return '采购经理';
        }else if(row.memposition==9){
          return '采购接口人';
        }else if(row.memposition==8){
          return '普通员工';
        }else if(row.memposition==10){
          return 'CEO';
        }else if(row.memposition==6){
          return 'admin';
        }else{
          return '职位异常';
        }
      },
      //是否内部人员格式化显示
      innerFormat(row, index){
        if(row.memisinner==1){
          return '是';
        }else if(row.memisinner==0){
          return '否';
        }else{
          return '异常';
        }
      },
      //获得职位信息
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
      //重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //表单点击右上角关闭时的操作
      handleClose(done){
        this.resetForm('member');
        done();
      },
      //添加用户前的工作
      addMemBefore(formName){
        this.getAllPos();
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=0;
      },
      //添加用户
      addMem(formName){
        //console.log(this.member);
        //let mem = new FormData();
        //for(let key in this.member) {
        //  mem.append(key, this.member[key]);
        //  console.log(mem.get(key));
        //};
        let mem = JSON.stringify(this.member);
        axios.post('http://localhost:8888/testMaven/addMem', mem,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            //console.log(res.data);
            if(res.data=='该用户已经存在'){
              this.$alert('该账号已存在，请重新输入账号', '提示', {
                confirmButtonText: '确定'
              });
            }else if(res.data=='新增用户失败'){
              this.$alert('后端可能出了有点问题', '提示', {
                confirmButtonText: '确定'
              });
            }else if(res.data=='新增用户成功'){
              this.getAllMem();
              this.dialogAddFormVisible=false;
              this.resetForm('member');
            }else{
              console.log("出现预期之外的返回结果");
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //删除用户
      delMemById(row, index){
        axios.get('http://localhost:8888/testMaven/delMemById',
          {
            params:{
              memId:row.memid
            }
          }).then(res=>{
          if(res.status == 200){
            this.$alert('成功删除用户'+row.memname, '提示', {
              confirmButtonText: '确定'
            });
            this.getAllMem();
          }
        }).catch(err=>{
          console.log(err);
        })
      },
      //编辑用户前应该做的事
      updMemBefore(row, index){
        this.getAllPos();
        this.member=row;
        this.member.mempwd='123456';
        this.member.memisinner=''+row.memisinner+'';
        this.dialogAddFormVisible=true;
        this.addOrUpdFlag=1;
      },
      //编辑用户
      updMem(formName){
        let mem = JSON.stringify(this.member);
        console.log(mem);
        axios.post('http://localhost:8888/testMaven/updMem', mem,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllMem();
            this.dialogAddFormVisible=false;
          }
        }).catch(err=>{
          console.log(err);
        });
        this.resetForm('member');
      },
      //提交按钮，调用add或者update
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.addOrUpdFlag==0){
              this.addMem('member');
            }else if(this.addOrUpdFlag==1){
              this.updMem('member');
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>
