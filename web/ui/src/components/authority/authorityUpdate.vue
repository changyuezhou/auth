<template>
    <div class="container">
        <v-dialog 
            :isShow="isShow"  
            @on-dialog-close="modalClose" 
            :hasHead="true">
            <header>编辑权限</header>
            <div class="formArea">
                <transition name="warnFade">
                    <p class="warnTip" v-show="isShowWarnTip">提示：{{warnText}}</p>
                </transition>
                <div class="line clearfix">
                    <label for="authId" class="pull-left"><sup>* </sup>权限ID：</label>
                    <input 
                        class="inputCom readonly"
                        :style="{'width':'320px'}" 
                        readonly="readonly"
                        v-model="formValue.authId.value">
                </div>

                <div class="line clearfix">
                    <label for="authName" class="pull-left"><sup>* </sup>权限名称：</label>
                    <input
                        class="inputCom"
                        :style="{'width':'320px'}"
                        placeholder="请输入权限名称"
                        v-model="formValue.authName.value"
                        @focus="utils.toggleInputActive($event)"  
                        @blur="utils.toggleInputActive($event)">     
                </div>

                <div class="line clearfix">
                    <label for="url" class="pull-left"><sup>* </sup>路径：</label>
                    <input
                        class="inputCom"
                        :style="{'width':'320px'}"
                        placeholder="请输入路径"
                        v-model="formValue.url.value"
                        @focus="utils.toggleInputActive($event)"  
                        @blur="utils.toggleInputActive($event)">     
                </div>

                <div class="line clearfix">
                    <label for="authFId" class="pull-left"><sup>* </sup>父权限ID：</label>
                    <input 
                        class="inputCom readonly"
                        :style="{'width':'320px'}" 
                        readonly="readonly"
                        v-model="formValue.authFId.value">
                </div>
                <div class="line clearfix">
                    <label for="systemId" class="pull-left"><sup>* </sup>系统ID：</label>
                    <input 
                        class="inputCom readonly"
                        :style="{'width':'320px'}" 
                        readonly="readonly"
                        v-model="formValue.systemId.value">
                </div>                                

                <!--div class="line clearfix">
                   <label for="authFId" class="pull-left"><sup>* </sup>父权限：</label>
                   <v-selection
                        :vStyle="inputStyle"
                        :selections="selectionsAuth"
                        :defaultValue="this.formValue.authFId.value"
                        @on-change="modalAuthFSelectionChange">
                    </v-selection>
                </div>

                <div class="line clearfix">
                   <label for="systemId" class="pull-left"><sup>* </sup>所属系统：</label>
                   <v-selection 
                        :vStyle="inputStyle"
                        :selections="selections"
                        :defaultValue="this.formValue.systemId.value"
                        @on-change="modalSelectionChange">
                    </v-selection>
                </div-->

                <div class="line clearfix">
                    <label for="description" class="pull-left"><sup></sup>备注：</label>
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
            systemList:{
                type:Array,
                default(){
                    return []
                }
            },
            authList:{
                type:Array,
                default(){
                    return []
                }
            },            
            defaultData:{
                type:Object,
                default(){
                    return {
                        authId:'',
                        authName:'',
                        url:'',
                        authFId:'',
                        systemId:'',
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
                    authId:{value:"",label:"权限ID"},
                    authName:{value:"",label:"权限名称"},
                    url:{value:"",label:"路径"},
                    authFId:{value:"",label:"父权限ID"},
                    systemId:{value:"",label:"系统ID"},
                    description:{value:"",label:"备注信息"}
                },
                inputStyle:{
                    width:"222px"
                }                
            }
        },
        computed:{
            submitValue(){
                return  {
                    authId:this.formValue.authId.value,
                    authName:this.formValue.authName.value,
                    url:this.formValue.url.value,
                    // authFId:this.formValue.authFId.value,
                    systemId:this.formValue.systemId.value,
                    description:this.formValue.description.value
                }
            }
            /*,
            selections(){
                let tmpArr=[{label:"=请选择=",value:""}]
                this.systemList.forEach((v,i)=>{
                    tmpArr.push({label:v.systemName,value:v.systemId})
                })
                return tmpArr
            },
            selectionsAuth(){
                let tmpArr=[{label:"=请选择=",value:""}]
                this.authList.forEach((v,i)=>{
                    tmpArr.push({label:v.authName,value:v.authId})
                })
                return tmpArr
            }
            */
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
            setDefaultValue(){
                for(let key in this.formValue){
                    this.formValue[key]["value"] = this.defaultData[key]
                }
            },        
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
            /*
            modalSelectionChange(data){
                this.formValue.systemId.value=data.value
                if(!data.value){
                    this.characterTip = false
                    return
                }  
                this.characterTipText = data.label
                this.characterTip = true 
            },
            modalAuthFSelectionChange(data){
                this.formValue.authFId.value=data.value
                if(!data.value){
                    this.characterTip = false
                    return
                }  
                this.characterTipText = data.label
                this.characterTip = true 
            },
            */
            checkForm(){
                //判空
                for(let key in this.formValue){
                    if(!this.formValue[key].value && key != "description" && key != "authFId"){
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