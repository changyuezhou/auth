<template>
      <div class="container">
        <div class="link" :class="{'active':isExpand}" @click="switchDrop"><span class="leftIcon" :style="{'background-image':'url('+titleIcon+')'}"></span>{{title}} <span class="right-icon" :class="{'dropDown':isExpand}"></span></div>
        <ul class="submenu" ref="subContainer" :style="{'height':isExpand?subHeight+'px':0}">
          <router-link v-for="item in submenu" :to="item.router" tag="li" active-class="select" :key="item.router">{{item.label}}</router-link>
        </ul>
      </div>
</template>

<script>

export default {
  props:{
    title:{
      type:String,
      default:"一级菜单"
    },
    titleIcon:{
      type:String,
      default:require("../../../static/images/leftNav/agentMng.png")
    },
    submenu:{
      type:Array,
      default(){
        return [
            {
              label:"二级菜单1",
              router:"/main"
            },
            {
              label:"二级菜单2",
              router:"/sec"
            },
            {
              label:"二级菜单3",
              router:"/main"
            }
          ]
      }
    },
    isExpand:{
      type:Boolean,
      default:false
    }
  },
  data(){
    return {
      subHeight:0
    }
  },
  methods:{
    switchDrop(){
      this.$emit("switchDrop",this.isExpand)
    }
  },
  mounted(){
    this.subHeight=(this.$refs.subContainer.children.length*(this.$refs.subContainer.children[0].clientHeight))
  }
}
</script>

<style scoped lang="less">
    .container{
      .link{
        height: 55px;
        line-height: 55px;
        font-size: 16px;
        color:#1e2127;
        text-align: center;
        // background-color: #1773ca;
        cursor: pointer;
        position:relative;
        >.right-icon{
          position:absolute;
          top:50%;
          margin-top: -7px;
          right:13px;
          height: 14px;
          width:14px;
          background: url('../../images/leftNav/dropArrow.png') no-repeat center center;
          transition:all .3s linear;
        }
        >.right-icon.dropDown{
          transform:rotate(180deg);
        }
        >.leftIcon{
          position:absolute;
          width:25px;
          height: 25px;
          left:20px;
          top:50%;
          margin-top:-12.5px;
          background-position: center center;
          background-repeat: no-repeat;
        }
      }
      .link:hover{
        background-color: #cddae8;
      }
      .link.active{
        background-color: #cddae8;
      }
      // .link:after{
      //     content:"";
      //     width:100%;
      //     height: 7px;
      //     position:absolute;
      //     bottom:0;
      //     left:0;
      //     background-image: url('../../assets/dropDown/sep-line.png');
      //   }
      .submenu{
        transition:height .5s ease;
        overflow:hidden;
        >li{
          height: 40px;
          line-height:40px;
          text-align:center;
          width: 100%;
          cursor:pointer;
          font-size:14px;
          background-color: #e3edf8;
          color:#5b5b5b;
        }
        >li:hover{
          background-color: #cddae8;
          color:#206ae6;
        }
        >li.select{
          background-color: #cddae8;
          color:#206ae6;
        }
      }
    }

</style>
