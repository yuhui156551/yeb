<template>
    <el-container>
        <el-header class="homeHeader">
          <div class="title">云E办</div>
          <el-dropdown class="userInfo">
            <span class="el-dropdown-link">
              {{ user.name }} <i><img :src="user.userFace"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>设置</el-dropdown-item>
              <el-dropdown-item>退出登录</el-dropdown-item>
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
              <router-view/>
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
</style>