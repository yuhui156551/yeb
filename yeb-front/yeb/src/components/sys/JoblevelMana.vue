<template>
  <div>
    <div>
      <el-input
          class="addPosInput"
          placeholder="添加职称等级..."
          prefix-icon="el-icon-plus"
          v-model="jl.name">
      </el-input>
      <el-select v-model="jl.titleLevel" placeholder="职称等级" style="margin-right: 10px">
        <el-option
            v-for="item in titleLevels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button icon="el-icon-plus" type="primary" @click="addJobLevel">添加</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table
          :data="jls"
          style="width: 927px"
          stripe
          border
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="id"
            label="编号"
            width="120">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职称名称"
            width="150">
        </el-table-column>
        <el-table-column
            prop="titleLevel"
            label="职称等级"
            width="150">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建日期"
            width="150">
        </el-table-column>
        <el-table-column
            prop="enabled"
            label="是否启用"
            width="150">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
            <el-tag type="danger" v-else >未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="操作"
            width="150">
          <template slot-scope="scope">
            <el-button size="small" @click="showEditDialog(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteHandler(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="danger" style="margin-top: 10px" :disabled="this.multipleSelection==0" @click="deleteMany">批量删除</el-button>
    </div>
    <el-dialog
        title="编辑职称"
        :visible.sync="dialogVisible"
        width="30%">
      <table>
        <tr>
          <td>
            <el-tag>职称名称</el-tag>
          </td>
          <td>
            <el-input v-model="updateJl.name" style="margin-left: 6px"></el-input>
          </td>
        </tr>
        <tr>  
          <td>
            <el-tag style="margin-top: 3px">职称等级</el-tag>
          </td>
          <td>
            <el-select v-model="updateJl.titleLevel" placeholder="职称等级" style="margin: 3px 0px 0px 6px">
              <el-option
                  v-for="item in titleLevels"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>  
          </td>
        </tr>
        <tr>
          <td>
            <el-tag style="margin-top: 10px">是否启用</el-tag>
          </td>
          <td>
            <el-switch
                style="margin-left: 10px;margin-top: 10px"
                v-model="updateJl.enabled"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="已启用"
                inactive-text="未启用">
            </el-switch>
          </td>
        </tr>
      </table>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doUpdate">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "JoblevelMana",
    data(){
      return{
        // 所填的数据，发送给后端
        jl:{
          name:'',
          titleLevel:'' 
        },
        titleLevels: [
          '正高级', '副高级', '高级', '中级', '初级'
        ],
        // 要更新的数据
        updateJl:{
          name:'',
          titleLevel:'',
          enabled: ''
        },
        // 后端传过来的数据存放到这
        jls:[],
        dialogVisible:false,
        multipleSelection:[]
      }
    },
    mounted() {
      this.initJls();
    },
    methods:{
      deleteMany() {
        this.$confirm('此操作将永久删除 【' + this.multipleSelection.length + '】 条职称, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = '?';
          this.multipleSelection.forEach(item =>{
            ids += 'ids=' + item.id + '&';
          })
          this.deleteRequest('/system/basic/joblevel/' + ids).then(resp => {
            if (resp) {
              this.initJls();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      doUpdate() {
        this.putRequest('/system/basic/joblevel/',this.updateJl).then(resp=>{
          if (resp) {
            this.initJls();
            this.dialogVisible = false;
          }
        })
      },
      showEditDialog(row){
        // 修改时，不需要传递date，后端使用的是LocalDatetime，这里的数据是LocalDate类型，如果传递，会引发转换错误。
        row.createDate = '';
        Object.assign(this.updateJl, row);
        this.dialogVisible = true;
      },
      // 删除
      deleteHandler(row){
        this.$confirm('此操作将永久删除 【' + row.name + '】 职称, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/system/basic/joblevel/' + row.id).then(resp => {
            if (resp) {
              this.initJls();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      // 添加
      addJobLevel(){
        if(this.jl.name && this.jl.titleLevel){
          this.postRequest('system/basic/joblevel/',this.jl).then(resp=>{
            if(resp){
              this.initJls();
            }
          })
        }
      },
      // 初始化
      initJls(){
        this.getRequest('system/basic/joblevel/').then(resp=>{
          if(resp){
            this.jls = resp;
            this.jl.name = '';
            this.jl.titleLevel = '';
          }
        })
      }
    }
  }
</script>

<style scoped>
  .addPosInput{
    width: 300px;
    margin-right: 10px;
  }
</style>