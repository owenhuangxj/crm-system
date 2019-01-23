<template>

    <div id="resumeAllot">


            <div id="tab_title" >
              <a @click="tabSwitch(0)">固定分配</a><a @click="tabSwitch(1)">随机分配</a>
            </div>
            <!--横线--> <!--滑动条-->
            <div id="tabScroll" class="split"><div  id="scrollBlock"></div></div>
            <!--人员选择-->
            <div id="tab-emp" class="context">
                <span>人&emsp;&ensp;员:</span>
                <el-select   class="allot_input" v-model="employeeSelect" multiple placeholder="请选择">
                    <el-option
                            v-for="item in employees"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                </div>
                  <div id="allot_num" class="context">
                    <span>分配数量:</span>
                    <el-input-number  class="allot_input"  v-model="num" :step="3" :min="1" :max="100" label="描述文字"></el-input-number>
                  </div>

             <div id="allot_time" class="context">
            <span>分配时间：</span>
            <el-radio v-model="radio" label="1" @change="hideTimePicker()">立刻发送</el-radio>
            <el-radio v-model="radio" label="2" @change="showTimePicker()">选择时间</el-radio>
            <br/>
            <div id="timePicker" class="context">
                 <el-date-picker  class="allot_input" v-model="dateDefault" type="datetime" placeholder="选择任务时间" align="right" :picker-options="timePicker"></el-date-picker>
            </div>
          </div>


      <div id="tab-button">
        <div class="split"/>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </div>


</div>

</template>

<script>
  import allotApi from  '@/api/allot';

  export default {
        data: function () {
            return {
                /*时间选框*/
                timePicker: {
                    shortcuts: [{
                        text: '今天下午',
                        onClick(picker) {
                            const date = new Date();
                            date.setHours(14);
                            date.setMinutes(0);
                            date.setSeconds(0);
                            if (date > new Date()) {
                                picker.$emit('pick', date);
                            } else {
                                picker.$emit('pick', new Date());
                            }

                        }
                    }, {
                        text: '明天早上',
                        onClick(picker) {
                            const date = new Date();
                            date.setHours(9);
                            date.setDate(date.getDate() + 1);
                            date.setMinutes(0);
                            date.setSeconds(0);
                            date.setTime(date.getTime());
                            picker.$emit('pick', date);
                        }
                    }]
                },
                radio: 'allotTime',
                dateDefault: '',
                // 标签名
                title: "简历分配",
                //数字框每次增量
                val: 5,
                //初始量
                num: 5,
                /*下拉框中员工*/
                employees: [],
                employeeSelect: [],
            }
        },
        created() {
            console.log("creates方法执行")
        },
        computed: {
            data() {
                alert("computed方法执行")
            },

        },
        mounted: function () {
            console.log("mounted执行");
         allotApi.getAllUsers().then(response => {
            this.employees = response.data;
            console.log(response.data);
          })
        },
        methods: {
            //选项卡变化
            tabSwitch(index) {
              switch (index){
                case 0:
                  document.getElementById("allot_num").style.display = "none";
                  document.getElementById("scrollBlock").style.marginLeft="0em";
                  break;
                case 1:
                  document.getElementById("allot_num").style.display = "block";
                  document.getElementById("scrollBlock").style.marginLeft="6.5em";
                  break;
              }
                console.log('执行Tab');
            },

            showTimePicker() {
                document.getElementById("timePicker").style.display = "block";
            },
            hideTimePicker() {
                document.getElementById("timePicker").style.display = "none";
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {
                    });
            },
        },
        /*监控函数*/
        watch: {
            dateDefault: {
                deep: true,
                handler: function (newVal, oldVal) {
                    console.log('new:', newVal, 'old:', oldVal);
                },
            },
            }


    }


</script>

<style scoped>

    #resumeAllot {
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 5px;
      padding-left: 2em;
      padding-right: 2em;
    }
    .context{
      padding-top: 0.5em;
      padding-bottom: 0.5em;

    }
    .context span{
      padding-right: 0.8em;

    }



    #tab_title {
        height: 50px;
    }
    #tab_title > a {
      float: left;
      padding-right: 40px;
      height: 70px;
      line-height: 70px;
      font-size: 14px;
    }

    #tab_title > a:hover {
      color: #409eff;
    }

    #allot_num{
        display: none;
    }

    #timePicker {
        display: none;
    }


    a {
        color: #000;
        text-decoration: none;
    }



    /*分割线*/
    .split {
        margin-top: 1rem;
        margin-bottom: 1rem;
        background-color: #f0f0f0;
        height: 0.2rem;
    }

    #scrollBlock {
        width: 4rem;
        height: 0.2rem;
        background-color: #409eff;
    }


</style>
