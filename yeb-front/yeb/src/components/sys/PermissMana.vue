<template>
  <div>
    <div class="permissMana">
      <el-input placeholder="请输入角色英文名..." v-model="role.name">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input v-model="role.nameZh" placeholder="请输入角色中文名"></el-input>
      <el-button size="mini" type="primary" icon="el-icon-plus" @click="addRole">添加角色</el-button>
    </div>
    <div class="permissManaMain">
      <el-collapse v-model="activeName" accordion  @change="change">
        <el-collapse-item :title="role.nameZh" :name="role.id" v-for="(role,index) in roles" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问资源</span>
              <el-button style="float: right; padding: 3px 0; color:#ff0000" type="text" 
                         icon="el-icon-delete"
                         @click="doDelete(role)"></el-button>
            </div>
            <div>
              <el-tree :data="allMenus" 
                       :props="defaultProps" 
                       show-checkbox
                       ref="tree"
                       :key="index"
                       :default-checked-keys="selectMenus"
                       node-key="id"></el-tree><!--node-key，其值为节点数据中的一个字段名-->
              <div style="display: flex; justify-content: end; margin-top: 5px">
                <el-button size="small" @click="cancel">取消修改</el-button>
                <el-button size="small" type="primary" @click="doUpdate(role.id, index)">确认修改</el-button>
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "PermissMana",
  data() {
    return {
      role: {
        name: '',
        nameZh: ''
      },
      roles: [],
      allMenus:[],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      selectMenus:[],
      activeName:'-1'
    }
  },
  mounted() {
    this.initRoles();
  },
  methods: {
    doDelete(role){
      this.$confirm('此操作将永久删除角色 【' + role.nameZh + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/permiss/role/' + role.id).then(resp => {
          if (resp) {
            this.initRoles();
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    addRole(){
      if(this.role.name && this.role.nameZh){
        this.postRequest('/system/basic/permiss/', this.role).then(resp=>{
          if(resp){
            this.initRoles();
            this.role.name = '';
            this.role.nameZh = '';
          }
        })
      }else{
        this.$message.error('所有字段不能为空!');
      }
    },
    cancel(){
      this.activeName = '-1';
    },
    doUpdate(rid,index){
      let tree = this.$refs.tree[index];
      // 获取被选中的叶子节点
      let selectedKeys = tree.getCheckedKeys(true);
      let url = '/system/basic/permiss/?rid=' + rid;
      selectedKeys.forEach(key=>{
        url += '&mids=' + key;
      });
      this.putRequest(url).then(resp=>{
        if(resp){
          this.activeName = '-1';
        }
      })
    },
    change(rid){// rid来源是r.id
      if(rid){
        this.initAllMenu();
        this.initSelectMenus(rid);
      }
    },
    initSelectMenus(rid) {
      // 根据rid查询菜单id，展示为默认选中效果
      this.getRequest('/system/basic/permiss/mid/' + rid).then(resp => {
        if (resp) {
          this.selectMenus = resp;
        }
      });
    },
    initAllMenu(){
      this.getRequest('/system/basic/permiss/menus').then(resp=>{
        if(resp){
          this.allMenus = resp;
        }
      })
    },
    initRoles() {
      this.getRequest('/system/basic/permiss/').then(resp => {
        if (resp) {
          this.roles = resp;
        }
      })
    }
  }
}
</script>

<style scoped>
.permissMana {
  display: flex;
}

.permissMana .el-input {
  width: 300px;
  margin-right: 6px;
}

.permissManaMain {
  margin-top: 10px;
  width: 700px;
}
</style>