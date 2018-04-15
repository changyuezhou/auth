<template>
    <div class="chooser-component">
      <label v-for="(item,index) in selections" :class="{'active':index==nowIndex}" :value="item.value" @click="choosedOne(index)">
        <span><i></i></span>
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
                  value:"1"
                },
                {
                  label:"无敌2",
                  value:"2",
                  chosed:true
                },
                {
                  label:"无敌3",
                  value:"3"
                }
              ]
      }

      
    }
  },
  data(){
    return {
      nowIndex:0
    }
  },
  methods:{
    choosedOne(index){
      this.nowIndex= index;
      this.$emit("on-change",this.selections[index])
    }
  },
  mounted(){
    this.selections.forEach((v,i)=>{
      if(v.chosed){
        this.nowIndex=i
      }
    })
  }
}
</script>
<style scoped lang="less">
  .chooser-component{
    label{
      display:inline-block;
      height: 20px;
      line-height:20px;
      cursor:pointer;
      padding: 0 5px 0 5px;
      >span{
        display:inline-block;
        vertical-align:bottom;
        height:20px;
        width: 20px;
        border: 1px solid #bccad0;
        border-radius:50%;
        margin-right:10px;
        overflow: hidden;
        position:relative;
        >i{
          position:absolute;
          height:8px;
          width:8px;
          border-radius:50%;
          background-color: #fff;
          top:50%;
          left:50%;
          transform:translate(-50%,-50%);
          display:none;
        }
      }
    }
    label.active{
      >span{
        background-color:  #309dfc;;
       >i{
        display:block;
       }
      }
    }
  }
</style>
