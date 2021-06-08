<template>
  <el-container>
    <el-header style="height: 25px">
    </el-header>

    <el-main>
      <el-table :data="purList">
        <el-table-column prop="purname" label="方案名称" width="250">
        </el-table-column>
        <el-table-column prop="cretime" label="创建时间" width="250" :formatter="dateFormat">
        </el-table-column>
        <el-table-column prop="purstatus" label="方案状态" :formatter="purStatusFormat">
        </el-table-column>
        <el-table-column prop="total" label="方案总额(元)">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="500">
          <template slot-scope="scope">
            <el-button @click="goto(scope.row, 'purCategory')" size="small" type="primary" plain>查看</el-button>
            <el-button size="small" type="warning" plain @click="updStatus(scope.row,0)">驳回</el-button>
            <el-button size="small" type="success" plain @click="updStatus(scope.row,1)">通过</el-button>
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
    name: 'purApproval',
    data(){
      return{
        purList:[],
        dialogAddFormVisible:false,
      };
    },
    created() {
      axios.get('http://localhost:8888/testMaven/getAllPurNoPass',
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
    methods: {
      //查询数据的封装
      getAllPur() {
        axios.get('http://localhost:8888/testMaven/getAllPurNoPass',
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
      //reqStatus格式化显示
      purStatusFormat(row, index) {
        if (row.purstatus == 0) {
          return '草稿待确定';
        } else if (row.purstatus == 1) {
          return '审批中';
        } else if (row.purstatus == 2) {
          return '驳回';
        } else if (row.purstatus == 3) {
          return '已确定';
        } else {
          return '状态异常';
        }
      },
      //时间格式化显示
      dateFormat(row, index) {
        return moment(row.cretime).format('YYYY-MM-DD HH:mm:ss');
      },
      //审核
      updStatus(row, sta) {
        let te = new FormData();
        te.append("memPos", "ceo");
        te.append("purId", row.purid);
        te.append("status", sta);
        axios.post('http://localhost:8888/testMaven/updPurStatus', te,
          {
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
          }
        ).then(res => {
          if (res.status == 200) {
            this.getAllPur();
          }
        }).catch(err => {
          console.log(err);
        });
      },
      //跳转品类页面
      goto(row, path) {
        this.$router.push({name: path, params: {purId: row.purid, updFlag: false, ArlFlag: true}});
      }
    }
  }
</script>
