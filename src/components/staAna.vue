<template>
  <div id="app">
    <el-header style="height: 30px">
      <span class="demonstration">请选择时间范围</span>
      <el-date-picker
        v-model="value2"
        type="datetimerange"
        :picker-options="pickerOptions"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        align="left">
      </el-date-picker>
      <el-button @click="testTime">统计</el-button>
    </el-header>

    <!--统计图-->
    <el-container id="staAna">
    </el-container>
    合计: {{moneyTotal.toFixed(2)}}(元)
  </div>
</template>

<script>
  import * as echarts from 'echarts';
  import axios from "axios";
  export default {
    name: 'staAna',
    data() {
      return {
        data1:["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"],
        data2: [5, 20, 36, 10, 10, 20],
        moneyTotal:0,
        myData:[],
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        value2: '',
        myChart:''
      };
    },
    mounted() {
      // 基于准备好的dom，初始化echarts实例
      this.myChart=echarts.init(document.getElementById('staAna'));
      this.drawLine(this.myData);
    },
    methods: {
      drawLine(myData){
        //let myChart = echarts.init(document.getElementById('staAna'));
        let myChart = this.myChart;
        // 绘制图表
        setTimeout(()=>{
          myChart.setOption({
            title: { text: '时间段内购买品类及金额' },
            tooltip: {},
            legend: {
              data:['金额']
            },
            dataset: {
              dimensions: ['cat', 'total', 'num'],
              source:myData
            },
            xAxis: {
              type: 'category',
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              name: '金额',
              type: 'bar'
            }]
          });
        }, 500)

      },
      testTime(){
        // console.log(this.value2);
        // console.log(this.value2[0]);
        // console.log(this.value2[1]);
        let myChart = echarts.init(document.getElementById('staAna'));
        if(this.value2==''){
          //console.log("meiyoudyi");
        }else{
          let te=new FormData();
          console.log("-----------------自选time---------");
          console.log(this.value2[0]);
          te.append("sta",this.value2[0]);
          te.append("end",this.value2[1]);
          axios.post('http://localhost:8888/testMaven/staAnaByTime',te,
            {
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              }
            }
          ).then(res=>{
            if(res.status == 200){
              if(res.data.info='Success'){
                this.myData=res.data.myData;
                this.drawLine(this.myData);
                this.getMoneyTotal(this.myData);
              }else{
                this.myData=[];
                this.drawLine(this.myData);
                this.getMoneyTotal(this.myData);
              }
            }

          }).catch(err=>{
            console.log(err);
          });
        }
      },
      getMoneyTotal(myData){
        this.moneyTotal=0;
        for(let i in myData){
          this.moneyTotal+=myData[i].total;
        }
        //this.moneyTotal.toFixed(2);
      }
    }
  }

</script>
<style>
  #staAna{margin:0 auto;width:1000px;height:600px;padding-top: 50px;}
</style>
