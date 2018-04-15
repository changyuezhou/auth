<template>
    <div class="chooser-component">
     <label 
      v-for="(item,index) in selections" 
      :style="vStyle" 
      :class="{active:item.chosed}" 
      :value="item.value" 
      :key="index"
      @click="toggleChose({index,item})">
        <span>
          <i></i>
        </span>
        {{item.label}}
     </label>
    </div>
</template>

<script> 



export default {
  props:{
    selections:{
      type:Array,
      default(){
        return [
                {
                  label:"无敌1",
                  value:"1",
                  chosed:false
                },
                {
                  label:"无敌2",
                  value:"2",
                  chosed:true
                },
                {
                  label:"无敌3",
                  value:"3",
                  chosed:false
                }
              ]
      }  
    },
    vStyle:{
      type:Object,
      default(){
        return {}
      }
    }
  },
  methods:{
    toggleChose(item){
        this.$emit("on-change",item)
    },
    hasChosed(index){
      return this.nowIndex.indexOf(index)!==(-1)
    }
  }
}
</script>
<style scoped lang="less">
    .chooser-component>label{
      display:inline-block;
      height:20px;
      line-height:20px;
      padding:0 5px 0 5px;
      cursor:pointer;
      margin-bottom: 10px;
      >span{
        display:inline-block;
        width: 20px;
        height:20px;
        border: 1px solid #b3c3c9;
        vertical-align: bottom;
        margin-right:10px;
        position: relative;
        background-color: #fff;
        >i{
          position:absolute;
          width:14px;
          height:9px;
          top:50%;
          left:50%;
          transform:translate(-50%,-50%);
          background: url("../../assets/images/icons/onCheck.png") no-repeat center center;
        }
      }
    }
    .chooser-component>label.active{
      >span{
        border: none;
        background-color: #309bff;
      }
    }
</style>
