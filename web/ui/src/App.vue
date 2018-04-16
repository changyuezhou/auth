<template>
  <div id="app">
    <head-bar :userName="user_name"></head-bar>
    <left-Nav></left-Nav>
    <transition name="fade" mode="out-in">
      <router-view></router-view>
    </transition>

    <!-- 全局警告弹框 -->
    <!-- 
      使用：打开 this.$store.commit('show_global_alert',"文字")
     -->
    <v-alert :needCancel="false" :isShow="$store.state.global_alert.show" :text="$store.state.global_alert.text" @on-alert-close="$store.commit('close_global_alert')"></v-alert>
    
    <!-- 全局 成功toast -->
    <!-- 
      使用：打开 this.$store.dispatch('showToast','文字')
     -->
    <v-toast :isShow="$store.state.global_toast.show" :text="$store.state.global_toast.text"></v-toast>
    <!-- loading -->
    <!-- loading></loading -->
  </div>
</template>

<script>
import headBar from '@/components/head_bar'
import leftNav from '@/components/left_menu'
import loading from '@/components/basic/loading'
import {eventBus} from '@/common/eventBus.js'
export default {
    components:{
      headBar,
      leftNav,
      loading
    },
    data(){
      return {
      user_name:"未知"
      }
    },
    methods:{
      resetComponents(){
        eventBus.$emit("reset-components")
      }
  },
  beforeMount(){
    this.user_name = this.utils.getCookie('user_name')||"用户"
  },
    mounted(){
      document.body.addEventListener('click',(e)=>{
          this.resetComponents()
      })
    }
}
</script>

<style>
  #app{
    width: 100%;
    min-height: 100%;
    min-width: 1366px;
    padding-top: 98px;
    padding-left: 248px;
    font-size: 14px;
    color:#1e2127;
  }
  
  .inputCom {
    width: 222px;
    height: 40px;
    border: 1px solid #d2d2d2;
    padding-left: 24px;
    line-height: 40px;
    font-size: 14px;
    transition: all 0.30s ease-in-out;
    -webkit-transition: all 0.30s ease-in-out;
    -moz-transition: all 0.30s ease-in-out;
  }

  .inputCom.active {
    border: 1px solid #1773ca;
    box-shadow: 0 0 5px rgba(23, 115, 202, 1);
    -webkit-box-shadow: 0 0 5px rgba(23, 115, 202, 1);
    -moz-box-shadow: 0 0 5px rgba(23, 115, 202, 1);
  }

  .inputCom.readonly {
    background-color: #eeeeee;
    color: #888888;
  }

</style>