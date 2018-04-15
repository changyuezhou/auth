<template>
  <div class="guild-container">
    <p>
      <span>您的位置</span>
      <span v-for='item in positions'>&gt;{{item}}</span>
    </p>
  </div>
</template>

<script>
export default {
    props:{
      positions:{
        type:Array,
        default:[]
      }
    },
    data(){
      return {
    
      }
    }
}
</script>

<style scoped lang="less">
   .guild-container{
      font-size: 18px;
      color:#000;
   }
</style>
