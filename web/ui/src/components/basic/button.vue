<template>
  <div class="container">
    <button @click="btnClick" :class="[type,size]" :text="text">
      {{text}}
    </button>
  </div>
</template>

<script>
export default {
    props:{
      type:{
        type:String,
        default:"default"
      },
      text:{
        type:String,
        default:"默认"
      },
      isBigBtn:{
        type:Boolean,
        default:false
      }
    },
    data(){
      return {
    
      }
    },
    computed:{
      size(){
        if(this.isBigBtn){
          return "big"
        }
        return this.text.length>2?"big":"small"
      }
    },
    methods:{
      btnClick(){
        this.$emit("click",event)
      }
    },
    mounted(){
      
    }
}
</script>

<style scoped>
  button{
    text-align: center;
    font-size:14px;
    cursor: pointer;
    border-radius: 2px;
    box-sizing: border-box;
  }
  button.big{
    min-width: 96px;
    padding:11px 20px 11px 20px;
  }
  button.small{
    width: 96px;
    height: 36px;
  }
  button.default{
    color:#3c3c3c;
    background-color: #fff;
    border: 1px solid #a0a0a0;
  }
  button.default.big{
    padding:10px 19px 10px 19px;
  }
  button.default:hover{
    background-color: #eeeeee;
  }
  button.primery{
    background-color: #309bff;
    color:#fff;
  }
  button.primery:hover{
    background-color: #7abefe;
  }
  button.warn{
    background-color: #e60012;
    color:#fff;
  }
  button.warn:hover{
    background-color: #cb1d2b;
  }
</style>
