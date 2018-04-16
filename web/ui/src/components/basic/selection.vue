<template>
    <div class="selection-component">
      <div class="selection-show" ref="selBox" :class="{'droped':isDrop}" :style="vStyle" @click.stop="toggleDrop">
        <span>{{ selections[nowIndex].label }}</span>
        <img class="arrow" src="../../assets/images/leftNav/dropArrow.png" height="9" width="14">
      </div>
      <div class="selection-list" v-if="isDrop" :style="{'top':boxHeight}">
        <ul>
          <li v-for="(item, index) in selections" :key="item" @click.stop="chooseSelection(index)">{{ item.label }}</li>
        </ul>
      </div>
    </div>
</template>

<script>
  import {eventBus} from '../../common/eventBus.js'
  export default {
    props: {
      selections: {
        type: Array,
        default(){
            return [{
            label: 'test',
            value: ''
          }]
        }
      },
      vStyle:{
        type:Object,
        default(){
            return {}
          }
      },
      defaultValue:{
        type:[String,String],
        default:""
      }
    },
    data () {
      return {
        isDrop: false,
        nowIndex: 0,
        boxHeight:40
      }
    },
    watch:{
      defaultValue(cur,old){
         //如果有默认值设置默认
        if(this.defaultValue||this.defaultValue==""){
            this.selections.forEach((v,i)=>{
                if(v.value==this.defaultValue){
                  this.nowIndex=i
                  return 
                }
            })
        }
      }
    },
    mounted(){
        //联动下拉框的高
        this.boxHeight=this.$refs.selBox.getBoundingClientRect().height   
        //点击空白出收起下拉框
        eventBus.$on("reset-components",()=>{
            this.isDrop = false;
          })
    },
    methods: {
      toggleDrop () {
        if(!this.isDrop){
            this.$emit("click")
        } 
        this.isDrop = !this.isDrop
      },
      chooseSelection (index) {
        this.nowIndex = index
        this.isDrop = false
        this.$emit('on-change', this.selections[this.nowIndex])
      }
    }
  }
</script>

<style scoped lang="less">
.selection-component {
  position: relative;
  display: inline-block;
  font-size:14px;
}
.selection-show {
  border: 1px solid #e3e3e3;
  width: 320px;
  height: 40px;
  display: inline-block;
  position: relative;
  cursor: pointer;
  line-height: 40px;
  border-radius: 3px;
  background: #fff;
  border: 1px solid #d2d2d2;
  padding-left: 25px;
}
.selection-show .arrow {
  position: absolute;
  right:15px;
  top:15px;
}
.selection-show.droped{
  border-bottom: none;
  border-bottom-left-radius:0; 
  border-bottom-right-radius:0; 
}
.selection-show.droped .arrow{
  transform: rotate(180deg);
}
.selection-list {
  display: inline-block;
  position: absolute;
  max-height: 200px;
  overflow-y: scroll;
  left: 0;
  width: 100%;
  background: #fff;
  border-top: 1px solid #e3e3e3;
  border-bottom: 1px solid #e3e3e3;
  z-index: 5;
}
.selection-list li {
  padding: 10px 25px 10px 25px;
  border-left: 1px solid #e3e3e3;
  border-right: 1px solid #e3e3e3;
  cursor: pointer;
  background: #fff;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

}
.selection-list li:hover {
  background: #e3e3e3;
}
</style>
