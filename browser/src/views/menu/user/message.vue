<template>
  <div class="login-container">
    <h1>{{pageName}}</h1>
    <!-- Admin can see this -->
    <!--<el-tag v-permission="['root']">root</el-tag>-->

    <!-- Editor can see this -->
    <!--<el-tag v-permission="['manager']">manager</el-tag>-->

    <!-- Editor can see this -->
    <!--<el-tag v-permission="['root','manager']">Both root or manager can see this</el-tag>-->
    <div id="msglist">
      <!--上部标题标签-->
      <div id="msgTab" ref="msgTab">
        <a href="javascript:;"
           @click="Tab(index)"
           v-for="(item,index) in items"
           :class="{activeTab : index===curId}"
        >{{item.item}}</a>
        <!--<span class="count">({{item.msgCount}})</span>-->
      </div>
      <!--横线-->
      <div id="tabScroll">
        <!--滑动条-->
        <div ref="smallScroll" class="smallScroll"></div>
      </div>
      <!--中间消息列表-->
      <div class="tab-con">
        <div v-for="(content, index) in contents">
          <div class="msgItem" v-for="item in content" v-show="item.status===curId">
            <div class="msgTitle">{{item.title}}</div>
            <div class="itemUtil">
              <a href="javascript:;" class="itemBtn" v-for="(itemBtn,index) in itemBtns" v-show="index===curId"
                 @click="changeStatus(item,itemBtn.func)">{{itemBtn.btntext}}</a>
            </div>
            <div class="msgTime">{{item.timeStap}}</div>

          </div>
        </div>
      </div>
      <!--下部工具按钮-->
      <div class="bottom-util">
        <a href="javascript:;" v-show="index===curId"
           v-for="(utilItem, index) in utilBtns"
           class="utilBtn"
           @click="changeAllStatus(utilItem.func)"
        >{{utilItem.btntext}}</a>

        <a href="javascript:;" class="utilBtn" id="refreshbtn" @click="reload()">刷新消息</a>
      </div>

    </div>

    <msgtip></msgtip>
  </div>

</template>

<script>
  // 当然你也可以为了方便使用，将它注册到全局
  // import permission from '@/directive/permission/index' // 权限判断指令
  import msgApi from '@/api/message'
  import msgtip from '@/components/MsgTip'

  export default {
    // directives: { permission },
    name: 'Menu1',
    data() {
      return {
        pageName: '我的消息',
        curId: 0,
        // 标签名
        items: [
          {item: '未读消息', msgCount: 0},
          {item: '已读消息', msgCount: 0},
          {item: '回收站', msgCount: 0},
        ],


        // 下方工具按钮
        utilBtns: [
          {btntext: '全部标为已读', color: '#409eff', func: 'readAll'},
          {btntext: '删除全部', color: '#f56c6c', func: 'delAll'},
          {btntext: '清空回收站', color: '#f56c6c', func: 'clearAll'}
        ],

        // 每条消息后的工具按钮
        itemBtns: [
          {btntext: '标为已读', color: '#fff', func: "read"},
          {btntext: '删除', color: '#f56c6c', func: "delete"},
          {btntext: '还原', color: '#f56c6c', func: "reduct"}
        ],
      }
    },
    components:{
      msgtip
    },
    methods: {
      Tab(index) {

        this.curId = index;
        document.getElementsByClassName("utilBtn")[index].style.background = this.utilBtns[index].color;
        //等vue将active类赋值完成后再执行
        setTimeout(this.tabScrollMove, 10);
        // setTimeout(initMsgList, 11, this, index);
        initMsgList(this, index);
      },
      changeStatus(item, func) {
        console.log(item);
        console.log(item.id);
        console.log(func);
        let data = {
          msgId: item.id,
          oldStatus: item.status,
          uname: this.$store.getters.name
        }
        msgApi.changeStatus(data).then(res => {
          setTimeout(loadMsgList, 1000, this)
        })
      },
      changeAllStatus(func) {
        console.log(func);
        let data = {
          func: func,
          uname: this.$store.getters.name
        };

        msgApi.changeAllStatus(data).then(res=>{
          // 1000毫秒后加载消息列表
            setTimeout(loadMsgList, 1000, this);
        })
      },
      reload() {
        loadMsgList(this);
      },
      // 滑动条
      tabScrollMove() {
        this.$refs.smallScroll.style.left = document.getElementsByClassName("activeTab")[0].offsetLeft + 'px';
      }


    },

    beforeCreate: function () {
      // 中间表内容
      this.contents = [[], [], []];
      // axios中this是window对象。所以要将现在的this赋值给中间变量_this
      console.log('before')

    },
    created: function () {
    },
    mounted: function () {
      initMsgList(this, 0);
      setTimeout(loadMsgList, 1000, this);

    },
    beforeUpdate: function () {
    },
    updated: function () {
    }

  }

  // 初始化消息列表数据
  function initMsgList(obj, state) {
    console.log('请求接口');
    console.log("init中的obj");
    const data = {
      status: state,
      uname: obj.$store.getters.name
    }
    msgApi.initMsgList(data).then(res => {
      obj.contents[state] = [];
      console.log(obj.contents);
      console.log(res.data)
      res.data.data.forEach(function (v) {
        // 将消息填入消息列表
        obj.contents[state].unshift(v);
        //
        //     });
      })
    })
  }

  // 刷新后加载消息列表
  function loadMsgList(obj) {
    console.log("load中的obj", obj);
    obj.Tab(1);
    obj.Tab(2);
    obj.Tab(0)
  }

</script>

<style scoped>

  #msglist {
    padding: 30px;
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
    height: 550px;
  }

  #msgTab {
    height: 50px;

  }

  a {
    color: #000;
    text-decoration: none;
  }

  #msgTab > a {
    float: left;
    /*padding-right: 40px;*/
    height: 70px;
    line-height: 70px;
    font-size: 14px;
    text-align: left;
    display: inline-block;
    width: 100px;
  }

  #msgTab > a:hover {
    color: #409eff;
  }

  #tabScroll {
    /*position: relative;*/
    /*padding:0 30px;*/
    background-color: #f0f0f0;
    height: 2px;
  }

  .smallScroll {
    /*左右位置过渡*/
    transition: left .3s;
    position: absolute;
    width: 60px;
    height: 2px;
    background-color: #409eff;
  }

  #msgTab a.activeTab {
    color: #409eff;
  }

  .tab-con {
    margin-top: 20px;
    overflow: auto;
    height: 350px;
  }

  .utilBtn {
    display: inline-block;
    color: #fff;
    padding: 9px 15px;
    margin-top: 30px;
    background: #409eff;
    font-size: 12px;
    border-radius: 5px;
  }

  .msgItem {
    box-sizing: border-box;
    border-bottom: 1px solid #eee;
    height: 50px;
  }

  .msgItem:hover {
    background-color: #eee;
  }

  .msgItem > div {
    float: right;
  }

  .msgItem .msgTitle {
    float: left;
    color: #409eff;
    font-size: 12px;
    height: 50px;
    line-height: 50px;
  }

  .msgItem .itemUtil {
    border: 1px solid #ddd;
    font-size: 12px;
    margin-top: 7px;
    margin-left: 60px;
    border-radius: 5px;
  }

  .itemBtn {
    display: inline-block;
    color: #fff;
    text-decoration: none;
    padding: 9px 15px;
    background: #409eff;
    font-size: 12px;
    border-radius: 5px;
  }

  .msgTime {
    font-size: 12px;
    height: 50px;
    line-height: 50px;
  }

</style>

