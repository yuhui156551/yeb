<template>
  <div>
    <div>
      <el-input
          class="addPosInput"
          placeholder="添加职位..."
          prefix-icon="el-icon-plus"
          v-model="pos.name">
      </el-input>
      <el-button icon="el-icon-plus" type="primary" @click="addPosition">添加</el-button>
    </div>
    <div>
      <el-table
          stripe
          border
          @selection-change="handleSelectionChange"
          :data="positions"
          style="width: 70%"
          class="posManaMain">
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
            label="职位"
            width="150">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间"
            width="150">
        </el-table-column>
<!--        <el-table-column-->
<!--            prop="enabled"-->
<!--            label="是否启用"-->
<!--            width="120">-->
<!--        </el-table-column>-->
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button
                size="small"
                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
                size="small"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-button type="danger" style="margin-top: 10px" 
               :disabled="multipleSelection.length==0"
               @click="deleteMany">批量删除</el-button>
    <el-dialog
        title="编辑职位"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <el-tag>职位名称</el-tag>
        <el-input v-model="updatePos.name" class="updatePosInput"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doUpdate">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "PosMana",
    data(){
      return{
        pos:{
          name:''
        },
        positions:[],
        dialogVisible: false,
        updatePos:{
          name:'',
        },
        multipleSelection: []
      }
    },
    mounted() {
      this.initPositions();
    },
    methods:{
      // 批量删除
      deleteMany(){
        this.$confirm('此操作将永久删除 【' + this.multipleSelection.length + '】 条职位, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = '?';
          this.multipleSelection.forEach(item =>{
            ids += 'ids=' + item.id + '&';
          })
          this.deleteRequest('/system/cfg/pos/' + ids).then(resp => {
            if (resp) {
              this.initPositions();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      // 多选
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      // 修改 
      doUpdate(){
        this.putRequest('/system/cfg/pos/', this.updatePos).then(resp=>{
          if(resp){
            this.initPositions();
            this.dialogVisible = false;
          }
        })
      },
      // 添加
      addPosition(){
        if(this.pos.name){
          this.postRequest('/system/cfg/pos/', this.pos).then(resp=>{
            if(resp){
              // 添加完成，刷新数据
              this.initPositions();
              this.pos.name = '';
            }
          })
        }else {
          this.$message.error("职位名称不能为空！");
        }
      },
      // 编辑
      handleEdit(index,data){
        // this.updatePos = data;
        Object.assign(this.updatePos, data);
        this.dialogVisible = true;
      },
      // 删除
      handleDelete(index,data){
        this.$confirm('此操作将永久删除 【' + data.name + '】 职位, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/system/cfg/pos/' + data.id).then(resp => {
            if (resp) {
              this.initPositions();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      // 初始化数据
      initPositions(){
        this.getRequest('/system/cfg/pos/').then(resp=>{// 路径最后还需要一个'/'
          if(resp){
            this.positions = resp;
          }
        })
      }
    }
  }
</script>

<style>
  .addPosInput{
    width: 300px;
    margin-right: 10px;
  }
  .posManaMain{
    margin-top: 10px;
  }
  .updatePosInput{
    width: 200px;
    margin-left: 10px;
  }
</style>