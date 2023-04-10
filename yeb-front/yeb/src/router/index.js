import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden: true
  }
]

const router = new VueRouter({
  routes
})

// 全局路由守卫
// router.beforeEach((to,from,next)=>{
//   if(window.sessionStorage.getItem("tokenStr")) {
//     initMenu(router, store);
//     next();
//   }else{
//     // 去登录页，放行
//     next();
//   }
// })

export default router
