<template>
<div id="app">
  <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
  <div class="table">
  <div style="text-align: left">
    <p style='font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;'>员工管理 {{tableDatas.name}} {{select_word}}</p>
  </div>
  <div class="handle-box" style="text-align: left">
    <el-select v-model="select_cate" placeholder="搜索类型" class="handle-select mr10">
      <el-option v-for="send in sendList" :name="send.typename" :key="send.typename" :label="send.typename" :value="send.typename"></el-option>
    </el-select>
    <el-input v-model="select_word" placeholder="筛选关键词" class="handle-input mr10"></el-input>
      <el-date-picker style="width: 140px"
        v-model="value2"
        type="date"
        placeholder="开始日期" value-format="yyyy-MM-dd"
        :picker-options="pickerOptions1">
      </el-date-picker>
    <i class="el-icon-arrow-right"></i>
    <el-date-picker style="width: 140px"
                    v-model="value1"
                    type="date"
                    placeholder="结束日期" value-format="yyyy-MM-dd"
                    :picker-options="pickerOptions1">
    </el-date-picker>
    <el-button type="primary" icon="search" @click="search">搜索</el-button>
   <el-table
     :data="tableData"
     border
    class="table" style="width: 100%" ref="multipleTable"  @row-dbclick="doubleclick(scope.$index)" @selection-change="handleSelectionChange">
     <el-table-column
       prop="userId"
       label="编号"
       width="180" >
     </el-table-column>
     <el-table-column
       prop="userName"
       label="姓名"
       width="180"
       >
     </el-table-column>
     <el-table-column
       prop="sex"
       label="性别"
       width="180"
       :filters="[{text: '男', value: '男'}, {text: '女', value: '女'}]"
       :filter-method="filterHandler">
     </el-table-column>
     <el-table-column
       prop="phone"
       label="手机号"
       width="180" >
     </el-table-column>
     <el-table-column
       prop="email"
       label="邮箱"
       width="180" >
     </el-table-column>
     <el-table-column
       prop="status"
       label="状态"
       width="180"
       :filters="[{text: '在线', value: '在线'}, {text: '下线', value: '下线'}]"
       :filter-method="filterHandler">
     </el-table-column>
     <el-table-column
       prop="createDate"
       label="注册日期"
       width="180" >
     </el-table-column>
     <el-table-column
       prop="address"
       label="地址" :formatter="formatter">
     </el-table-column>
     <el-table-column
       fixed="right"
       label="操作"
       width="100">
       <template slot-scope="scope">
         <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
         <el-button type="text" size="small">编辑</el-button>
       </template>
     </el-table-column>
   </el-table>
  </div>
  <div class="block">
    <el-pagination
      @size-change="handleSizeChange"
      background @current-change="handleCurrentChange"
      @prev-click="handlePrevPage"
      :current-page="currentPage4"
      :page-sizes="[1, 2, 3, 4]"
      :page-size="1"
      layout="total, sizes, prev, pager, next, jumper"
      :total="cur_total">
    </el-pagination>
  </div>
  </div>
</div>

</template>

