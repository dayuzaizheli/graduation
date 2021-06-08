<template>
  <div id="home" >
    <!--水平导航栏-->
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1" style=" width: 280px; padding-left: 100px;">处理中心</el-menu-item>
      <el-menu-item index="2" style=" padding-left: 1300px;">
        <el-link icon="el-icon-circle-close" @click="logout">Logout</el-link>
      </el-menu-item>

    </el-menu>
    <div class="line"></div>
    <!--竖直导航栏-->
    <el-row class="tac">
      <el-col :span="4">
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose">
          <el-submenu index="1" v-if="posResp.qggl">
            <template slot="title">
              <i class="el-icon-s-help"></i>
              <span>请购管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1" v-if="posResp.wdqg" @click="goto('/request')">我的请购</el-menu-item>
              <el-menu-item index="1-2" v-if="posResp.qgsp" @click="goto('/reqApproval')">请购审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2" v-if="posResp.cgfa">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>采购方案</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1" v-if="posResp.wdfa" @click="goto('/purchase')">我的方案</el-menu-item>
              <el-menu-item index="2-2" v-if="posResp.fasp" @click="goto('/purApproval')">方案审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="3" v-if="posResp.cgjg" >
            <i class="el-icon-message-solid"></i>
            <span slot="title" @click="goto('/result')">采购结果</span>
          </el-menu-item>
          <el-menu-item index="4" v-if="posResp.ddgl">
            <i class="el-icon-s-order"></i>
            <span slot="title" @click="goto('/order')">订单管理</span>
          </el-menu-item>
          <el-menu-item index="5" v-if="posResp.htgl">
            <i class="el-icon-s-management"></i>
            <span slot="title" @click="goto('/contract')">合同管理</span>
          </el-menu-item>
          <el-submenu index="6" v-if="posResp.plgl">
            <template slot="title">
              <i class="el-icon-s-operation"></i>
              <span>品类管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="6-1" v-if="posResp.wdpl" @click="goto('/category')">我的品类</el-menu-item>
              <el-menu-item index="6-2" v-if="posResp.plsp" @click="goto('/catApproval')">品类审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="7" v-if="posResp.gysgl">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span>供应商管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="7-1" v-if="posResp.wdgys" @click="goto('/supplier')">我的供应商</el-menu-item>
              <el-menu-item index="7-2" v-if="posResp.gyssp" @click="goto('/supApproval')">供应商审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="8" v-if="posResp.tjfx">
            <i class="el-icon-s-data"></i>
            <span slot="title" @click="goto('/MyHome/staAna')">统计分析</span>
          </el-menu-item>
          <el-submenu index="9" v-if="posResp.xtgl">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="9-1" v-if="posResp.cygl" @click="goto('/memberM')">成员管理</el-menu-item>
              <el-menu-item index="9-2" v-if="posResp.gwgl" @click="goto('/positionM')">岗位管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-col>
      <el-col :span="20">
        <router-view></router-view>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: 'MyHome',
    data() {
      return {
        activeIndex: '1',
        activeIndex2: '1',
        isRouterAlive:true,
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
    created() {
      //let resRouter=this.$route.params.posResp;
      let tes=sessionStorage.posResp;
      let resRouter=JSON.parse(tes);
      console.log(resRouter);
      //请购管理
      this.posResp.qggl=resRouter.qggl;
      console.log(resRouter.qggl);
      this.posResp.wdqg=resRouter.wdqg;
      this.posResp.qgsp=resRouter.qgsp;
      //采购方案
      this.posResp.cgfa=resRouter.cgfa;
      this.posResp.wdfa=resRouter.wdfa;
      this.posResp.fasp=resRouter.fasp;
      //采购结果
      this.posResp.cgjg=resRouter.cgjg;
      //订单管理
      this.posResp.ddgl=resRouter.ddgl;
      //合同管理
      this.posResp.htgl=resRouter.htgl;
      //品类管理
      this.posResp.plgl=resRouter.plgl;
      this.posResp.wdpl=resRouter.wdpl;
      this.posResp.plsp=resRouter.plsp;
      //供应商管理
      this.posResp.gysgl=resRouter.gysgl;
      this.posResp.wdgys=resRouter.wdgys;
      this.posResp.gyssp=resRouter.gyssp;
      //统计分析
      this.posResp.tjfx=resRouter.tjfx;
      //系统管理
      this.posResp.xtgl=resRouter.xtgl;
      this.posResp.cygl=resRouter.cygl;
      this.posResp.gwgl=resRouter.gwgl;
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      //页面跳转
      goto(path){
        this.$router.push(path).catch(err=>{});
      },
      reload () {
        this.isRouterAlive = false
        this.$nextTick(function () {
          this.isRouterAlive = true
        })
      },
      logout(){
        sessionStorage.clear();
        this.$router.push({name: 'login'});
      }
    }
  }

</script>
<style>

</style>
