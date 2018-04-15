<template>
    <div class="container">
        <transition name="drop">
            <div class="box" v-if="isShow" :class="{'fail':isFail}">
                <div class="leftIcon pull-left">
                    <img :src="isFail?failImg:successImg">
                </div>
                <p class="textBox">{{text}}</p>
            </div>
        </transition>     
    </div>
</template>

<script>
  export default {
    props:{
        isShow:{
            type:Boolean,
            default:false
        },
        isFail:{
            type:Boolean,
            default:false
        },
        text:{
            type:String,
            default:"测试"
        }
    },
    data () {
            return {
                successImg:require('../../assets/images/icons/success.png'),
                failImg:require('../../assets/images/icons/fail.png')
            }
        }
    }
</script>

<style scoped lang="less">
    .box{
        min-width: 260px;
        height: 60px;
        border: 1px solid #309bff;
        border-radius: 4px;
        padding-left: 66px;
        position: fixed;
        top:10%;
        left:50%;
        transform:translateX(-50%);
        background-color: #fff;
        z-index: 6;
        .leftIcon{
            position:absolute;
            left:0;
            top:0;
            height:100%;
            width: 66px;
            background-color: #309bff;
            >img{
                position:absolute;
                left:50%;
                top:50%;
                transform:translate(-50%,-50%);
            }
        }
        .textBox{
            width:100%;
            padding:0 58px 0 58px;
            height:60px;
            line-height: 60px;
            text-align: center;
            font-size: 18px;
        }
    }
    .box.fail{
        border: 1px solid #c02f4e;
        .leftIcon{
            background-color: #c02f4e;
        }
    }



    .drop-enter-active {
        transition: all .5s ease;
    }
    .drop-leave-active {
        transition: all .3s ease;
    }
    .drop-enter {
        transform: translate(-50%,-500px);
        opacity: 0
    }
    .drop-leave-active {
        transform: translate(-50%,-500px);
        opacity: 0
    }
</style>
