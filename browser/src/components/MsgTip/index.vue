<template xmlns:th="http://www.thymeleaf.org">
  <div id="msgtip">
    <el-button :plain="true" @click="send">发送</el-button>
  </div>
</template>

<script>
  import msgApi from '@/api/message'

  export default {
    data: function () {
      return {
        ws: new WebSocket("ws://" + location.hostname + "/websocket/" + this.$store.getters.name)
      }
    },
    methods: {
      // 发送消息
      send(msgArr) {
        // let msgArr = [
        //   {
        //     "id": 2,
        //     "msgTo": "guanyu",
        //     "title": "新标题标题2",
        //     "text": "内容内容2",
        //     "status": 0,
        //     "kind": "request"
        //   },
        //   {
        //     "id": 3,
        //     "msgTo": "admin",
        //     "title": "新标题标题3",
        //     "text": "内容内容3",
        //     "status": 0,
        //     "kind": "request"
        //   },
        // ];
        let msgdata = {
          msglist : msgArr,
          uname   : this.$store.getters.name
        }

        if (msgArr.length > 0) {
          msgApi.passMsg(msgdata).then(res => {
            if (res.data.succ === true) {
              for (let ind = 0; ind < msgArr.length; ind++) {
                let msg = msgArr[ind];
                console.log(JSON.stringify(msg));
                this.ws.send(JSON.stringify(msg));
              }
            }
          })
        }

      },

      // 接收请求消息
      requesttip(title, msgFrom) {

        // 提示
        var _this = this;
        const notify = this.$notify.info({
          title: '新消息',
          dangerouslyUseHTMLString: true,
          message: '<strong>你有一条新消息，快去看看</strong><br>' + title + '...',
          position: 'bottom-right',
          showClose: false,
          duration: 0,
          onClick: function () {
            // 响应
            var data = {
              "id": 2,
              "status": 0,
              "msgTo": msgFrom,
              "title": "反馈",
              "text": "收到消息",
              "kind": "response",
            };
            _this.ws.send(JSON.stringify(data))
            notify.close();
          }
        })

        // this.soundPlay();
        // Tab.reload();

      },
      //接收响应消息
      responsetip(msgTo) {
        this.$notify({
          title: '已接收',
          dangerouslyUseHTMLString: true,
          message: '<strong>员工' + msgTo + '已接收到消息</strong><br>',
          position: 'bottom-right',
          duration: 0,
          type: 'success'
        })
        // this.soundPlay();
      },


    },
    beforeCreate(){
    },

    mounted() {
      var _this = this;
      //连接发生错误的回调方法  

      this.ws.onerror = function () {
        console.log("WebSocket连接发生错误");
      };

      //连接成功建立的回调方法  
      this.ws.onopen = function () {
        console.log("WebSocket连接成功");
      }
      // //接收到消息的回调方法  
      this.ws.onmessage = function (event) {
        console.log("yes")
        let info = event.data.split("~~");
        // 判断是请求还是响应
        console.log("event", event)
        console.log("info",info)
        let jsondata = JSON.parse(info[0]);
        console.log("jsondata", jsondata)
        if (jsondata.kind === "request") {
            if (event.data) {
              _this.requesttip(jsondata.title, info[1]);
            }
        }
        else if (jsondata.kind === "response") {
            if (event.data) {
              $sendMsgV.responsetip(info[1]);
            }
          }
        console.log(event.data)
      }
      //连接关闭的回调方法  
      this.ws.onclose = function () {
        console.log("WebSocket连接关闭");
      }
      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。  
      this.ws.onbeforeunload = function () {
        //closeWebSocket();  
      }


    },
    beforeDestroy: function () {
    }
  }





</script>


