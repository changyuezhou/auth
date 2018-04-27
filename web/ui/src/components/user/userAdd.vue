<template>
    <div class="container">
        <v-dialog 
            :isShow="isShow"  
            @on-dialog-close="modalClose" 
            :hasHead="true">
            <header>添加用户</header>
            <div class="formArea">
                <transition name="warnFade">
                    <p class="warnTip" v-show="isShowWarnTip">提示：{{warnText}}</p>
                </transition>

                <div class="line clearfix">
                    <label for="userName" class="pull-left"><sup>* </sup>用户名称：</label>
                    <input 
                        class="inputCom"
                        :style="{'width':'320px'}" 
                        placeholder="请输入用户名称"
                        v-model="formValue.userName.value"
                        @focus="utils.toggleInputActive($event)"  
                        @blur="checkUserName($event)">
                    <p>账号的初始密码为：111111</p>
                    <span v-show="isShowUserNameCheck" style="color:red">该用户名已被使用</span>
                </div>             

                <div class="line clearfix">
                    <label for="contactName" class="pull-left"><sup>* </sup>联系人：</label>
                    <input 
                        class="inputCom"
                        :style="{'width':'320px'}" 
                        placeholder="请输入联系人"
                        v-model="formValue.contactName.value"
                        @focus="utils.toggleInputActive($event)"  
                        @blur="utils.toggleInputActive($event)">
                </div>

                <div class="line clearfix">
                    <label for="mobileNumber" class="pull-left"><sup>* </sup>联系电话：</label>
                    <input 
                        class="inputCom"
                        :style="{'width':'320px'}" 
                        placeholder="请输入联系电话"
                        v-model="formValue.mobileNumber.value"
                        @focus="utils.toggleInputActive($event)"  
                        @blur="utils.toggleInputActive($event)">
                </div>                  

                <div class="line clearfix">
                    <label for="description" class="pull-left"><sup></sup>备注：</label>
                    <input 
                        class="inputCom"
                        :style="{'width':'320px'}" 
                        placeholder="请输入备注信息" 
                        v-model="formValue.description.value"
                        @focus="utils.toggleInputActive($event)"  
                        @blur="utils.toggleInputActive($event)">     
                </div>
            </div>
            <div class="btnBox clearfix">
                <v-button class="btn" type="primery" text="确认提交" @click.stop="checkForm"></v-button>
                <v-button class="btn" text="取消" :isBigBtn="true" @click.stop="modalClose"></v-button>
            </div>    
        </v-dialog>
    </div>
</template>

<script>
    export default {
        props:{
            isShow:{
                type:Boolean,
                default:false
            }
        },
        data(){
            return {
                canSubmit:true,
                timeOut:null,
                warnText:"",
                isShowUserNameCheck:false,                
                isShowWarnTip:false,
                formValue:{
                    userName:{value:"",label:"用户名称"},
                    contactName:{value:"",label:"联系人"},
                    mobileNumber:{value:"",label:"联系电话"},
                    description:{value:"",label:"备注信息"}
                }
            }
        },
        computed:{
            submitValue(){
                return  {
                    userName:this.formValue.userName.value,
                    contactName:this.formValue.contactName.value,
                    mobileNumber:this.formValue.mobileNumber.value,
                    description:this.formValue.description.value,
                    password: this.utils.md5("111111")
                }
            }
        },
        watch:{
            isShow(cur,old){
                if(!cur){
                    this.resetForm()
                }
            }
        },
        methods:{
            modalClose(){
                this.$emit("on-modal-close")
                this.isShowUserNameCheck = false
            },
            showWarnTip(text){
                if(this.isShowWarnTip){
                    this.warnText = text
                    clearTimeout(this.timeOut)
                    this.timeOut=setTimeout(()=>{this.isShowWarnTip=false},5000)
                }else{
                    this.warnText = text
                    this.isShowWarnTip = true
                    this.timeOut=setTimeout(()=>{this.isShowWarnTip=false},5000)
                }   
            },
            checkUserName(e){
                e.target.classList.toggle("active")
                if(!this.formValue.userName.value){return}
                this.apis.getUserByName(this.formValue.userName.value)
                .then((data)=>{
                    if(Object.keys(data.data).length>0){
                        this.isShowUserNameCheck = true
                    }else{
                        this.isShowUserNameCheck = false
                    }
                })
                .catch((err)=>{
                    console.log(err)
                })
            },            
            checkForm(){
                //判空
                for(let key in this.formValue){
                    if(!this.formValue[key].value && key != "description"){
                        let text = this.formValue[key].label+"不能为空"
                        this.showWarnTip(text)
                        return
                    }
                }
                //验证通过后的操作
                this.$emit("dialogSubmited",this.submitValue)
            },
            resetForm(){
                for(let key in this.formValue){
                    this.formValue[key].value=""
                }
            }
        }
    }
</script>

<style scoped lang="less">
    .container{
        header{
            height: 42px;
            width: 642px;
            background-color: #1773ca;
            text-align:center;
            color:#fff;
            font-size:16px;
            line-height:42px;
        }
        .formArea{
            padding-top:55px;
            position:relative;
            .warnTip{
                position:absolute;
                top:0;
                left:0;
                height: 26px;
                width:100%;
                line-height: 26px;
                text-align:center;
                color:#ec0606;
                font-size:14px;
                background-color: #fff799;
            }
            .line{
                margin-bottom: 15px;
                position:relative;
                label{
                    width: 31%;
                    text-align: right;
                    font-size: 16px;
                    height: 40px;
                    line-height: 40px;
                    margin-right: 20px;
                    sup{
                        color:#f42626;
                        vertical-align: top;
                    }
                }
            }
        }
        .btnBox{
            padding-left:180px;
            margin-top:30px;
            margin-bottom:35px;
            .btn{
                float: left;
            }
            .btn:last-child{
                margin-left:35px;
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