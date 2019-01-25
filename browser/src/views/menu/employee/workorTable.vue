<template>
  <div id="app">
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <el-container>
      <el-header height="100px">
          <p>本周完成量</p>
        <div class="handle-box" style="text-align: left">
        <P class="yewu">{{worktor.userName}}:业务详情 今日完成量：{{numbers}}  未完成量：{{numbers2}} 完成率：</P>
      </div>
      </el-header>
      <el-main >
        <div @dblclick="doubleclick">
          <el-table
                  :data="tableData"
                  stripe
                  style="width: 100%"  @row-dbclick="doubleclick(scope.$index)">
            <el-table-column
                    prop="stuNumber"
                    label="编号"
                    width="120" >
            </el-table-column>
            <el-table-column
                    prop="stuName"
                    label="姓名"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="stuLevel"
                    label="优先级"
                    width="100" >
            </el-table-column>
            <el-table-column
                    prop="followSize"
                    label="跟踪次数"
                    width="90" >
            </el-table-column>
            <el-table-column
                    prop="stuPhoneNum"
                    label="手机号"
                    width="160" >
            </el-table-column>
            <el-table-column
                    prop="stuQq"
                    label="QQ"
                    width="160" >
            </el-table-column>
            <el-table-column
                    prop="stuChannel"
                    label="来源途径"
                    width="160" >
            </el-table-column>
            <el-table-column
                    prop="stuStatus"
                    label="进度情况"
                    width="160" >
            </el-table-column>
            <el-table-column
                    prop="stuAddress"
                    label="地址">
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
                  @current-change="handleCurrentChange"
                  @prev-click="handlePrevPage"
                  :current-page="currentPage4"
                  :page-sizes="[1, 2, 3, 4]"
                  :page-size="1"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="cur_total">
          </el-pagination>
        </div>
      </el-main>
    </el-container>

  </div>

</template>

<script>
  import workerApi from '@/api/schedule'

  export default {
    name:'WorkorTable',
    data() {
      return {
        worktor: JSON.parse(this.$route.query.worker),
        // worktor:'李小姐',
        userId:2,
        numbers:'10',
        numbers2:'5',
        numbers3:33,
        select_cate:'',
        select_word:'',
        cur_page:1,
        cur_page_size:1,
        cur_total:0,
        sendList:[{'typename': 'Id'},{'typename': '姓名'},{'typename': '年纪'},{'typename': '时间'}],
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
          stuId:'1312',
          stuNumber:'23156451',
          stuName: '王小虎',
          stuSex:'男',
          stuLevel:'高',
          followSize:'2',
          stuChannel:'网络招聘',
          stuPhoneNum:'12345678952',
          stuQq:'321498@163.com',
          date: '2016-05-02',
          stuStatus:'入学',
          stuAddress: '上海市普陀区金沙江路 153 弄'
        }, {
          stuId:'1312',
          stuNumber:'23156451',
          stuName: '王小虎',
          stuSex:'男',
          stuLevel:'高',
          followSize:'2',
          stuChannel:'网络招聘',
          stuPhoneNum:'12345678952',
          stuQq:'321498@163.com',
          date: '2016-05-02',
          stuStatus:'入学',
          stuAddress: '上海市普陀区金沙江路 153 弄'
        }, {
          stuId:'1312',
          stuNumber:'23156451',
          stuName: '王小虎',
          stuSex:'男',
          stuLevel:'高',
          followSize:'2',
          stuChannel:'介绍来的',
          stuPhoneNum:'12345678952',
          stuQq:'321498@163.com',
          date: '2016-05-02',
          stuStatus:'入学',
          stuAddress: '上海市普陀区金沙江路 15w弄'
        }, {
          stuId:'1312',
          stuNumber:'23156451',
          stuName: '王大虎',
          stuSex:'男',
          stuLevel:'高',
          followSize:'2',
          stuChannel:'网络招聘',
          stuPhoneNum:'12345678952',
          stuQq:'321498@163.com',
          date: '2016-05-02',
          stuStatus:'入学',
          stuAddress: '上海市普陀区金沙江路 153 弄'
        }]
      }
    },
    //获得初始化数据
    created() {
      alert("我执行了careated的方法");
      this.getAllNum();
      this.getData();
    },
    computed: {
      data() {
        alert("wozhixing");
      }
    },
    methods:{

      //获得初始化数据
      getData(){
        // 查询某个员工下的所有学员
        workerApi.getAllWorkerOfStuData(this.userId,this.cur_page,this.cur_page_size).then(res=>{
          alert("获得的数据："+JSON.stringify(res.data));
          // this.$message.success("成功获得数据");
          this.tableData = res.data.data;
          this.LevelAndStatusOfHandle();//数据进行可视化处理
        }).catch(function(reason) {
          console.log('catch:', reason);
        });
      },
      getAllNum(){
        //获得某个员工名下的学员的总人数
        workerApi.getAllStuNum().then(res=>{
          alert("获得的总人数据："+JSON.stringify(res.data));
          this.cur_total=res.data.data;//获得用户的总数量
        }).catch(function(reason) {
          console.log('catch:', reason);
        });
      },
      //对优先级和进度情况进行可视化处理
      LevelAndStatusOfHandle(){

        for (var i=0;i<this.tableData.length;i++){
          switch (this.tableData[i].stuLevel) {
            // 优先级别：1：无，2：低，3：中，4：高
            case '1':this.tableData[i].stuLevel='无';
            break;
            case '2':this.tableData[i].stuLevel='低';
            break;
            case '3':this.tableData[i].stuLevel='中';
            break;
            case '4':this.tableData[i].stuLevel='高';
            break;
            default:
              break;
          }
        };
        for (var i=0;i<this.tableData.length;i++){
          switch (this.tableData[i].stuStatus) {
            // 0：未分配，1：新增，2：跟踪中，3：待面试，4：面试未通过，5：面试通过，6：已缴未清，7：已缴费，8：入学，9：放弃入学，10：退学，11：已退费，12：放弃
            case '0':this.tableData[i].stuStatus='未分配';
              break;
            case '1':this.tableData[i].stuStatus='新增';
              break;
            case '2':this.tableData[i].stuStatus='跟踪中';
              break;
            case '3':this.tableData[i].stuStatus='待面试';
              break;
            case '4':this.tableData[i].stuStatus='面试未通过';
              break;
            case '5':this.tableData[i].stuStatus='面试通过';
              break;
            case '6':this.tableData[i].stuStatus='已缴未清';
              break;
            case '7':this.tableData[i].stuStatus='已缴费';
              break;
            case '8':this.tableData[i].stuStatus='入学';
              break;
            case '9':this.tableData[i].stuStatus='放弃入学';
              break;
            case '10':this.tableData[i].stuStatus='退学';
              break;
            case '11':this.tableData[i].stuStatus='已退费';
              break;
            case '12':this.tableData[i].stuStatus='放弃';
              break;
            default:
              break;
          }
        }
      },
      handleClick(row) {
        //查看的内容
        alert("查看的内容"+JSON.stringify(row))
        console.log(row);
        this.$router.push({path:'genzongxiangqing',query:{student:JSON.stringify(row)}})
      },
      doubleclick(index){
        alert("你好我是双击事件"+JSON.stringify(index) )
      },
      handlePrevPage(val){
        console.log(`上页 ${val} 条`);
      },
      handleNextPage(val){
        console(`下页 ${val} 条`);
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);

        this.cur_page_size=val;
        this.getData();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.cur_page=val;
        this.getData();

      }
    }
  }
</script>

<style scoped>
  .handle-box {
    margin-bottom: 20px;
  }

  .yewu{
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }
</style>
