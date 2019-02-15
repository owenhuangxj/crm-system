<template>
  <!--主界面-->
  <div id="dataGrid">

    <el-dialog id="searchDialog"
      title="请输入查询条件"
      :visible.sync="searchDialog">
      <div>
        <el-radio-group v-model="searchButton" size="medium">
          <el-radio-button label="姓名"></el-radio-button>
          <el-radio-button label="电话"></el-radio-button>
        </el-radio-group>
      </div>
      <div>
        <el-input  v-model="searchValue" style="width:60%" placeholder="请输出查询内容"></el-input>
      </div>
      <div>
        <el-button @click="getData">提交</el-button>
      </div>
    </el-dialog>

    <div id="tool-bar">
      <el-button @click="toggleSelection()"><i class="el-icon-close">取消选择</i></el-button>
      <el-button><i class="el-icon-search" @click=dialog(-1)>条件查询</i></el-button>
      <el-button @click=dialog()> <NewBasicInformation></NewBasicInformation></el-button>
      <el-button @click=dialog()>  <ResumeAllot  :selectedId="selectedId" :selectedNum="selectedNum" ></ResumeAllot></el-button>
      <el-button @click=dialog()><ResumeImport> </ResumeImport> </el-button>

      <el-button><i class="el-icon-delete" @click=dialog(3)>删除简历</i> </el-button>
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
        <el-table-column prop="stuNumber" label="编号" width="120"></el-table-column>
        <el-table-column prop="stuSex" label="性别" width="120"></el-table-column>
        <el-table-column prop="stuPhoneNum" label="电话" width="120"></el-table-column>
        <el-table-column prop="stuCreateTime" label="录入日期" width="200"></el-table-column>
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
  </div>

</template>

<script>
  import  ResumeAllot  from './resume-allot.vue';
  import  ResumeImport  from './resume-import.vue';
  import  NewBasicInformation  from './new-basic-information.vue';
  import allotApi from  '@/api/allot';

  let totalData=1;

  export default {
    components: {
      ResumeAllot,
      ResumeImport,
      NewBasicInformation,
    },

    data() {
      return {
        resumeData: [],
        multipleSelection: [],
        currentPage: 1,
        pageSize:10,
        searchButton:"姓名",
        searchValue:'',
        totalData:totalData,
        searchDialog:false,
        selectedId:[],
        selectedNum:[],
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
        let column ='0';
        if(this.searchButton=="姓名"){
          column='1';
        }else{
          column='2';
        }

        let param=new URLSearchParams();
        param.append("current",this.currentPage);
        param.append("size",this.pageSize);
        param.append("column",column);
        param.append("value",this.searchValue);

        allotApi.getUnAllotResume(param).then(response => {
          this.resumeData = response.data.data.records;
          this.searchDialog=false;
        })

      },
      delRows(){
        let param=new URLSearchParams();
        param.append("stuIds",this.selectedId)
        allotApi.delByStuId(param).then(response => {
          this.resumeData = response.data;
        })
      },
      /**打开对话框*/
      dialog(index){
        this.getSelectedId();
        this.getSelectedNum();
        switch (index) {
          case -1:/*搜索*/
            this.searchDialog=true;
          break;
          case 3:/*删除简历*/
            if(this.selectedId.length==0){
              this.$confirm('请选择您要删除的简历？')
                .then(_ => {
                  done();
                })
                .catch(_ => {
                });
            }else{
              this.$confirm('删除后不可恢复请确认？')
                .then(_ => {
                  this.delRows();
                  done();
                })
                .catch(_ => {
                });
            }
            break;
        }

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
      selectedId.push(item.stuId);
      }
      console.log(selectedId);
        this.selectedId=selectedId;
      },
      getSelectedNum(){
        let selectedNum=new Array() ;
        for (let item of this.multipleSelection){
          selectedNum.push(item.stuNumber);
        }
        console.log(selectedNum);
        this.selectedNum=selectedNum;
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
        this.pageSize=val;
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
      selectedNum:{
        deep: true,
        handler: function (newVal, oldVal) {
          console.log('selectedNum new:', newVal, 'old:', oldVal);
        },
      },
      searchButton:{
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

  #searchDialog{
    display: -webkit-box;
    -webkit-box-orient: vertical;
    padding-left: 2em;
    padding-right: 2em;
  }
  #searchDialog div{
    margin: 0.5em 0.5em;
  }

</style>
