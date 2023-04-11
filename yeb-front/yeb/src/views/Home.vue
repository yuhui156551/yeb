<template>
    <el-container>
        <el-header class="homeHeader">
          <div class="title">云E办</div>
          <el-dropdown class="userInfo" @command="commandHandler">
            <span class="el-dropdown-link">
              {{ user.name }} <i><img :src="user.userFace"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item cammand="userInfo">个人中心</el-dropdown-item>
              <el-dropdown-item command="setting">设置</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-container>
            <el-aside width="200px">
                <el-menu router unique-opened>
                    <el-submenu :index="index+''" 
                                v-for="(item,index) in routes" :key="index"
                                v-if="!item.hidden">
                        <template slot="title"><i :class="item.iconCls" style="color: #409eff;margin: 2px 5px 0px 0px"></i>
                            <span>{{ item.name }}</span>
                        </template>
                        <el-menu-item :index="children.path"
                                      v-for="(children,indexj) in item.children" :key="indexj">
                          <span>{{ children.name }}</span>
                        </el-menu-item>
                    </el-submenu> 
                </el-menu>
            </el-aside>
            <el-main>
              <el-breadcrumb separator-class="el-icon-arrow-right" 
                             v-if="this.$router.currentRoute.path!='/home'">
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
              </el-breadcrumb>
              <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
                欢迎来到云E办系统!
              </div>
              <router-view class="homeRouterView"/>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
  export default {
      name: "Home",
      data(){
        return{
          user: JSON.parse(window.sessionStorage.getItem('user'))
        }
      },
      computed:{
        routes(){
          // 从 state 获取路由
          return this.$store.state.routes;
        }
      },
      methods:{
        commandHandler(command){
          if(command == 'logout'){
            this.$confirm('此操作将注销登录, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              // 退出登录
              this.postRequest('/logout');
              // 清除用户信息
              window.sessionStorage.removeItem('tokenStr');
              window.sessionStorage.removeItem('user');
              // 清空vuex
              this.$store.commit('initRoutes', []);
              // 跳转到登录页
              this.$router.replace('/');
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除'
              });
            });
          }
        }
    }
  }
</script>

<style>
  .homeHeader{
    background: #409eff;
    display: flex;
    align-content: center;
    justify-content: space-between;
    padding: 0px 15px;
    box-sizing: border-box;
  }
  .homeHeader .title{
    font-size: 30px;
    font-family: 华文行楷;
    margin-top: 15px;
    color: white;
  }
  .homeHeader .userInfo{
    /*鼠标移动到头像附近才显示*/
    cursor: pointer;
  }
  .el-dropdown-link img{
    width: 48px;
    height: 48px;
    border-radius: 24px;
    margin-left: 8px;
  }
  .homeWelcome{
    text-align: center;
    font-size: 30px;
    font-family: 华文行楷;
    color: cornflowerblue;
    margin-top: 10px;
  }
  .homeRouterView{
    margin-top: 5px;
  }
</style>