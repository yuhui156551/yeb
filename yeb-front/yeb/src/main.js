import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.css'
import {getRequest} from "@/utils/api";
import {postRequest} from "@/utils/api";
import {putRequest} from "@/utils/api";
import {deleteRequest} from "@/utils/api";

import {initMenu} from "@/utils/menus";


Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.prototype.getRequest = getRequest
Vue.prototype.postRequest = postRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.deleteRequest = deleteRequest

// 全局路由守卫
router.beforeEach((to, from, next) => {
    if (window.sessionStorage.getItem("tokenStr")) {
        initMenu(router, store);
        // 如果遇到啥问题，实在找不到原因，那就删除浏览器数据吧，亲测多次有效
        if (!window.sessionStorage.getItem('user')) {
            // 如果没有用户信息，则发送请求获取
            return getRequest('/admin/info').then(resp => {
                if (resp) {
                    window.sessionStorage.setItem('user', JSON.stringify(resp));
                    next();
                }
            })
        }
        next();
    } else {
        // 没有token，放行到登录页
        if(to.path == '/'){
            next();
        }else {
            // 如果没有登录，访问的也不是登录页，那么登录成功之后直接跳转到访问的页面
            next('/?redirect' + to.path);
        }
    }
})

new Vue({
    router,
    store,
    render: h => h(App)// 渲染App组件
}).$mount('#app')
