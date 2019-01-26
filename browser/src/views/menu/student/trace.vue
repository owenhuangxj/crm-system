<template>
  <div id="tracktable">
    <div id="button_div">
      <div v-permission="['root']">
        <span id="stuInfo">学员信息操作</span>&nbsp;
        <!--<el-button class="el_bt" round @click="delResume"><i class="el-icon-delete"></i>&nbsp;删除简历</el-button>-->
        <el-button class="el_bt" round @click="recallResume"><i class="el-icon-circle-close-outline"></i>&nbsp;撤回简历
        </el-button>
        <el-button class="el_bt" round @click="getUserNames"><i class="el-icon-refresh"></i>&nbsp;调换咨询师</el-button>
      </div>
      <!--弹出“调换咨询电话”的对话框-->
      <el-dialog title="调换咨询师" :visible.sync="dialogFormVisible" center :append-to-body='true' :lock-scroll="false"
                 width="30%">
        <p>请选择调换的咨询师</p>
        <el-select v-model="username">
          <el-option v-for="item in userNames" :label="item.label" :value="item.value"></el-option>
        </el-select>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="doConfirm">确 定</el-button>
        </div>
      </el-dialog>

    </div>

    <div id="search">
      <!--下拉框-->
      <el-select style="width: 110px" v-model="value">
        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
      <!--输入框-->
      <el-input v-model="input" style="width: 200px" placeholder="请输入关键字"><i class="el-icon-search"></i></el-input>
      <!--搜索按键-->
      <el-button class="el_bt" type="primary" @click="doSearch">点击搜索</el-button>
    </div>

    <el-tooltip class="item" effect="dark" content="双击行查看学员详情" placement="top">
      <!--表格 @row-click：行的单击事件，@cell-dblclick：行的双击事件，@select-all：全选，@clearSelection：取消全选-->
      <el-table ref="multipleTable" border :data="tableData" @row-dblclick="rowClick($event)"
                @selection-change="selectionChange"
                @select-all="selectAll(tableData)" @clearSelection="clearSelect" reserve-selection
                :default-sort="{prop: 'stuLevel', order: 'descending'}">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="编号" width="55"></el-table-column>
        <el-table-column prop="flag" label="状态标识" width="80"></el-table-column>
        <el-table-column prop="stuNumber" label="学员编号" width="110"></el-table-column>
        <el-table-column prop="stuName" label="学员姓名" width="90"></el-table-column>
        <el-table-column prop="stuLevel" label="优先级" width="70"></el-table-column>
        <!--<el-table-column prop="trackCount" label="跟踪次数" width="80"></el-table-column>-->
        <el-table-column prop="stuPhoneNum" label="手机号" width="120"></el-table-column>
        <el-table-column prop="stuSource" label="来源途径" width="110"></el-table-column>
        <el-table-column prop="stuStatus" label="进度情况" width="95"></el-table-column>
        <el-table-column prop="trackNextTime" label="下次跟踪时间" width="155"></el-table-column>
        <el-table-column prop="trackWays" label="咨询方式" width="90"></el-table-column>
        <el-table-column prop="nick" label="咨询师" width="90"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="100"></el-table-column>
        <el-table-column prop="trackPredictTime" label="预计上门" width="100"></el-table-column>
      </el-table>
    </el-tooltip>
    <!--
    :total：总共有多少条数据，把后台获取到的数据总数复制给total就可以了
    :page-size：每页显示的条数
    :page-sizes：下拉框选择每页显示多少条数据
    -->
    <div class="fenye">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="currentPage" :page="pageNum" :page-size="pageSize" :total="total"
                     :page-sizes="[10, 20, 30, 40]" layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>


  </div>
</template>

