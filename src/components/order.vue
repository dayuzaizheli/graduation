<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" @click="addBefore" v-if="cgjlOrCgjkr">新建订单</el-button>
    </el-header>

    <el-dialog title="新建订单" :visible.sync="dialogAddFormVisible" :before-close="handleClose">
      <el-form :model="ord" :rules="rules" ref="ord" label-width="100px" class="demo-ruleForm">
        <el-form-item label="采购方案" prop="ordpurid">
          <el-select v-model="ord.ordpurid" filterable placeholder="请选择采购方案" @change="getAllSupCan">
            <el-option
              v-for="item in purList"
              :key="item.purid"
              :label="item.purname"
              :value="item.purid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="ordsupplier">
          <el-select v-model="ord.ordsupplier" filterable placeholder="请选择供应商" @change="getAllConCan">
            <el-option
              v-for="item in supList"
              :key="item.supid"
              :label="item.supname"
              :value="item.supid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同" prop="ordcon">
          <el-select v-model="ord.ordcon" filterable placeholder="请选择合同">
            <el-option
              v-for="item in conList"
              :key="item.conid"
              :label="item.conname"
              :value="item.conid">
              <span style="float: left">{{ item.conname }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.consurquota }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ord')">提交</el-button>
          <el-button @click="resetForm('ord')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <el-main>
      <el-table :data="ordList">
        <el-table-column prop="purName" label="方案名" width="250" >
        </el-table-column>
        <el-table-column prop="supName" label="供应商" >
        </el-table-column>
        <el-table-column prop="memRealName" label="创建人" >
        </el-table-column>
        <el-table-column prop="ordCreTime" label="创建时间" :formatter="dateFormat">
        </el-table-column>
        <el-table-column prop="conName" label="合同">
        </el-table-column>
        <el-table-column prop="total" label="总额">
        </el-table-column>
        <el-table-column prop="ordStatus" label="状态" :formatter="statusFormat">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="500">
          <template slot-scope="scope">
            <el-button @click="goto(scope.row, 'ordCategory')" size="small" type="primary" plain>查看/编辑</el-button>
            <el-button size="small" type="warning" plain @click="delOrdById(scope.row)" v-if="cgjlOrCgjkr">删除</el-button>
            <el-button size="small" type="success" plain @click="updStatus(scope.row)">确认</el-button>
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
    name: 'order',
    data(){
      return{
        ordList:[],
        memId:sessionStorage.getItem("memId"),
        memPos:sessionStorage.getItem("memPos"),
        ord:{
          ordid:'',
          ordname:'',
          ordpurid:'',
          ordsupplier:'',
          ordmember:'',
          ordmemsup:'',
          ordcretime:'',
          ordbegintime:'',
          ordcontimsup:'',
          ordcontime:'',
          ordstatus:'',
          total:'',
          ordcon:''
        },
        purList:[],
        supList:[],
        conList:[],
        dialogAddFormVisible:false,
        cgjlOrCgjkr:true,
        rules:{
          ordpurid:[
            {required: true, message: '请选择方案', trigger: 'blur'}
          ],
          ordsupplier:[
            {required: true, message: '请选择供应商', trigger: 'blur'}
          ],
          ordcon:[
            {required: true, message: '请选择合同', trigger: 'blur'}
          ],
        },
      };
    },
    created() {
      let memPos=sessionStorage.getItem("memPos");
      let memId=sessionStorage.getItem("memId");
      if(memPos=="cgjl"){
        this.cgjlOrCgjkr=true;
      }else{
        this.cgjlOrCgjkr=false;
      }
      axios.get('http://localhost:8888/testMaven/getAllOrdByMem',
        {
          params:{
            memId:memId,
            memPos:memPos,
          }
        }
      ).then(res=>{
        if(res.status == 200){
          console.log(res.data);
          if(res.data.info=='Success'){
            this.ordList=res.data.ordList;
          }else{
            console.log(res.data.info);
          }
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    methods: {
      //查询数据的封装
      getAllOrd() {
        //let memId=this.memId;
        //let memPos=this.memPos;
        let memPos=sessionStorage.getItem("memPos");
        let memId=sessionStorage.getItem("memId");
        axios.get('http://localhost:8888/testMaven/getAllOrdByMem',
          {
            params:{
              memId:memId,
              memPos:memPos,
            }
          }
        ).then(res=>{
          if(res.status == 200){
            console.log(res.data);
            if(res.data.info=='Success'){
              this.ordList=res.data.ordList;
            }else{
              console.log(res.data.info);
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      //reqStatus格式化显示
      statusFormat(row, index) {
        if (row.ordStatus == 0) {
          return '草稿';
        } else if (row.ordStatus == 1) {
          return '已确定';
        } else if (row.ordStatus == 2) {
          return '履行中';
        } else if (row.ordStatus == 3) {
          return '履行完毕';
        } else if (row.ordStatus == 4) {
          return '完成';
        } else {
          return '状态异常';
        }
      },
      //时间格式化显示
      dateFormat(row, index) {
        return moment(row.ordCreTime).format('YYYY-MM-DD HH:mm:ss');
      },
      delOrdById(row, index){
        if(row.ordStatus==0){
          axios.get('http://localhost:8888/testMaven/delOrdById',
            {
              params:{
                ordId:row.ordId
              }
            }
          ).then(res=>{
            if(res.status == 200){
              if(res.data=='Success'){
                this.getAllOrd();
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
        }else{
          this.$alert('只有状态为草稿的订单可以删除', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      updStatus(row){
        let sta=0;
        let memPos=sessionStorage.getItem("memPos");
        if(memPos=="cgjl"){
          if(row.ordStatus==0){
            sta=1;
          }else if(row.ordStatus==3){
            sta=4;
          }else if(row.ordStatus==4){
            this.$alert('该订单已结束', '提示', {
              confirmButtonText: '确定'
            });
          }else{
            sta=0;
            this.$alert('等待订单履行完毕才能再次确定', '提示', {
              confirmButtonText: '确定'
            });
          }
        }else if(memPos=="cgjkr"){
          if(row.ordStatus==1){
            sta=2;
          }else if(row.ordStatus==2){
            sta=3;
          }else{
            sta=0;
            this.$alert('该订单已履行完毕', '提示', {
              confirmButtonText: '确定'
            });
          }
        }else{
          sta=0;
        }
        if(sta==0){
        }else{
          let te=new FormData();
          te.append("memPos", memPos);
          te.append("status", sta);
          te.append("ordId", row.ordId);
          axios.post('http://localhost:8888/testMaven/updOrdStatus',te,
            {
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
            }
          ).then(res=>{
            if(res.status == 200){
              this.getAllOrd();
            }
          }).catch(err=>{
            console.log(err);
          });
        }
      },
      addBefore(){
        this.getAllPurCan();
        this.dialogAddFormVisible=true;
        this.supList=[];
        this.conList=[];
      },
      handleClose(done){
        this.resetForm('ord');
        done();
      },
      getAllPurCan(){
        axios.get('http://localhost:8888/testMaven/getPurPass',
        ).then(res=>{
          if(res.status == 200){
            console.log(res.data);
            if(res.data.info=='Success'){
              this.purList=res.data.purList;
            }else{
              console.log(res.data.info);
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      getAllSupCan(){
        let te=this.ord.ordpurid;
        axios.get('http://localhost:8888/testMaven/getPurSup',
          {
            params:{
              purId:te
            }
          }
        ).then(res=>{
          if(res.status == 200){
            console.log(res.data);
            if(res.data.info=='Success'){
              this.supList=res.data.supList;
            }else{
              console.log(res.data.info);
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      getAllConCan(){
        let te=this.ord.ordsupplier;
        axios.get('http://localhost:8888/testMaven/getSupCon',
          {
            params:{
              supId:te
            }
          }
        ).then(res=>{
          if(res.status == 200){
            console.log(res.data);
            if(res.data.info=='Success'){
              this.conList=res.data.conList;
            }else{
              console.log(res.data.info);
            }
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      addOrd(){
        this.ord.ordmember=this.memId;
        let te=JSON.stringify(this.ord);
        axios.post('http://localhost:8888/testMaven/addOrder',te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res=>{
          if(res.status == 200){
            this.getAllOrd();
            this.resetForm('ord');
            this.dialogAddFormVisible=false;
          }
        }).catch(err=>{
          console.log(err);
        });
      },
      submitForm(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.addOrd();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName){
        this.$refs[formName].resetFields();
      },
      //跳转品类页面
      goto(row, path) {
        if(row.ordStatus==0){
          this.$router.push({name: path, params: {ordId: row.ordId, updFlag: true}});
        }else{
          this.$router.push({name: path, params: {ordId: row.ordId, updFlag: false}});
        }
      },
    }
  }
</script>
