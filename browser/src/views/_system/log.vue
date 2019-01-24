<template>
  <div class="app" align="center">
    <h3>日志操作</h3>
    <div>
      <!--时间-->
      <span class="demonstration">开始</span>
      <el-date-picker
        v-model="value1"
        type="date"
        placeholder="选择日期"
        :picker-options="pickerOptions1"
        value-format="yyyy-MM-dd">
      </el-date-picker>
      <span class="demonstration">结束</span>
      <el-date-picker
        v-model="value2"
        type="date"
        placeholder="选择日期"
        :picker-options="pickerOptions1"
        value-format="yyyy-MM-dd">
      </el-date-picker>
      <!--选择器-->
      <el-select v-model="value" placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <!--输入框-->
      <el-input v-model="search" placeholder="请输入要查询的用户名或账号" style="width: 20%"></el-input>
      <!--按钮-->
      <el-button type="primary" icon="el-icon-search" @click="btn">搜索</el-button>
    </div>
    <br/>
    <el-table
      :data="tableData"
      border
      style="width: 100%" >
      <el-table-column
        prop="logId"
        label="序号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="logType"
        label="日志类型"
        width="120">
      </el-table-column>
      <el-table-column
        prop="logContent"
        label="详细操作">
      </el-table-column>
      <el-table-column
        prop="logErrorCode"
        label="错误码"
        width="250">
      </el-table-column>
      <el-table-column
        prop="uid"
        label="用户账号">
      </el-table-column>
      <el-table-column
        prop="nick"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="logDate"
        label="操作日期">
      </el-table-column>
    </el-table>
    <br/>
    <!--分页-->
    <div>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        @prev-click="handlePrevPage"
        :current-page="currentPage4"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="cur_total">
      </el-pagination>

    </div>
  </div>
</template>

<script>

  import logApi from '@/api/log'

  export default {
    name: 'log',
    data() {
      return {
        pickerOptions1: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          }
        },
        options: [{
          value: '用户操作',
          label: '用户操作'
        }, {
          value: '查询',
          label: '查询'
        }, {
          value: '删除',
          label: '删除'
        }, {
          value: '添加',
          label: '添加'
        },{
          value: '修改',
          label: '修改'
        }],
        tableData: [{
          logId: '',
          logType:'',
          logErrorCode: '',
          logContent:'',
          uid:'',
          nick: '',
          logDate: '',
        }],
        currentPage4: 1,//默认从第几页开始
        value: '',//下拉选择
        search: '',//搜索数据
        value1: '',//第一个日期
        value2: '',//第二个日期
        cur_total:0,//显示的总条数
        cur_page:1,//跳转的页数
        cur_page_size:10,//一页显示的条数

      };
    },
    methods: {

      /*显示总人数*/
      logNumALL(){
        logApi.logNumAll() .then((res) => {
          /*  alert(JSON.stringify(res.data))*/
          this.cur_total = res.data.data
        })
        /*页面加载的数据*/
        logApi.selectLog(this.cur_page,this.cur_page_size) .then((res) => {
         /* alert(JSON.stringify(res.data))*/
          this.tableData = res.data.data
        })

      },

      /*每页显示的条数*/
      handleSizeChange(val) {
        this.cur_page_size = val
        this.logNumALL();
        console.log(`每页 ${val} 条`);
      },
      /*当前页显示的跳数*/
      handleCurrentChange(val) {
        this.cur_page = val
        this.logNumALL();
        console.log(`当前页: ${val}`);
      },
      /*返回上一页显示的条数*/
        handlePrevPage(val){
    console.log(`上页 ${val} 条`);
  },
  /*返回下一页显示的条数*/
  handleNextPage(val){
    console(`下页 ${val} 条`);
  },
  /*搜索框查询的信息*/
  btn:function () {
    var search = this.search
    alert("是什么:" + this.search + this.value1+this.value2+this.value)
  },

  },
  mounted() {

  },
  created() {
    /* alert("我执行了careated的方法");*/
    this.logNumALL();
  },

  };
</script>
