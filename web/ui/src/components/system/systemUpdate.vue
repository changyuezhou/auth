<template>
    <div class="container">
        <v-dialog 
            :isShow="isShow"  
            @on-dialog-close="modalClose" 
            :hasHead="true">
            <header>编辑系统</header>
            <div class="formArea">
                <transition name="warnFade">
                    <p class="warnTip" v-show="isShowWarnTip">提示：{{warnText}}</p>
                </transition>

                <div class="line clearfix">
                    <label for="systemId" class="pull-left"><sup>* </sup>系统ID：</label>
                    <input 
                        class="inputCom readonly"
                        :style="{'width':'320px'}" 
                        readonly="readonly"
                        v-model="formValue.systemId.value">
                </div>

                <div class="line clearfix">
                    <label for="systemName" class="pull-left"><sup>* </sup>系统名称：</label>
                    <input
                        class="inputCom"
                        :style="{'width':'320px'}"
                        placeholder="请输入系统名称"
                        v-model="formValue.systemName.value"
                        @focus="utils.toggleInputActive($event)"  
                        @blur="utils.toggleInputActive($event)">     
                </div>

                <div class="line clearfix">
                    <label for="description" class="pull-left"><sup>* </sup>备注：</label>
                    <input  
                        class="inputCom" 
                        :style="{'width':'180px'}"
                        placeholder="请输入备注信息" 
                        v-model.number="formValue.description.value"
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
            },
            defaultData:{
                type:Object,
                default(){
                    return {
                        systemId:'',
                        systemName:'',
                        description:''
                    }
                }
            }
        },
        data(){
            return {
                canSubmit:true,
                timeOut:null,
                warnText:"",
                isShowWarnTip:false,
                formValue:{
                    systemId:{value:"",label:"系统Id"},
                    systemName:{value:"",label:"系统名称"},
                    description:{value:"",label:"备注"}
                }
            }
        },
        computed:{
            submitValue(){
                return  {
                    systemId:this.formValue.systemId.value,
                    systemName:this.formValue.systemName.value,
                    description:this.formValue.description.value
                }
            }
        },
        watch:{
            isShow(cur,old){
                if(cur){
                     for(let key in this.formValue){
                       this.formValue[key].value=this.defaultData[key]
                    }
                }else{
                    this.resetForm()
                }
            }
        },
        methods:{
            modalClose(){
                this.$emit("on-modal-close")    
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
            checkForm(){
                //判空
                for(let key in this.formValue){
                    if(!this.formValue[key].value && key != "description"){
                        let text = this.formValue[key].label+"不能为空"
                        this.showWarnTip(text)
                        return
                    }
                }
                //判断是否修改过
                let hasUpdated = false
                for(let key in this.submitValue){
                    if(this.defaultData[key]&&(this.submitValue[key]!=this.defaultData[key])){
                        hasUpdated = true
                        break;
                    }
                }
                if(!hasUpdated){
                    this.showWarnTip('没有任何修改')
                    return
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