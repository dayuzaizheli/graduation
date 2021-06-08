import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import main from '@/components/main'
import test from '@/components/test'
import request from '@/components/request'
import reqApproval from '@/components/reqApproval'
import purchase from '@/components/purchase'
import result from '@/components/result'
import order from '@/components/order'
import contract from '@/components/contract'
import category from '@/components/category'
import catApproval from '@/components/catApproval'
import supplier from '@/components/supplier'
import supApproval from '@/components/supApproval'
import staAna from '@/components/staAna'
import memberM from '@/components/memberM'
import positionM from '@/components/positionM'
import MyHome from '@/components/MyHome'
import catSupplier from '@/components/catSupplier'
import reqCategory from '@/components/reqCategory'
import purCategory from '@/components/purCategory'
import purApproval from '@/components/purApproval'
import ordCategory from '@/components/ordCategory'
import resCategory from '@/components/resCategory'
import login from '@/components/login'

Vue.use(Router);



const routes=[
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/MyHome',
      name: 'MyHome',
      component: MyHome,
      children:[
        {
          path: '/request',
          name: 'request',
          component: request
        },
        {
          path: '/reqApproval',
          name: 'reqApproval',
          component: reqApproval
        },
        {
          path: '/purchase',
          name: 'purchase',
          component: purchase
        },
        {
          path: '/result',
          name: 'result',
          component: result
        },
        {
          path: '/order',
          name: 'order',
          component: order
        },
        {
          path: '/contract',
          name: 'contract',
          component: contract
        },
        {
          path: '/category',
          name: 'category',
          component: category
        },
        {
          path: '/catApproval',
          name: 'catApproval',
          component: catApproval
        },
        {
          path: '/supplier',
          name: 'supplier',
          component: supplier
        },
        {
          path: '/supApproval',
          name: 'supApproval',
          component: supApproval
        },
        {
          path: '/MyHome/staAna',
          name: 'staAna',
          component: staAna
        },
        {
          path: '/memberM',
          name: 'memberM',
          component: memberM
        },
        {
          path: '/positionM',
          name: 'positionM',
          component: positionM
        },
        {
          path: '/catSupplier',
          name: 'catSupplier',
          component: catSupplier
        },
        {
          path: '/reqCategory',
          name: 'reqCategory',
          component: reqCategory
        },
        {
          path: '/purCategory',
          name: 'purCategory',
          component: purCategory
        },
        {
          path: '/purApproval',
          name: 'purApproval',
          component: purApproval
        },
        {
          path: '/ordCategory',
          name: 'ordCategory',
          component: ordCategory
        },
        {
          path: '/resCategory',
          name: 'resCategory',
          component: resCategory
        }
      ]
    }
  ];
const router = new Router({
  routes,
  mode: 'history',
  linkActiveClass: 'active'
})
router.beforeEach((to,from,next)=>{
  if(to.path=='/login'){
    next()
  }else{
    let id=sessionStorage.getItem("memId");
    if(!id){
      next('/login');
    }else {
      next();
    }
  }
})
export default router;

