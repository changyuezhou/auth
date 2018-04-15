<template>
    <div class="chooser-component">
     <!--  <div v-for="(item,index) in selections" :class="{active:hasChosed(index)}" :value="item.value" @click="toggleChose(index)">{{item.label}}</div> -->
     <label v-for="(item,index) in selections" :style="vStyle" :class="{active:hasChosed(index)}" :value="item.value" @click="toggleChose(index)">
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
  data(){
    return {
      nowIndex:[]
    }
  },
  computed:{
    result(){
      return (this.nowIndex.map((i,v)=>{
        return this.selections[i]
      }));
    }
  },
  methods:{
    toggleChose(index){
        var myIndex = this.nowIndex.indexOf(index)
        if(myIndex===-1){
          this.nowIndex.push(index);
        }else{
          this.nowIndex.splice(myIndex,1)
        };
        this.$emit("on-change",this.result)
    },
    hasChosed(index){
      return this.nowIndex.indexOf(index)!==(-1)
    }
  },
  mounted(){
    this.selections.forEach((v,i)=>{
      if(v.chosed){
        this.nowIndex.push(i)
      }
    })
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
          background: url("../../images/basic/onCheck.png") no-repeat center center;
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