<script>

  import traceApi from '@/api/trace'
  // 当然你也可以为了方便使用，将它注册到全局
  import permission from '@/directive/permission/index.js' // 权限判断指令

  export default {
    directives: {permission},
    data() {
      return {
        tableData: [], // 后台传入的table表数据
        userNames: [], // 所有咨询师
        username: '',
        options: [{
          value: '1',
          label: '姓名'
        }, {
          value: '2',
          label: '咨询师'
        }, {
          value: '3',
          label: '咨询方式'
        }],
        pageSize: 10,    // pagesize：一页显示多少条，
        total: 0,        // total：一共多少条
        pageNum: 1,      // 当前页
        currentPage: 1,  // 默认显示第1页
        input: '',       // 输入框
        value: '1',      // 下拉框，默认让它选中“姓名”
        multipleSelection: {},     // 储存选中行的数据
        dialogFormVisible: false,  // “调换咨询电话”的对话框 false为关闭
        form: {
          info: '',
          template: '智联招聘',
          name: '周梦'
        },
        ruleForm: {
          resource: ''
        }
      }
    },
    // created()钩子函数，页面加载时就会执行
    created() {
      this.getNewsList();
    },
    methods: {
      // 发送请求，获取相应的数据
      getNewsList() {
        traceApi.queryTraceInfo(
          this.pagingData()
        ).then(res => {
          this.tableData = res.data.data;
          this.total = res.data.code;
        }).catch(err => {
          this.$message({ // 警告框
            type: 'warning',
            message: '系统内部错误！请联系管理员。错误代码：' + err
          });
          console.log("发生错误：" + err);
        });

      },
      // 调换咨询师，获取所有咨询师供管理员选择
      getUserNames() {
        if (this.multipleSelection[0] !== undefined) {
          traceApi.queryUserNames({}).then(res => {
            this.userNames = res.data.data;
            console.log(res.data);
          }).catch(err => {
            console.log("错误信息：" + err)
          });
          this.dialogFormVisible = true;
        } else {
          this.$message({ // 提示框
            type: 'warning',
            message: '至少选择一个学员'
          });
          this.dialogFormVisible = false
        }
      },
      // 一页显示多少条
      handleSizeChange(val) {
        this.pageSize = val;
        this.getNewsList();
      },
      // 当前页
      handleCurrentChange(val) {
        this.pageNum = val;
        this.getNewsList();
      },
      // 点击查询时执行的函数
      doSearch() {
        if (this.input !== "") {
          this.getNewsList();
        } else {
          // 如果没输入查询关键字会给与提示
          this.$message({
            type: 'info',
            message: '请输入查询关键字'
          })
        }
      },

      // 双击行执行的函数
      rowClick(column) {
        console.log(column);
        const stu = column.stuNumber;
        alert("行的双击事件：" + stu);
        this.$router.push({name:'stutraceinfo',params:{stuNum:stu}})
      },
      // 单独选择一行或多行（未点全选的情况）
      selectionChange(val) {
        this.multipleSelection = val
      },
      // 取消全选
      clearSelect(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row)
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      // 全选
      selectAll(val) {
        this.multipleSelection = val;
      },
      // 撤回简历
      recallResume() {
        let selection = {
          trace: this.multipleSelection
        };
        if (this.multipleSelection[0] !== undefined) {
          this.$confirm('你将撤回该学员的简历, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            traceApi.updateStuStatus(selection).then(res => {
              for(var i = 0;i < this.multipleSelection.length;i++){
                this.multipleSelection[i].stuStatus = "未分配"
              }
              this.$message({
                type: "success",
                message: res.data.msg
              });
              this.clearSelect();
            })
          })
        } else {
          this.$message({
            type: "warning",
            message: "至少选择一个学员"
          });
        }

      },
      // 提交咨询电话时执行
      doConfirm() {
        // 调换咨询电话的参数
        let exchange = {
          nick: this.username,
          trace: this.multipleSelection
        };
        traceApi.exchangePhone(
          exchange
        ).then(res => {
          // 修改成功后及时回显
          for (var i = 0; i < this.multipleSelection.length; i++) {
            this.multipleSelection[i].nick = this.username;
          }
          this.clearSelect();
          // 提示用户修改成功
          this.$message({
            type: "info",
            message: res.data.msg
          });
        }).catch(err => {
          this.$message({
            type: "warning",
            message: "系统内部错误！请联系管理员。错误代码：" + err
          })
        });
        this.dialogFormVisible = false
      },
      // 提交
      submitUpload() {
        this.$refs.upload.submit();
      },

      // 分页参数的写法
      pagingData() {
        const params = new URLSearchParams();
        params.append('pageSize', this.pageSize);
        params.append('pageNum', this.pageNum);
        params.append('input', this.input);
        params.append('value', this.value);
        return params;
      },
    },

  }
</script>

<style>
  #tracktable {
    border: 1px solid #e3e3e3;
    width: 100%;
    height: 100%;
  }

  #button_div {
    border-bottom: 1px solid #e3e3e3;
    margin-top: 10px;
    height: 50px;
  }

  #stuInfo, .el_bt, #search {
    font-family: Microsoft YaHei;
    padding: 10px;
  }

</style>

