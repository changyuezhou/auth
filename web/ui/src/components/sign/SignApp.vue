<template>
    <div id="sign">
                <div class="header clearfix" v-show="isShow">
                    <img :src="logoImg" class="pull-left">
                    <h3 class="pull-left">云协同管理系统</h3>
                </div>
                <div class="mainBox clearfix" v-show="isShow">
                    <div class="leftImg pull-left" style="background-image:url('../../assets/images/loginBanner.png')"></div>
                    <div class="rightLogBox pull-left">
                        <div class="formBox">
                            <p class="text">账号登录</p>
                            <div class="loginForm">
                                <transition name="warnFade">
                                    <p v-if="isWrong" class="warnTip">{{warnText}}</p>
                                </transition>
                                <form action="/login" method="post">
                                    <input type="text" placeholder="请输入账号" autocomplete="off" v-model="userName" name="username" @focus="onFocus" @blur="onBlur">
                                    <input type="password" class="lastInput" placeholder="请输入密码" autocomplete="off" v-model="password" name="password" @focus="onFocus" @blur="onBlur">
                                    <label class="isAutoLog" :class="{'checked':isRmbUser}" @click="toggleIsAutoLog">
                                <span>
                                    <i></i>
                                </span>
                                    自动登录
                            </label>
                                    <input type="button" class="loginBtn" @click="formSubmit" value="登录">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>                
                <loading :isShow="showLoading"></loading>
    </div>
</template>

<script>
import loading from '../basic/loading.vue'
export default {
    components:{
        loading
    },
    data() {
        return {
            warnTime: null,
            isShow: false,
            patten: /[\u4e00-\u9fa5]/,
            logoImg: require('../../assets/images/logo.png'),
            userName: "",
            password: "",
            platformId:"",
            authToken:"",
            redirectBack:"",
            isRmbUser: false,
            isWrong: false,
            warnText: "",
            showLoading:false
        }
    },
    computed:{
        postUrl(){
            return location.origin+'/api/auth/sign_in?redirectBack='+this.redirectBack
        }
    },
    methods: {
        encodePsw(psw,level=1){
            if(level === 1){
                return this.utils.md5(psw)
            }else if(level === 2){
                return this.utils.md5(psw+this.authToken)
            }else if(level === 3){
                return this.utils.md5(this.utils.md5(psw)+this.authToken)
            }
                  
        },
        onFocus(e) {
            e.target.classList.add("active")
        },

        onBlur(e) {
            e.target.classList.remove("active")
        },

        toggleIsAutoLog() {
            this.isRmbUser = !this.isRmbUser
        },

        formSubmit(e) {
            if (!this.checkForm()) {
                e.preventDefault()
                return
            }
            let fetchPsw = "",cookiePsw = ""
            if(this.password.startsWith('encoded$')){
                cookiePsw = this.password
                fetchPsw = this.encodePsw(this.password.substr(8,this.password.length-8),2)
            }else{
                cookiePsw = encodeURIComponent('encoded$'+this.encodePsw(this.password))
                fetchPsw = this.encodePsw(this.password,3)
            }
            if (this.isRmbUser) {
                this.utils.setCookie("rmbUser", "true", 7)
                this.utils.setCookie("userName", this.userName, 7)
                this.utils.setCookie("password", cookiePsw, 7)
            } else {
                this.utils.setCookie("rmbUser", "false", (-1))
                this.utils.setCookie("userName", this.userName, (-1))
                this.utils.setCookie("password", cookiePsw, (-1))
            }
            this.showLoading = true        
            this.axios.post(this.postUrl,{
                "userName":this.userName,
                "platformId":this.platformId,
                "password":fetchPsw,
                "authToken":this.authToken
            }).then((res)=>{
                this.showLoading = false
                if(res.data.code==302){
                    location.href = res.data.msg
                }else{
                    this.warnTipAnime(res.data.msg)
                }
            }).catch((err)=>{
                this.showLoading = false
                if(typeof(err)=='string'){
                    this.warnTipAnime(err)
                }else{
                    console.log(err)
                    this.warnTipAnime("")
                }
            }) 
        },

        //页面载入时判断是否自动登录
        isAutoLogin() {
            if (this.utils.getCookie("rmbUser") == "true") {
                this.isRmbUser = true;
                this.userName = this.utils.getCookie("userName")
                this.password =  decodeURIComponent(this.utils.getCookie("password")) 
            }
        },

        checkForm() {
            if (!this.userName || !this.password) {
                this.warnTipAnime("用户名和密码不能为空")
                return false
            }
            if (this.patten.test(this.userName) || this.patten.test(this.password)) {
                this.warnTipAnime("用户名和密码不能为中文字符")
                return false
            }
            return true
        },

        warnTipAnime(text) {
            if (this.isWrong) {
                clearTimeout(this.warnTime)
                this.warnText = text
                this.warnTime = setTimeout(() => {
                    this.warnText = ""
                    this.isWrong = false
                }, 2000)
            } else {
                this.warnText = text
                this.isWrong = true
                this.warnTime = setTimeout(() => {
                    this.warnText = ""
                    this.isWrong = false
                }, 2000)
            }
        }
    },
    created() {
        this.isAutoLogin()
        this['platformId']=this.utils.getUrlParam('platformId')
        this['authToken']=this.utils.getUrlParam('authToken')
        this.redirectBack =this.utils.getUrlParam('redirectBack',false)
        this.isShow=this.utils.getUrlParam('isShow',false)
    }
}
</script>

