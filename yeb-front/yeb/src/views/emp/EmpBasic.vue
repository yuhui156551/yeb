<template>
  <div>
    <div>
      <div style="display: flex; justify-content: space-between;">
        <div style="margin-top: 5px">
          <el-input style="width: 300px; margin-right: 8px"
                    v-model="empName"
                    placeholder="请输入员工名进行搜索..."
                    clearable
                    :disabled="showAdvanceSearchVisible"
                    @clear="initEmps"
                    prefix-icon="el-icon-search"></el-input>
          <el-button type="primary" icon="el-icon-search" @click="initEmps" :disabled="showAdvanceSearchVisible">搜索</el-button>
          <el-button type="primary" @click="showAdvanceSearchVisible = !showAdvanceSearchVisible">
            <i :class="showAdvanceSearchVisible?'fa fa-angle-double-up':'fa fa-angle-double-down'" aria-hidden="true"></i>
            高级搜索</el-button><!--三目运算-->
        </div>
        <div>
          <el-upload
              style="display: inline-flex;margin-right: 8px"
              action="/employee/basic/import"
              :show-file-list="false"
              :headers="headers"
              :before-upload="beforeUpload"
              :disabled="importDataDisabled"
              :on-success="onSuccess"
              :on-error="onError">
            <el-button :icon="importDataBtnicon" type="success">
              {{ importDataBtnText }}
            </el-button>
          </el-upload>

          <el-button type="success" icon="el-icon-upload" @click="exportData">
            <i  aria-hidden="true"></i>导出数据</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">添加员工</el-button>
        </div>
      </div>
    </div>
    <transition name="slide-fade"><!--过渡动画-->
    <div v-show="showAdvanceSearchVisible" style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px">
      <el-row>
        <el-col :span="5">
          政治面貌:
          <el-select v-model="searchValue.politicId" placeholder="政治面貌" size="mini"
                     style="width: 130px;">
            <el-option
                v-for="item in politicsStatus"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          民族:
          <el-select v-model="searchValue.nationId" placeholder="民族" size="mini"
                     style="width: 130px;">
            <el-option
                v-for="item in nations"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          职位:
          <el-select v-model="searchValue.posId" placeholder="职位" size="mini" style="width: 130px;">
            <el-option
                v-for="item in positions"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          职称:
          <el-select v-model="searchValue.jobLevelId" placeholder="职称" size="mini"
                     style="width: 130px;">
            <el-option
                v-for="item in joblevels"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="7">
          聘用形式:
          <el-radio-group v-model="searchValue.engageForm">
            <el-radio label="劳动合同">劳动合同</el-radio>
            <el-radio label="劳务合同">劳务合同</el-radio>
          </el-radio-group>
        </el-col>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-col :span="5">
          所属部门:
          <el-popover
              placement="right"
              title="请选择部门"
              width="200"
              trigger="manual"
              v-model="popVisible2">
            <el-tree default-expand-all :data="allDeps" :props="defaultProps"
                     @node-click="searvhViewHandleNodeClick"></el-tree>
            <div slot="reference"
                 style="width: 130px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 3px"
                 @click="showDepView2">{{inputDepName}}
            </div>
          </el-popover>
        </el-col>
        <el-col :span="10">
          入职日期:
          <el-date-picker
              v-model="searchValue.beginDateScope"
              type="daterange"
              size="mini"
              unlink-panels
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
          </el-date-picker>
        </el-col>
        <el-col :span="5" :offset="4">
          <el-button size="mini" @click="showAdvanceSearchVisible = false">取消</el-button>
          <el-button size="mini" icon="el-icon-search" type="primary" @click="initEmps('advanced')">搜索</el-button>
        </el-col>
      </el-row>
    </div>
    </transition>
      <div>
      <el-table
          :data="emps"
          style="width: 100%">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            fixed 
            width="70"><!--fixed 靠左边冻结-->
        </el-table-column>
        <el-table-column
            prop="workId"
            label="工号"
            width="100">
        </el-table-column>
        <el-table-column
            prop="gender"
            width="50"
            label="性别">
        </el-table-column>
        <el-table-column
            prop="birthday"
            width="120"
            align="left"
            label="出生日期">
        </el-table-column>
        <el-table-column
            prop="idCard"
            width="130"
            align="left"
            label="身份证号">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            width="90"
            label="婚姻状况">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            width="50"
            align="left"
            label="民族">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            width="90"
            label="籍贯">
        </el-table-column>
        <el-table-column
            prop="politicsStatus.name"
            width="120"
            align="left"
            label="政治面貌">
        </el-table-column>
        <el-table-column
            prop="email"
            width="160"
            align="left"
            label="电子邮件">
        </el-table-column>
        <el-table-column
            prop="phone"
            width="120"
            align="left"
            label="电话号码">
        </el-table-column>
        <el-table-column
            prop="address"
            width="300"
            align="left"
            label="联系地址">
        </el-table-column>
        <el-table-column
            prop="department.name"
            width="100"
            align="left"
            label="所属部门">
        </el-table-column>
        <el-table-column
            prop="joblevel.name"
            width="100"
            align="left"
            label="职称">
        </el-table-column>
        <el-table-column
            prop="position.name"
            width="100"
            align="left"
            label="职位">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            width="100"
            align="left"
            label="聘用形式">
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            width="80"
            align="left"
            label="最高学历">
        </el-table-column>
        <el-table-column
            prop="school"
            width="140"
            align="left"
            label="毕业院校">
        </el-table-column>
        <el-table-column
            prop="specialty"
            width="140"
            align="left"
            label="专业">
        </el-table-column>
        <el-table-column
            prop="workState"
            width="80"
            align="left"
            label="在职状态">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            width="100"
            align="left"
            label="入职日期">
        </el-table-column>
        <el-table-column
            prop="conversionTime"
            width="100"
            align="left"
            label="转正日期">
        </el-table-column>
        <el-table-column
            prop="beginContract"
            width="100"
            align="left"
            label="合同起始日期">
        </el-table-column>
        <el-table-column
            prop="endContract"
            width="100"
            align="left"
            label="合同截至日期">
        </el-table-column>
        <el-table-column
            width="100"
            align="left"
            label="合同期限">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.contractTerm }}</el-tag>
            年
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            width="200"
            label="操作">
          <template slot-scope="scope"> <!--靠右边冻结-->
            <el-button @click="showEditEmpView(scope.row)" style="padding: 3px" size="mini">编辑</el-button>
            <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>
            <el-button @click="deleteEmp(scope.row)" style="padding: 3px" size="mini" type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="display:flex; justify-content: center">
        <el-pagination
            background
            @current-change="currentChange"
            @size-change="sizeChange"
            layout="sizes, prev, pager, next, ->, total"
            :page-sizes="pageSize"
            :total="total">
        </el-pagination>
      </div>
      
    </div>
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="80%">
      <div>
        <el-form :model="emp" :rules="rules" ref="empForm">
          <el-row>
            <el-col :span="6">
              <el-form-item label="姓名:" prop="name"> <!-- 此处的name和rules校验规则有关 -->
                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="emp.name"
                          placeholder="请输入员工姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="性别:" prop="gender">
                <el-radio-group v-model="emp.gender" style="margin-top: 8px">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="出生日期:" prop="birthday">
                <el-date-picker
                    v-model="emp.birthday"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 150px;"
                    placeholder="出生日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="政治面貌:" prop="politicId">
                <el-select v-model="emp.politicId" placeholder="政治面貌" size="mini" style="width: 200px;">
                  <el-option
                      v-for="item in politicsStatus"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="民族:" prop="nationId">
                <el-select v-model="emp.nationId" placeholder="民族" size="mini" style="width: 150px;">
                  <el-option
                      v-for="item in nations"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="籍贯:" prop="nativePlace">
                <el-input size="mini" style="width: 120px" prefix-icon="el-icon-edit"
                          v-model="emp.nativePlace" placeholder="请输入籍贯"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="电子邮箱:" prop="email">
                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-message"
                          v-model="emp.email" placeholder="请输入电子邮箱"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="联系地址:" prop="address">
                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                          v-model="emp.address" placeholder="请输入联系地址"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="职位:" prop="posId">
                <el-select v-model="emp.posId" placeholder="职位" size="mini" style="width: 150px;">
                  <el-option
                      v-for="item in positions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="职称:" prop="jobLevelId">
                <el-select v-model="emp.jobLevelId" placeholder="职称" size="mini" style="width: 150px;">
                  <el-option
                      v-for="item in joblevels"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属部门:" prop="departmentId">
                <el-popover
                    placement="right"
                    title="请选择部门"
                    width="200"
                    style="margin-top: 8px"
                    trigger="manual"
                    v-model="popVisible">
                  <el-tree default-expand-all 
                           :data="allDeps" 
                           :props="defaultProps" 
                           :expand-on-click-node="false"
                           @node-click="handleNodeClick"></el-tree>
                  <div slot="reference"
                       style="width: 150px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                       @click="showDepView">{{inputDepName}}
                  </div>
                </el-popover>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="电话号码:" prop="phone">
                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-phone"
                          v-model="emp.phone" placeholder="电话号码"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="工号:" prop="workID">
                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
                          v-model="emp.workId" placeholder="工号" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="学历:" prop="tiptopDegree">
                <el-select v-model="emp.tiptopDegree" placeholder="学历" size="mini"
                           style="width: 150px;">
                  <el-option
                      v-for="item in tiptopDegrees"
                      :key="item"
                      :label="item"
                      :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="毕业院校:" prop="school">
                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
                          v-model="emp.school" placeholder="毕业院校名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="专业名称:" prop="specialty">
                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                          v-model="emp.specialty" placeholder="请输入专业名称"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="入职日期:" prop="beginDate">
                <el-date-picker
                    v-model="emp.beginDate"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="入职日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="转正日期:" prop="conversionTime">
                <el-date-picker
                    v-model="emp.conversionTime"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="转正日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同起始日期:" prop="beginContract">
                <el-date-picker
                    v-model="emp.beginContract"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="合同起始日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="合同终止日期:" prop="endContract">
                <el-date-picker
                    v-model="emp.endContract"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 170px;"
                    placeholder="合同终止日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="身份证号码:" prop="idCard">
                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
                          v-model="emp.idCard" placeholder="请输入身份证号码"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="聘用形式:" prop="engageForm">
                <el-radio-group v-model="emp.engageForm" style="margin-top: 8px">
                  <el-radio label="劳动合同" style="margin-top: 6px">劳动合同</el-radio>
                  <el-radio label="劳务合同" style="margin-top: 6px">劳务合同</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="婚姻状况:" prop="wedlock">
                <el-radio-group v-model="emp.wedlock" style="margin-top: 8px">
                  <el-radio label="已婚" style="margin-top: 6px">已婚</el-radio>
                  <el-radio label="未婚" style="margin-top: 6px">未婚</el-radio>
                  <el-radio label="离异" style="margin-top: 6px">离异</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="在职状态:" prop="workState">
                <el-radio-group v-model="emp.workState" style="margin-top: 8px">
                  <el-radio label="在职" style="margin-top: 6px">在职</el-radio>
                  <el-radio label="离职" style="margin-top: 6px">离职</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible=false">取 消</el-button>
    <el-button type="primary" @click="doAddEmp">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "EmpBasic",
    data(){
      return{
        emps: [],
        total: 0,
        currentPage: 1,
        size: 10,
        empName: '',
        pageSize: [5,10,20,50,100],
        dialogVisible: false,
        politicsStatus: [],
        nations: [],
        joblevels: [],
        positions: [],
        allDeps: [],// 部门
        popVisible: false,// 部门树状结构是否显示
        tiptopDegrees: ['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        inputDepName:'',// input里的部门名称
        title: '',
        headers: {
          Authorization: window.sessionStorage.getItem("tokenStr")
        },
        importDataDisabled: false,
        importDataBtnText: '导入数据',
        importDataBtnicon: 'el-icon-download',
        searchValue: {// 高级搜索
          politicId: null,
          nationId: null,
          jobLevelId: null,
          posId: null,
          engageForm: null,
          departmentId: null,
          beginDateScope: null
        },
        popVisible2: false,
        showAdvanceSearchVisible: false,
        emp: {// 编辑信息
          id: null,
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: null,
          nativePlace: '',
          politicId: null,
          email: '',
          phone: '',
          address: '',
          departmentId: null,
          jobLevelId: null,
          posId: null,
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: null,
          workState: '',
          workId: '',
          contractTerm: null,
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          workAge: null,
          salaryId: null,
          salary: null
        },
        rules: {// 校验规则
          name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
          birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
          idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'}, {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: '身份证号码格式不正确',
            trigger: 'blur'
          }],
          wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
          nationId: [{required: true, message: '请输入您组', trigger: 'blur'}],
          nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
          politicId: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
          email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}, {
            type: 'email',
            message: '邮箱格式不正确',
            trigger: 'blur'
          }],
          phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
          address: [{required: true, message: '请输入员工地址', trigger: 'blur'}],
          departmentId: [{required: true, message: '请输入部门名称', trigger: 'blur'}],
          jobLevelId: [{required: true, message: '请输入职称', trigger: 'blur'}],
          posId: [{required: true, message: '请输入职位', trigger: 'blur'}],
          engageForm: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
          tiptopDegree: [{required: true, message: '请输入学历', trigger: 'blur'}],
          specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
          school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
          beginDate: [{required: true, message: '请输入入职日期', trigger: 'blur'}],
          workState: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
          contractTerm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
          conversionTime: [{required: true, message: '请输入转正日期', trigger: 'blur'}],
          notworkDate: [{required: true, message: '请输入离职日期', trigger: 'blur'}],
          beginContract: [{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
          endContract: [{required: true, message: '请输入合同结束日期', trigger: 'blur'}],
          workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}],
        }
      }
    },
    mounted() {
      this.initEmps();
      this.initData();
    },
    methods:{
      // 显示部门树
      showDepView2() {
        this.popVisible2 = !this.popVisible2
      },
      // 高级搜索 --部门树
      searvhViewHandleNodeClick(data) {
        this.inputDepName = data.name;
        this.searchValue.departmentId = data.id;
        this.popVisible2 = !this.popVisible2
      },
      // 导入数据相关
      onSuccess() {
        this.importDataBtnicon = 'el-icon-download';
        this.importDataBtnText = '导入数据';
        this.importDataDisabled = false;
        this.initEmps();
      },
      onError() {
        this.importDataBtnicon = 'el-icon-download';
        this.importDataBtnText = '导入数据';
        this.importDataDisabled = false;
      },
      beforeUpload() {
        this.importDataBtnicon = 'el-icon-loading';
        this.importDataBtnText = '正在导入';
        this.importDataDisabled = true;

      },
      // 导出数据
      exportData(){
        this.downloadRequest('/employee/basic/export');
      },
      // 显示编辑框
      showEditEmpView(data) {
        this.initPositions();
        this.title = '编辑员工信息';
        this.emp = data;
        this.inputDepName = data.department.name;
        this.dialogVisible = true;
      },
      // 删除员工
      deleteEmp(data) {
        this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/employee/basic/" + data.id).then(resp => {
            if (resp) {
              this.initEmps();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      // 添加或编辑员工
      doAddEmp() {
        if (this.emp.id) {// 如果有id，则是编辑操作
          this.$refs['empForm'].validate(valid => {
            if (valid) {
              this.putRequest("/employee/basic/", this.emp).then(resp => {
                if (resp) {
                  this.dialogVisible = false;
                  this.initEmps();
                }
              })
            }
          });
        } else {// 没有id，为添加操作
          this.$refs['empForm'].validate(valid => {
            if (valid) {
              this.postRequest("/employee/basic/", this.emp).then(resp => {
                if (resp) {
                  this.dialogVisible = false;
                  this.initEmps();
                }
              })
            }
          });
        }
      },
      // 节点点击事件
      handleNodeClick(data) {
        this.inputDepName = data.name;
        this.emp.departmentId = data.id;
        this.popVisible = !this.popVisible
      },
      showDepView() {
        this.popVisible = !this.popVisible;
      },
      // 获取工作id
      getMaxWordID() {
        this.getRequest("/employee/basic/maxWorkID").then(resp => {
          if (resp) {
            this.emp.workId = resp.obj;
          }
        })
      },
      // 编辑界面部分复杂数据的显示
      initData() {
        if (!window.sessionStorage.getItem("nations")) {
          this.getRequest('/employee/basic/nations').then(resp => {
            if (resp) {
              this.nations = resp;
              window.sessionStorage.setItem("nations", JSON.stringify(resp));
            }
          })
        } else {
          this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
        }
        if (!window.sessionStorage.getItem("joblevels")) {
          this.getRequest('/employee/basic/joblevels').then(resp => {
            if (resp) {
              this.joblevels = resp;
              window.sessionStorage.setItem("joblevels", JSON.stringify(resp));
            }
          })
        } else {
          this.joblevels = JSON.parse(window.sessionStorage.getItem("joblevels"));
        }
        if (!window.sessionStorage.getItem("politicsStatus")) {
          this.getRequest('/employee/basic/politicsStatus').then(resp => {
            if (resp) {
              this.politicsStatus = resp;
              window.sessionStorage.setItem("politicsStatus", JSON.stringify(resp));
            }
          })
        } else {
          this.politicsStatus = JSON.parse(window.sessionStorage.getItem("politicsStatus"));
        }
        if (!window.sessionStorage.getItem("deps")) {
          this.getRequest('/employee/basic/deps').then(resp => {
            if (resp) {
              this.allDeps = resp;
              window.sessionStorage.setItem("deps", JSON.stringify(resp));
            }
          })
        } else {
          this.allDeps = JSON.parse(window.sessionStorage.getItem("deps"));
        }
        if (!window.sessionStorage.getItem("positions")) {
          this.getRequest('/employee/basic/positions').then(resp => {
            if (resp) {
              this.positions = resp;
              window.sessionStorage.setItem("positions", JSON.stringify(resp));
            }
          })
        } else {
          this.positions = JSON.parse(window.sessionStorage.getItem("positions"));
        }
      },
      // 添加员工界面框
      showAddEmpView() {
        // 防止点击编辑之后，再点击添加，数据没有清除
        this.emp= {
          id: null,
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: null,
          nativePlace: '',
          politicId: null,
          email: '',
          phone: '',
          address: '',
          departmentId: null,
          jobLevelId: null,
          posId: null,
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: null,
          workState: '',
          workId: '',
          contractTerm: null,
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          workAge: null,
          salaryId: null,
          salary: null
        };
        this.title = '添加员工';
        this.inputDepName = '';
        this.getMaxWordID();
        this.dialogVisible = true;
      },
      dialogVisible(){
        this.dialogVisible = true
      },
      sizeChange(size){
        this.size = size;
        this.initEmps();
      },
      currentChange(currentPage){
        this.currentPage = currentPage;
        this.initEmps();
      },
      // 初始化职位
      initPositions() {
        this.getRequest('/employee/basic/positions').then(resp => {
          if (resp) {
            this.positions = resp;
          }
        })
      },
      // 初始化员工数据（分页）
      initEmps(type){
        // 添加条件
        let url = '/employee/basic/?currentPage=' + this.currentPage + '&size=' + this.size;
        if (type && type == 'advanced') {
          if (this.searchValue.politicId) {
            url += '&politicId=' + this.searchValue.politicId;
          }
          if (this.searchValue.nationId) {
            url += '&nationId=' + this.searchValue.nationId;
          }
          if (this.searchValue.jobLevelId) {
            url += '&jobLevelId=' + this.searchValue.jobLevelId;
          }
          if (this.searchValue.posId) {
            url += '&posId=' + this.searchValue.posId;
          }
          if (this.searchValue.engageForm) {
            url += '&engageForm=' + this.searchValue.engageForm;
          }
          if (this.searchValue.departmentId) {
            url += '&departmentId=' + this.searchValue.departmentId;
          }
          if (this.searchValue.beginDateScope) {
            url += '&beginDateScope=' + this.searchValue.beginDateScope;
          }
        } else {
          url += "&name=" + this.empName;
        }
        this.getRequest(url).then(resp=>{
          if(resp){
            this.emps = resp.data;
            this.total = resp.total;
          }
        })
      }
    }
  }
</script>

<style scoped>
  /* 可以设置不同的进入和离开动画 */
  /* 设置持续时间和动画函数 */
  .slide-fade-enter-active {
    transition: all .5s ease;
  }
  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }
  .slide-fade-enter, .slide-fade-leave-to
    /* .slide-fade-leave-active for below version 2.1.8 */ {
    transform: translateX(10px);
    opacity: 0;
  }
</style>