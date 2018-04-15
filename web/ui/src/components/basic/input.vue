<template>
    <div class="container">
        <input  v-if="vType==='text'" 
                class="inputEl" 
                :class="[{'active':isActive},{'readonly':isReadonly}]" 
                type="text" 
                :placeholder="vPlaceholder"  
                v-model="text" 
                :style="vStyle" 
                :readonly="isReadonly" 
                @focus="onFocus" 
                @blur="onBlur"
                @input="onInput" >

         <input v-if="vType==='password'" 
                class="inputEl" 
                :class="[{'active':isActive},{'readonly':isReadonly}]" 
                type="password" 
                :placeholder="vPlaceholder"  
                v-model="text" 
                :style="vStyle" 
                :readonly="isReadonly" 
                @focus="onFocus" 
                @blur="onBlur" 
                @input="onInput">
    </div>
</template>

<script>
    import {eventBus} from '../../es6/eventBus.js'
    export default {
        props:{
            vStyle:{
                type:Object,
                default(){
                    return {}
                }
            },
            noReset:{
                type:Boolean,
                default:false
            },
            vPlaceholder:{
                type:String,
                default:"请输入..."
            },
            defaultValue:{
                type:[String,Number],
                default:""
            },
            isReadonly:{
                type:Boolean,
                default:false
            },
            vType:{
                type:String,
                default:"text"
            }

        },
        data(){
            return {
                isActive:false,
                text:this.defaultValue
            }
        },
        methods:{
            onFocus(){
                if(!this.isReadonly){
                    this.isActive = true;
                }     
            },
            onBlur(){
                this.isActive = false;
                this.$emit("on-change",this.text)
            },
            onInput(){
                this.$emit("input",this.text)
            }
        },
        mounted(){
            eventBus.$on("reset-update-psw",()=>{
                if(this.noReset){
                    return 
                }
                this.text=""
            })
        }
    }

</script>
<style scoped lang="less">
    .container{
       .inputEl{
            width: 320px;
            height: 40px;
            border: 1px solid #d2d2d2; 
            padding-left: 24px;
            line-height: 40px;
            font-size: 14px;
            transition: all 0.30s ease-in-out;
            -webkit-transition: all 0.30s ease-in-out;
            -moz-transition: all 0.30s ease-in-out;
        } 
        .inputEl.active{
            border: 1px solid #1773ca;
            box-shadow: 0 0 5px rgba(23, 115, 202, 1);
            -webkit-box-shadow: 0 0 5px rgba(23, 115, 202, 1);
            -moz-box-shadow: 0 0 5px rgba(23, 115, 202, 1);
        }
        .inputEl.readonly{
            background-color: #eeeeee;
            color:#888888;
        }
    }  
</style>