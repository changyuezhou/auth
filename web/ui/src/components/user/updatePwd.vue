<template>
    <div class="conatiner">
        <head-guild :positions='["系统配置","修改密码"]'></head-guild>
        <div class="formArea">
            <transition name="warnFade">
                <p v-if="isWrong" class="warnTip">*{{warnText}}</p>
            </transition>
            <div class="line clearfix">
                <label  class="pull-left">登录账号：</label>
                <p class="defaultValue">{{account}}</p>
            </div>
            <div class="line clearfix">
                <label  class="pull-left"><sup>*</sup>请输入原始密码：</label>
                <input 
                    class="inputCom"
                    style="width:320px" 
                    type="password" 
                    placeholder="请输入原始密码" 
                    v-model="originPsw"
                    @focus="utils.toggleInputActive($event)"
                    @blur="utils.toggleInputActive($event)"/> 

            </div>
            <div class="line clearfix">
                <label  class="pull-left"><sup>*</sup>输入新密码：</label>
                <input 
                    class="inputCom" 
                    style="width:320px"
                    type="password" 
                    placeholder="输入新密码" 
                    v-model="newPsw"
                    @focus="utils.toggleInputActive($event)"
                    @blur="utils.toggleInputActive($event)"/>       

            </div>
            <div class="line clearfix">
                <label  class="pull-left"><sup>*</sup>请再次输入新密码：</label>
                <input  
                    class="inputCom"
                    style="width:320px"
                    type="password" 
                    placeholder="请再次输入新密码" 
                    v-model="confirmPsw"
                    @focus="utils.toggleInputActive($event)"
                    @blur="utils.toggleInputActive($event)"/>                      
            </div>
            <div class="btnLine clearfix">
                <v-button class="btn" type="primery" text="确认提交" @click="comFirm"></v-button>
                <v-button class="btn" text="取消" :isBigBtn="true" @click="reset"></v-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                id:"",
                userName:"",
                account: "",
                originPsw:"",
                newPsw:"",
                confirmPsw:"",
                isWrong:false,
                warnText:"",
                canSubmit:false,
                warnTime:null,
                patten : /^(\w){6,20}$/,
                canSubmit:true
            }
        },
        methods:{
            comFirm(){
                if(!this.canSubmit){
                    return
                }
                if(!this.checkForm()){
                    return 
                }
                this.canSubmit = false;
                let old_password = this.utils.md5(this.originPsw+""),
                    new_password = this.utils.md5(this.newPsw)
                this.apis.updatePassword(old_password,new_password)
                .then((res)=>{
                    this.$store.dispatch('showToast','修改成功')
                    this.reset()
                    this.canSubmit = true
                })
                .catch((errMsg)=>{
                    this.$store.commit('show_global_alert',errMsg)
                    this.canSubmit = true
                })
            },

            checkForm(){
                if(!this.originPsw||!this.newPsw||!this.confirmPsw){
                    this.warnTipAnime("请将信息填写完整")
                    return false
                }
                if(!this.patten.test(this.newPsw)){
                    this.warnTipAnime("密码格式为6-20位数字或字母")
                    return false
                }
                if(this.newPsw==this.originPsw){
                    this.warnTipAnime("新密码不能和旧密码一致")
                    return false
                }
                if(this.newPsw!=this.confirmPsw){
                    this.warnTipAnime("两次密码输入不一致")
                    return false
                }
                return true
            },

            warnTipAnime(text){
                if (this.isWrong) {
                    clearTimeout(this.warnTime)
                    this.warnText = text
                    this.warnTime = setTimeout(() => {
                        this.warnText = ""
                        this.isWrong = false
                    }, 3000)
                } else {
                    this.warnText = text
                    this.isWrong = true
                    this.warnTime = setTimeout(() => {
                        this.warnText = ""
                        this.isWrong = false
                    }, 3000)
                }  
            },

            reset(){
                this.originPsw=""
                this.newPsw=""
                this.confirmPsw=""
            }
        },
        beforeMount(){
        this.account = this.utils.getCookie('user_name')||"用户"
      }
        
    }

</script>
<style scoped lang="less">
    .formArea{
        padding-top: 96px;
        position:relative;
        .warnTip{
            position:absolute;
            left:0;
            top:50px;
            text-align:center;
            height: 20px;
            width:100%;
            font-size:14px;
            color:#ec0606;
        }
        .line{
            margin-bottom: 13px;
            label{
                width: 40%;
                text-align: right;
                font-size: 16px;
                height: 40px;
                line-height: 40px;
                margin-right: 20px;
                sup{
                    color:#f42626;
                }
            }
            .defaultValue{
                display: inline-block;
                width:320px;
                height: 40px;
                line-height: 40px;
                border: 1px solid #D2D2D2;
                background-color: #EEEEEE;
                padding-left:24px;
                color:#888888;
                font-size:14px;
            }
        }
        .btnLine{
            padding-left:42%;
            margin-top: 50px;
            .btn{
                float: left;
            }
            .btn:last-child{
                margin-left: 58px;
            }
        }
    }
    .warnFade-enter-active,.warnFade-leave-active{
        transition:all .3s linear;
    }
    .warnFade-enter,.warnFade-leave-active{
        opacity: 0;
    }
    
</style>