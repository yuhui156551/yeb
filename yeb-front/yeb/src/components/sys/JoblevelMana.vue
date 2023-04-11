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
      <el-button icon="el-icon-plus" type="primary" @click="addPosition">添加</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table
          :data="jls"
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
            label="是否可用"
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
    </div>
  </div>
</template>

<script>
  export default {
    name: "JoblevelMana",
    data(){
      return{
        jl:{
          name:'',
          titleLevel:'' 
        },
        titleLevels: [
          '正高级', '副高级', '高级', '中级', '初级'
        ],
        jls:[]
      }
    },
    mounted() {
      this.initJls();
    },
    methods:{
      initJls(){
        this.getRequest('system/basic/joblevel/').then(resp=>{
          if(resp){
            this.jls = resp;
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