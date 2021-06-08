<template>
  <el-container>
    <el-header style="height: 40px; padding-top: 20px">
      <el-button style="" @click="colReq" >新建采购方案</el-button>
    </el-header>

    <el-dialog title="新建采购方案" :visible.sync="dialogAddFormVisible">
      <el-form :model="purchase">
        <el-form-item label="请输入方案名，格式建议：年份+月份+采购方案" >
          <el-input v-model="purchase.purname" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addPur">确 定</el-button>
      </div>
    </el-dialog>

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
            <el-button @click="goto(scope.row, 'purCategory')" size="small" type="primary" plain>查看/编辑</el-button>
            <el-button size="small" type="warning" plain @click="delPurById(scope.row)">删除</el-button>
            <el-button size="small" type="success" plain @click="updStatus(scope.row)">确认采购方案</el-button>
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
    name: 'purchase',
    data(){
      return{
        purList:[],
        dialogAddFormVisible:false,
        purchase:{
          purid:1,
          purname:'',
          purmember:1,
          purstatus:1,
          total:1,
          cretime:1
        }
      };
    },
    created() {
      let memId=sessionStorage.getItem("memId");
      axios.get('http://localhost:8888/testMaven/getAllPur',
        {
          params:{
            purMem:memId
          }
        }
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
        let memId=sessionStorage.getItem("memId");
        axios.get('http://localhost:8888/testMaven/getAllPur',
          {
            params: {
              purMem: memId
            }
          }
        ).then(res => {
          if (res.status == 200) {
            console.log(res.data);
            if (res.data.info == 'Success') {
              this.purList = res.data.purList;
            } else {
              console.log(res.data.info);
            }
          }
        }).catch(err => {
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
      //汇总请购单
      colReq() {
        axios.get('http://localhost:8888/testMaven/colReq'
        ).then(res => {
          if (res.status == 200) {
            console.log(res.data)
            //没有需要汇总的请购单则不需要建立采购方案
            if (res.data == 'Success') {
              //汇总了请购单再弹窗
              this.dialogAddFormVisible = true;
            } else {
              this.$alert('当前没有请购需求，不可建立采购方案', '提示', {
                confirmButtonText: '确定'
              });
            }
          }
        }).catch(err => {
          console.log(err);
        });
      },
      //添加请购单
      addPur() {
        //调用接口添加新数据
        let memId=sessionStorage.getItem("memId");
        this.purchase.purmember = memId;
        var pur = JSON.stringify(this.purchase);
        axios.post('http://localhost:8888/testMaven/addPur', pur,
          {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            }
          }).then(res => {
          if (res.status == 200) {
            if(res.data = '已经存在未确认的采购方案') {
              this.$alert('已经存在未确认的采购方案', '提示', {
                confirmButtonText: '确定'
              });
              this.dialogAddFormVisible = false;
            }else if (res.data = '目前没有未被纳入采购方案的请购需求,不能建立采购方案') {
              this.$alert('目前没有未被纳入采购方案的请购需求,不能建立采购方案', '提示', {
                confirmButtonText: '确定'
              });
            }else {
              this.getAllPur();
              this.dialogAddFormVisible = false;
            }
          }
        }).catch(err => {
          console.log(err);
        });
      },
      //删除请购单
      delPurById(row, index) {
        //某些状态不可删除并给出提示
        if(row.purstatus == 3) {
          //调用删除接口删除数据
          axios.get('http://localhost:8888/testMaven/delPurLogic',
            {
              params: {
                purId: row.purid
              }
            }).then(res => {
            if (res.status == 200) {
              this.$alert('删除成功', '提示', {
                confirmButtonText: '确定'
              });
              this.getAllPur();
            }
          }).catch(err => {
            console.log(err);
          })
        } else {
          this.$alert('状态已确定的方案才能删除', '提示', {
            confirmButtonText: '确定'
          });
        }
      },
      //提交审批
      updStatus(row, index) {
        let te = new FormData();
        let memPos=sessionStorage.getItem("memPos");
        te.append("memPos", memPos);
        te.append("purId", row.purid);
        te.append("status", 1);
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
        if (row.purstatus == 0 || row.purstatus == 2) {
          this.$router.push({name: path, params: {purId: row.purid, updFlag: true, ArlFlag: false}});
          //this.$router.push({name: path, query: {reqId: row.reqId}});
        } else {
          this.$router.push({name: path, params: {purId: row.purid, updFlag: false, ArlFlag: false}});
        }
      }
    }
  }
</script>
