<template>
    <div class="login-register">
        <div class="ms-register">
            <div class="ms-title">注册</div>
            <!--model顾名思义是我们双向绑定的数据，而ref=”user1”,我们可以把form表单看成是一个类，
            那么这个类的对象名字就是我们用ref指定的名字，这个名字会在js中调用elementUI的原生态方法中用到，
            而relus则是我们自定义校验的规则，定义在js的data里。 注意2：若要使用校验则必须在每个中设置prop
            属性，该属性的名字必须的和relus中定义的校验规则中对应的属性名字一样-->
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content" >
                <el-form-item prop="email">
                    <el-input v-model="ruleForm.email" placeholder="邮箱" name="email" type="text">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="用户名" name="username" type="text">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <!-- @keyup.enter 监听键盘回车时间 element主键后要加native-->
                    <el-input type="password" name="password" placeholder="密码" v-model="ruleForm.password" @keyup.enter.native="submitForm('ruleForm')">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="passwordAgain">
                    <el-input type="password" name="passwordAgain" placeholder="确认密码" v-model="ruleForm.passwordAgain" @keyup.enter.native="submitForm('ruleForm')">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-input type="text" placeholder="验证码" v-model="ruleForm.code" @keyup.enter.native="submitForm('ruleForm')">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
                </div>
              <div class="login-btn">
                <el-button type="primary" @click="toLogin()">返回登陆</el-button>
              </div>
                <!--<p class="login-tips">Tips : 用户名和密码随便填。</p>-->
            </el-form>
        </div>
    </div>
</template>

<script>
  import registerOption from '@/api/register'
  export default {
        data(){
            var num = Math.floor((Math.random() * 9 + 1) * 100000);
            var validateEmail = (rule, value, callback) => {
                if (!(/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(this.ruleForm.email))) {
                    callback(new Error('邮箱格式不正确'));
                } else {
                  registerOption.emailAction(this.ruleForm.email,num).then(res => {
                   if( res.data.data==false){
                   alert('邮箱已存在');
                   }
                  })
                }
                callback();
            };
            var validateName = (rule, value, callback) => {
                if (value.length<3) {
                    callback(new Error('用户名不能少于3个字符'));
                } else {
                  registerOption.nameAction(this.ruleForm.username).then(res =>{
                    if(res.data.data==false){
                      alert('用户已存在');
                    }
                  })
                }
                callback();
            };
            var validatePass = (rule, value, callback) => {
                if (value.length<6) {
                    callback(new Error('密码不能少于6个字符'));
                } else {
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value.length<6) {
                    callback(new Error('密码不能少于6个字符'));
                } else if (value != this.ruleForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            var validateCode = (rule, value, callback) => {
                if (value=="") {
                    callback(new Error('请输入验证码'));
                } else {
                    if(value!=num){
                        callback(new Error('验证码错误'));
                    }
                    callback();
                }
            };
            return {
                ruleForm: {
                    email:'',
                    username: '',
                    password: '',
                    passwordAgain:'',
                    code:'',
                },
                rules: {
                    email: [
                        { required: true,//是否必填
                            validator: validateEmail,//错误提示信息
                            trigger: 'blur'//检验方式（blur为鼠标点击其他地方，）
                        },
                     //    {
                     //        type: 'email',//要检验的类型（number，email，date等）
                     //        message: '请输入正确的邮箱',
                     //        trigger: ['blur', 'change']//（change为检验的字符变化的时候）
                     // }
                    ],
                    username: [
                        { required: true, validator: validateName, trigger: 'blur' }
                    ],

                    password: [
                        { required: true, validator: validatePass, trigger: 'blur'}
                    ],
                    passwordAgain: [
                        { required: true, validator: validatePass2, trigger: 'blur'}
                    ],
                    code: [
                        { required: true,  validator: validateCode, trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
          toLogin(){
            this.$router.push({path: '/login'});
          },
            submitForm(formName) {

                this.$refs[formName].validate((valid) => {
                    if (valid) {
                      registerOption.registerAction(this.ruleForm.email,this.ruleForm.username,this.ruleForm.password).then(res =>{
                        if(res.data.data==true){
                          this.$router.push({path: '/login'});
                        }else{
                          console.log('注册失败!!');
                          return false;
                        }
                      })
                    }

                });
            }
        }
    }
</script>

<style scoped>
    .login-register{
        position: fixed;
        width:100%;
        height:100%;
        background-size: 100%;
        background-color: #2d3a4b;
    }
    .ms-title{
        width:100%;
        line-height: 50px;
        text-align: center;
        font-size:20px;
        color: #fff;
        border-bottom: 1px solid #ddd;
        background-color: #ddd;
    }
    .ms-register{
        position: absolute;
        left:50%;
        /*top:50%;*/
        width:350px;
        margin:50px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255,255,255, 0.3);
        overflow: hidden;
    }
    .ms-content{
        padding: 30px 30px;
    }
    .login-btn{
        text-align: center;
    }
    .login-btn button{
        width:100%;
        height:36px;
        margin-bottom: 10px;
    }
</style>
