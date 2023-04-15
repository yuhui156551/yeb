<template>
  <div>
    <el-input
        style="width: 500px"
        placeholder="请输入部门名称进行搜索"
        prefix-icon="el-icon-search"
        v-model="filterText">
    </el-input>

    <el-tree
        style="width: 500px; margin-top: 5px"
        :expand-on-click-node="false"
        :data="deps"
        :props="defaultProps"
        :filter-node-method="filterNode"
        ref="tree">
      <span slot-scope="{ node, data }" 
            style="display: flex; justify-content: space-between; width: 100%">
        <span>{{ data.name }}</span>
        <span>
          <el-button
              type="primary"
              size="mini"
              class="depBtn"
              @click="() => addDep(data)">
            添加部门
          </el-button>
          <el-button
              type="danger"
              size="mini"
              class="depBtn"
              @click="() => deleteDep(data)">
            删除部门
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog
        title="添加部门"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <table>
          <tr>
            <td>
              <el-tag style="margin-right: 10px">上级部门</el-tag>
            </td>
            <td>
              {{ pname }}
            </td>
          </tr>
          <tr>
            <td>
              <el-tag>部门名称</el-tag>
            </td>
            <td>
              <el-input v-model="dep.name" placeholder="请输入部门名称..."></el-input>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddDep">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "DepMana",
    data(){
      return{
        pname: '',
        dialogVisible: false,
        dep: {
          name: '',
          parentId: -1
        },
        filterText:'',
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        deps:[]
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    mounted() {
      this.initDeps();
    },
    methods: {
      initDep(){
        this.dep = {
          name:'',
          parentId:-1
        };
        this.pname = '';
      },
      doAddDep(){
        this.postRequest('/system/basic/department/', this.dep).then(resp=>{
          if(resp){
            this.initDeps();
            this.initDep();
            this.dialogVisible = false;
          }
        })
      },
      addDep(data) {
        this.pname = data.name// 当前部门作为上级部门
        this.dep.parentId = data.id;// 当前部门id作为父id
        this.dialogVisible = true;
      },
      deleteDep(data){
        if (data.isParent) {
          this.$message.error('父部门删除失败！');
        } else {
          this.$confirm('此操作将永久删除【' + data.name + '】部门, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteRequest(' /system/basic/department/' + data.id).then(resp => {
              // if (resp) {
              //   this.removeDepFromDeps(null, this.deps, data.id);
              // }
              if(resp){
                this.initDeps();
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        }
      },
      initDeps(){
        this.getRequest('/system/basic/department/').then(resp=>{
          if(resp){
            this.deps = resp;
          }
        })
      },
      filterNode(value, data) {
        // 如果输入框删除值，展开所有
        if (!value) return true;
        return data.name.indexOf(value) !== -1;
      }
    },
  }
</script>

<style>
  .depBtn{
    padding: 2px;
  }
</style>