<style scoped lang='less'>
    #sign{
        .header{
            padding-top: 80px; 
            padding-left: 40px;
            height:90px;
            box-sizing: content-box;
            img{
                height:90px;
            }
            h3{
                font-size: 36px;
                color:#1773ca;
                height: 100%;
                line-height: 120px;
                margin-left: 40px;
            }
        }
        .mainBox{
            width: 100%;
            min-width: 1360px;
            height:500px;
            background-color: #1773ca;
            //padding-left:1120px;
            position: relative;
            .leftImg{
                //width: 1120px;
                //position:absolute;
                //left: 0;
                //top:0;
                width: 58%;
                height: 100%;
                background-repeat: no-repeat;
            }
            .rightLogBox{
                height:100%;
                padding-left:7%;
                padding-top:40px;
                .formBox{
                    width: 455px;
                    height: 420px;
                    padding-top:50px;
                    border: 2px solid #309bff;
                    border-radius: 4px;
                    background-color: #fff;
                    >.text{
                        font-size:30px;
                        color:#309bff;
                        text-align: center;
                    }
                    .loginForm{
                        width:100%;
                        padding-left:70px;
                        margin-top:75px;
                        position:relative;
                        .warnTip{
                            position:absolute;
                            left:94px;
                            top:-20px;
                            height: 20px;
                            font-size:14px;
                            color:#ec0606;
                        }
                        input{
                            width: 320px;
                            height: 40px;
                            border: 1px solid #b5b5b5;
                            border-radius:2px;
                            padding-left:24px;
                            transition: all 0.30s ease-in-out;
                            -webkit-transition: all 0.30s ease-in-out;
                            -moz-transition: all 0.30s ease-in-out;
                        }
                        .lastInput{
                            margin-top: 30px;
                        }
                        input.active{
                            border: 1px solid #1773ca;
                            box-shadow: 0 0 5px rgba(23, 115, 202, 1);
                            -webkit-box-shadow: 0 0 5px rgba(23, 115, 202, 1);
                            -moz-box-shadow: 0 0 5px rgba(23, 115, 202, 1);
                        }
                        .isAutoLog{
                            display:block;
                            font-size:14px;
                            height: 20px;
                            margin-top:13px;
                            color:#888888;
                            line-height: 20px;
                            cursor:pointer;
                            >span{
                                display:inline-block;
                                width: 20px;
                                height: 20px;
                                border: 1px solid #b3c3c9;
                                border-radius:2px;
                                vertical-align:top;
                                position:relative;
                                >i{
                                    position:absolute;
                                    width:14px;
                                    height:9px;
                                    top:50%;
                                    left:50%;
                                    transform:translate(-50%,-50%);
                                    background: url('../../assets/images/onCheck.png') no-repeat center center;
                                }
                            }
                        }
                        .isAutoLog.checked{
                            >span{
                                border: none;
                                background-color: #309bff;
                            }
                        }
                        .loginBtn{
                            width: 320px;
                            height: 46px;
                            background-color: #309bff;
                            border-radius:3px;
                            color:#fff;
                            text-align:center;
                            line-height: 46px;
                            font-size:16px;
                            cursor:pointer;
                            margin-top:34px;
                            padding-left: 0;
                        }
                        .loginBtn:hover{
                            background-color: #7abefe;
                        }
                    }
                }
            }
        }   
    }
</style>

