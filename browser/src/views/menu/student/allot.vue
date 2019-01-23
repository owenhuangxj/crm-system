<template>
  <!--主界面-->
  <div id="dataGrid">

    <div id="tool-bar">
      <el-button @click="toggleSelection()"><i class="el-icon-close"></i>取消选择</el-button>
      <el-button><i class="el-icon-plus" @click=dialog(0)></i>新增简历</el-button>
      <el-button><i class="el-icon-upload" @click=dialog(1)>简历导入</i> </el-button>
      <el-button><i class="el-icon-message" @click=dialog(2)>简历分配</i> </el-button>
      <el-button><i class="el-icon-delete"></i> 删除简历</el-button>

    </div>
    <div id="table">
      <el-table ref="multipleTable" :data="resumeData" tooltip-effect="dark"  :default-sort = "{prop: 'date', order: 'descending'}"  @selection-change="selectionChange">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="right" inline class="demo-table-expand">
              <el-form-item label="现在住址"><span>{{ props.row.stuAddress }}</span></el-form-item>
              <el-form-item label="工作经历"><span>{{ props.row.stuWork }}</span></el-form-item>
              <el-form-item label="培训经历"><span>{{ props.row.stuTrained }}</span></el-form-item>
              <el-form-item label="项目经验"><span>{{ props.row.stuProject }}</span></el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection"width="55"></el-table-column>
        <el-table-column label="序号" type="index" width="50"></el-table-column>
        <el-table-column prop="stuName" label="姓名" width="120"></el-table-column>
        <el-table-column prop="stuSex" label="性别" width="120"></el-table-column>
        <el-table-column prop="stuPhoneNum" label="电话" width="120"></el-table-column>
        <el-table-column prop="stuCreateTime" label="录入日期" width="120"></el-table-column>
        <el-table-column prop="stuSource" label="来源" width="120"></el-table-column>
        <el-table-column prop="stuChannel" label="方式" width="120"></el-table-column>
      </el-table>
    </div>

    <div id="pagination">

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalData">
      </el-pagination>
    </div>


    <el-dialog
      title="简历分配"
      :visible.sync="allotDialog"
      :before-close="handleClose">
      <ResumeAllot></ResumeAllot>
    </el-dialog>

      <el-dialog
        title="简历导入"
        :visible.sync="importDialog"
        :before-close="handleClose">
   <ResumeImport> </ResumeImport>

    </el-dialog>



  </div>

</template>

<script>
  import  ResumeAllot  from './resume-allot.vue';
  import  ResumeImport  from './resume-import.vue';
  import allotApi from  '@/api/allot';

  let resumeData=[{
    stuName: '12',
    stuNumber: '321',
    stuSex: '321',
    stuPhoneNum: '321',
    stuAddress: '321',
    stuCreateTime:"11",
    stuSource: '21321',
    stuWork: '321',
    stuProject: '123',
    stuTrained: '123',
  }
  ];

  let totalData=1;

  export default {
    components: {
      ResumeAllot,
      ResumeImport
    },

    data() {
      return {
        resumeData: null,
        multipleSelection: [],
        currentPage: 0,
        totalData:totalData,
        allotDialog: false,
        importDialog: false,
        newDialog:false,
        selectedId:null,
      }
    },
    created() {
      this.getData();
    },
    computed: {
      data(){
        alert("computed方法执行")
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
        allotApi.getUnAllotResume().then(response => {
          this.resumeData = response.data.data.records;
          /*this.resumeData=res.data.value.records;
          this.curentPage=res.data.value.current;
          this.totalData=res.data.value.total;*/
        })
      },
      /**打开对话框*/
      dialog(index){
        switch (index) {
          case 0: this.newDialog=true;
          break;
          case 1: this.importDialog=true;
          break;
          case 2: this.allotDialog=true;
          break;
        }
        this.getSelectedId()
      },
      handleClick(row) {
        alert("查看的内容"+JSON.stringify(row))
        console.log(row);
      },
      search() {
        this.is_search = true;
        alert("搜索的内容："+this.select_cate+":"+this.select_word+"start:"+this.value1+"end:"+this.value2);
      },
      selectionChange(val){
        this.multipleSelection = val
      },
      getSelectedId(){
        let selectedId=new Array() ;

      for (let item of this.multipleSelection){
      selectedId.push(item.stuNumber);
      }
        this.selectedId=selectedId;
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
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
      },
      handleSizeChange(val) {
        this.cur_page_size=val;
        this.getData();
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        this.cur_page=val;
        this.getData();
        console.log(`当前页: ${curretnPage}`);
      },
      formatter(row, column) {
        return row.address;
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },

      filterHandler(value, row, column) {
        const property = column['property'];
        return row[property] === value;
      }
    },
    /*监控函数*/
    watch: {
      resumeData: {
        deep: true,
        handler: function (newVal, oldVal) {
          console.log('resumeData new:', newVal, 'old:', oldVal);
        },
      },
      selectedId:{
        deep: true,
        handler: function (newVal, oldVal) {
          console.log('selectedId new:', newVal, 'old:', oldVal);
        },
      },

    }


  }

</script>

<style scoped>
  /*数据框的主界面*/
  #dataGrid{
    display: -webkit-box;
    -webkit-box-orient: vertical;
    text-align: center;
    padding-left: 2em;
    padding-right: 2em;
  }
  #tool-bar{
    padding-bottom: 1em;
    padding-top: 2em;
  }
  #pagination{
    padding-bottom: 1em;
    padding-top: 1em;
  }
  /*分配简历窗口*/
  el-table{
    stripe:true;
    width: 100%;
  }
</style>
