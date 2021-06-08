<template>
  <el-container>
    <el-header style="height: 30px; padding-top: 20px">

    </el-header>

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
            <el-button size="small" type="warning" plain @click="updSupStatus(scope.row, 0)">驳回</el-button>
            <el-button size="small" type="success" plain @click="updSupStatus(scope.row, 1)">审核通过</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'supApproval',
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
      axios.get('http://localhost:8888/testMaven/getAllSup',
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
        axios.get('http://localhost:8888/testMaven/getAllSup',
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
      //修改状态
      updSupStatus(row, sta){
          let te=new FormData();
          te.append("memPos","cgjl");
          te.append("supStatus",sta);
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
      },
      //页面跳转
      goto(row,path){
        //this.$router.push({ name: path, query: { supid: row.supid} });
        this.$router.push({name: path, params: {supid: row.supid}});
      },
    }
  }
</script>
