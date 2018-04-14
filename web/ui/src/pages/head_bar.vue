<template>
  <div class="header" >
    <h4 class="pull-left">云协同管理系统</h4>
<!--    <div class="logoImg pull-left"></div>-->
    <div class="pull-right quit" @click="quit">退出</div>
    <div class="pull-right name">{{userName}}</div>
  </div>
</template>

<script>
export default {
    props:{
        userName:{
            type:[String,Number],
            default:"未知"
        }
    },
    data(){
      return {
    
      }
    },
    methods:{
        quit(){
            let domain = "."+location.hostname
            this.utils.setCookie('access_token','',-1,domain)
            this.utils.setCookie('open_id','',-1,domain)
            this.utils.setCookie('user_name','',-1,domain)
            this.apis.getLeftMenuList()
                .then((data)=>{
    
                })
                .catch((errMsg)=>{
              
                })
        }
    }
}
</script>

<style scoped lang="less">
   .header{
        position: fixed;
        top:0;
        left:0;
        height:65px;
        width: 100%;
        background-color: #1773ca;
        z-index: 5;
        h4{
           font-size:24px;
           color:#fff;
           height: 65px;
           line-height: 65px;
           padding-left: 32px;
        }
        /*.logoImg{
            width: 124px;
            height: 64px;
            background-image: url('../assets/logo.png');
            background-repeat: no-repeat;
            background-size: cover;
            margin-left: 20px;
        }*/
        .name,.quit{
            height: 100%;
            line-height: 65px;
            color:#fff;
            font-size:18px;
            margin-right: 18px;
        }
        .quit{
            text-decoration: underline;
            cursor: pointer;
            width:53px;
            margin-right: 55px;
            font-size: 16px;
            padding-right: 20px;
            background-image: url('../assets/images/icons/quit.png');
            background-repeat: no-repeat;
            background-position: right center;
        }
   }
</style>