<script>

  import workerApi from '@/api/schedule'
  export default{
    name:'employee',
    data() {
      return {
        select_cate:'姓名',
        select_word:'',
        del_list:[],
        cur_page:1,
        cur_page_size:1,
        cur_total:0,
        url:'',
        sendList:[{'typename': '编号'},{'typename': '姓名'},{'typename': '手机号'},{'typename': '邮箱'}],
        pickerOptions1: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        value1: '',
        value2: '',
        currentPage4: 4,
        msg: 'Welcome to Your Vue.js App',
        tableData: [{
          userId:'1312',
          userName: '王小虎',
          sex:'男',
          phone:'12345678952',
          email:'321498@163.com',
          createDate: '2016-05-02',
          status:'status',
          address: '上海市普陀区金沙江路 153 弄'
        }, {
          userId:'1312',
          userName: '王老虎',
          sex:'男',
          phone:'12345678952',
          email:'321498@163.com',
          createDate: '2016-05-02',
          status:'status',
          address: '上海市普陀区金沙江路 153 弄'
        }, {
          userId:'1312',
          userName: '王d大虎',
          sex:'男',
          phone:'12345678952',
          email:'321498@163.com',
          createDate: '2016-05-02',
          status:'status',
          address: '上海市普陀区金沙江路 153 弄'
        }, {
          userId:'1312',
          userName: '王小花',
          sex:'女',
          phone:'12345678952',
          email:'321498@163.com',
          createDate: '2016-05-02',
          status:'status',
          address: '上海市普陀区金沙江路 153 弄'
        }]
      }
    },
    //监控表格的内容发生变化
    // computed: {
    //   data() {
    //     return this.tableData.filter((d) => {
    //       let is_del = false;
    //       for (let i = 0; i < this.del_list.length; i++) {
    //         if (d.name === this.del_list[i].name) {
    //           is_del = true;
    //           break;
    //         }
    //       }
    //       if (!is_del) {
    //         if (d.address.indexOf(this.select_cate) > -1 &&
    //           (d.name.indexOf(this.select_word) > -1 ||
    //             d.address.indexOf(this.select_word) > -1)
    //         ) {
    //           return d;
    //         }
    //       }
    //     })
    //   }
    // },
    created() {
      // alert("我执行了careated的方法");
      this.getAllNum();
      this.getData();
    },
    computed: {
    data(){
      alert("我是computed方法")
    },
      tableDatas: function() {
        var _search = this.select_word;
        if (_search) {
          return this.tableData.filter(function(product) {
            return Object.keys(product).some(function(key) {
              return String(product[key]).toLowerCase().indexOf(_search) > -1
            })
          })
        }
        return this.tableData;
      }
    },
methods:{
  //获得初始化数据
  getData(){
    // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
    workerApi.getAllWorkerData(this.cur_page,this.cur_page_size).then(res=>{
      // alert("获得的数据："+JSON.stringify(res.data));
      // this.$message.success("成功获得数据");
      this.tableData = res.data.data;
      this.LevelAndStatusOfHandle();//数据进行可视化处理
    }).catch(function(reason) {
      console.log('catch:', reason);
    });
  },

  getAllNum(){
    workerApi.getAllNum().then(res=>{
      // alert("获得的总人数据："+JSON.stringify(res.data));
        this.cur_total=res.data.data;//获得用户的总数量
    }).catch(function(reason) {
      console.log('catch:', reason);
    });
  },
  //对获得的数据进行可视化处理的方法
  LevelAndStatusOfHandle(){
    for (var i=0;i<this.tableData.length;i++){
      if(this.tableData[i].sex==1){
        this.tableData[i].sex='男'
      }else {
        this.tableData[i].sex='女'
      }
      if(this.tableData[i].status==1){
        this.tableData[i].status='在线'
      }else{
        this.tableData[i].status='下线'
      }
    }
  },
  handleClick(row) {
    //查看的内容
    alert("查看的内容"+JSON.stringify(row))
    console.log(row);
    // this.$router.push({path:'WorkorTable',query:{worktor:row.name}});
    this.$router.push({path:'WorkorTable',query:{worker:JSON.stringify(row)}});

  },
  search() {
    this.is_search = true;
    alert("搜索的内容："+this.select_cate+":"+this.select_word+"start:"+this.value1+"end:"+this.value2);
    var search={
      select_word: this.select_word,
      select_cate: this.select_cate,
      satrtDate:this.value1,
      endDate:this.value2
    };
    workerApi.getSomeWorkerData(search).then(res=>{}).catch(function (reason){
      console.log('catch:', reason);
    })

  },
  doubleclick(index){
    alert("你好我是双击事件"+JSON.stringify(index))
  },
  handlePrevPage(val){
    console.log(`上页 ${val} 条`);
  },
  handleNextPage(val){
    console(`下页 ${val} 条`);
  },
  handleSizeChange(val) {
    this.cur_page_size=val;
    this.getData();
    console.log(`每页 ${val} 条`);
  },
  handleCurrentChange(val) {
    this.cur_page=val;
    this.getData();
    console.log(`当前页: ${val}`);
  },
  handleSelectionChange(){
    console("我改变了");
  },
  formatter(row, column) {
    return row.address;
  },
  filterHandler(value, row, column) {
    const property = column['property'];
    return row[property] === value;
  }
}
  }
</script>

<style scoped>
  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 100px;
  }

  .handle-input {
    width: 200px;
    display: inline-block;
  }
  .del-dialog-cnt{
    font-size: 16px;
    text-align: center
  }
  .table{
    width: 100%;
    font-size: 14px;
  }
  .red{
    color: #ff0000;
  }
  .mr10{
    margin-right: 10px;
  }
</style>
