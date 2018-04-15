<template>
    <div class="alert-wrap">
        <v-dialog :isShow="isShow" @on-dialog-close="alertClose" :vStyle="vStyle">
          <slot>
            <p class="textBox">{{text}}</p>
          </slot>
          <div v-if="needCancel" class="buttonBox">
            <p class="pull-left confirm" @click="alertComfirm">确认</p>
            <p class="pull-left cancel" @click="alertClose">取消</p>
          </div>
          <div v-if="!needCancel" class="buttonBox hasCancel">
            <p class="confirm" @click="alertClose">确认</p>
          </div>
        </v-dialog>
    </div>
</template>


<script>
import vDialog from './dialog.vue'
export default {
  components:{
    vDialog
  },
  props:{
    isShow:{
      type:Boolean,
      default:false
    },
    text:{
      type:String,
      defalut:"警告"
    },
    needCancel:{
      type:Boolean,
      default:true
    }
  },
  data() {
      return {
        vStyle:{
          "minWidth":"300px",
          "paddingTop":"45px"
        }
      }
  },
  methods:{
    alertClose(){
      this.$emit("on-alert-close")
    },
    alertComfirm(){
      this.$emit("on-confirm")
    }
  }
}
</script>



<style scoped lang="less">
  .container{
    min-width:300px;
  }
  .textBox{
    min-width: 300px;
    padding:0 42px 42px 42px;
    text-align:center;
    color:#1773ca;
  }
  .buttonBox{
    p{
      width:50%;
      height: 42px;
      line-height: 42px;
      text-align:center;
      font-size:16px;
      cursor:pointer;
    }
    .confirm{
      color:#fff;
      background-color: #309bff;
    }
    .confirm:hover{
      background-color: #7abefe;
    }
    .cancel{
      color:#309bff;
      background-color: #cddae8;
    }
    .cancel:hover{
      background-color: #eeeeee;
    }
  }
  .buttonBox.hasCancel{
    p{
      width:100%;
    }
  }
</style>
