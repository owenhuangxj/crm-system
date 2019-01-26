<template>
  <div>



  <span  @click="allotDialog=true"><i  class="el-icon-message"  >简历分配</i> </span>

  <div id="resumeAllot">
  <el-dialog  title="简历分配"
      :visible.sync="allotDialog"
    :before-close="handleClose"
    :close-on-click-modal=false
  >

            <div id="tab_title" >
              <a @click="tabSwitch(1)">随机分配</a> <a  id="fixed" @click="tabSwitch(0)">固定分配</a>
            </div>
            <!--横线--> <!--滑动条-->
            <div id="tabScroll" class="split"><div  id="scrollBlock"></div></div>
            <!--人员选择-->
            <div id="tab-emp" class="context">
                <span>人&emsp;&ensp;员:</span>
                <el-select  id="empSelector" class="allot_input"  v-model="employeeSelect"  :multiple=true  placeholder="请选择">
              <el-option
                v-for="item in employees"
                :key="item.uid"
                :label="item.nick"
                :value="item.uid">
              </el-option>
            </el-select>
                </div>
                  <div id="allot_num" class="context">
                    <span>分配数量:</span>
                    <el-input-number  class="allot_input"  v-model="num" :step="3" :min="1" :max="100" label="描述文字"></el-input-number>
                  </div>

             <div id="allot_time" class="context">
            <span>分配时间：</span>
            <el-radio v-model="radio" label="1"   @change="setAllotTime('now')">立刻发送</el-radio>
            <el-radio v-model="radio" label="2"   @change="setAllotTime('fixed')">选择时间</el-radio>
            <br/>
            <div id="timePicker" class="context">
                 <el-date-picker  class="allot_input" v-model="allotTime" type="datetime" placeholder="选择任务时间" align="right" :picker-options="timePicker"></el-date-picker>
            </div>
          </div>

      <div id="tab-button">
        <div class="split"/>
        <el-button @click="allotDialog = false">取 消</el-button>
        <el-button type="primary" @click="allot()">确 定</el-button>
      </div>

  </el-dialog>
  </div>
  </div>
</template>

<script>
  import allotApi from  '@/api/allot';

  export default {

    props:["selectedId"],

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
                // 标签名
                title: "简历分配",
                //数字框每次增量
                val: 5,
                //初始量
                num: 5,
                /*下拉框中员工*/
                employees: [],
                employeeSelect: [],
                allotDialog:false,
                allotTime:'' ,
                index:'',
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
          allotApi.getAllUsers().then(response => {
            this.employees = response.data.user;
            console.log(this.employees)
          })
        },
        methods: {
            //选项卡变化
            tabSwitch(index) {
            /*0 是固定分配 ,1是随机分配*/
              console.log(this.selectedId);
              switch (index){
                case 0:
                  if(0==this.selectedId.length){
                    this.$alert('请先选择要分配的简历', '警告', {
                      confirmButtonText: '确定',
                    });
                    break;
                  }else{
                    document.getElementById("allot_num").style.display = "none";
                    document.getElementById("scrollBlock").style.marginLeft="6.5em";
                    this.index=0;
                  }
                  break;
                case 1:
                  document.getElementById("allot_num").style.display = "block";
                  document.getElementById("scrollBlock").style.marginLeft="0em";
                  this.index=1;
                  this.num=this.selectedId.length;
                  break;
              }
                console.log('执行Tab');
            },


            setAllotTime(way) {
              switch (way){
                case 'now':
                  this.allotTime= -1;
                  document.getElementById("timePicker").style.display = "none";
                  break;
                case 'fixed':
                  document.getElementById("timePicker").style.display = "block";
                  break;
              }
            },

            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {
                    });
            },
          allot(){
            let params=new URLSearchParams();

            switch (this.index) {
              case 0:
                params.append("key",this.employeeSelect[0]);
                params.append("value",this.selectedId);
                params.append("way",0);
                params.append("allotTime",this.allotTime);
                break;
              case 1:
              break;
            }
            console.log(params.get("key"))

            allotApi.allotResume(params).then(response => {
              this.employees = response.data;
            })
          }
        },
        /*监控函数*/
        watch: {
            allotTime: {
                deep: true,
                handler: function (newVal, oldVal) {
                    console.log('allotTime:','new:', newVal, 'old:', oldVal);
                },
            },
          employeeSelect:{
            deep: true,
            handler: function (newVal, oldVal) {
              console.log('employeeSelect:','new:', newVal, 'old:', oldVal);
            },
          },
          index:{
            deep: true,
            handler: function (newVal, oldVal) {

              console.log("index:",'new:', newVal, 'old:', oldVal);
            },

          }
            }


    }


</script>

<style scoped>

    #resumeAllot {
        background: #fff;
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
        display: block;
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
