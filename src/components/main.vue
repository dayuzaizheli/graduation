<template>
  <div id="home" >
    <!--水平导航栏-->
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">处理中心</el-menu-item>
      <el-menu-item index="4" ><a href="https://www.ele.me" target="_blank">个人中心</a></el-menu-item>
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
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>请购管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1" @click="goto('/request')">我的请购</el-menu-item>
              <el-menu-item index="1-2" @click="goto('/reqApproval')">请购审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>采购方案</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1" @click="goto('/purchase')">我的方案</el-menu-item>
              <el-menu-item index="2-2" @click="goto('/purchase')">方案审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="3">
            <i class="el-icon-document"></i>
            <span slot="title" @click="goto('/result')">采购结果</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-document"></i>
            <span slot="title" @click="goto('/order')">订单管理</span>
          </el-menu-item>
          <el-menu-item index="5">
            <i class="el-icon-document"></i>
            <span slot="title" @click="goto('/contract')">合同管理</span>
          </el-menu-item>
          <el-submenu index="6">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>品类管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="6-1" @click="goto('/category')">我的品类</el-menu-item>
              <el-menu-item index="6-2" @click="goto('/catApproval')">品类审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="7">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>供应商管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="7-1" @click="goto('/supplier')">我的供应商</el-menu-item>
              <el-menu-item index="7-2" @click="goto('/supApproval')">供应商审批</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="8">
            <i class="el-icon-document"></i>
            <span slot="title" @click="goto('/staAna')">统计分析</span>
          </el-menu-item>
          <el-submenu index="9">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="9-1" @click="goto('/memberM')">成员管理</el-menu-item>
              <el-menu-item index="9-2" @click="goto('/positionM')">岗位管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-col>
      <el-col :span="2">
        <router-view></router-view>
      </el-col>
    </el-row>

  </div>
</template>

<script>
  export default {
    name: 'main',
    data() {
      return {
        activeIndex: '1',
        activeIndex2: '1'
      };
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
        this.$router.push(path);
      }
    }
  }

</script>
<style>

</style>
