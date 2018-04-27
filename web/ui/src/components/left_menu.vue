<template>
    <div class="leftNavContainer">
        <template v-for="(item,index) in leftMenuList"> 
            <div v-if="null != item.children" :key="item.authFId + index" class="link" :class="{'active':(index==currentExpendMenu)}" @click="currentExpendMenu=index">
                <span class="leftIcon" :style="{backgroundImage: 'url(' + require('@/assets/images/leftNav/icons/menu.png') + ')'}"></span>{{item.authName}}<span class="right-icon" :class="{'dropDown':(index==currentExpendMenu)}"></span>
            </div>
            <div v-if="null == item.children" :key="item.authFId + index" class="link" :class="{'active':(index==currentExpendMenu)}" @click="currentExpendMenu=index">
                <span class="leftIcon" :style="{backgroundImage: 'url(' + require('@/assets/images/leftNav/icons/menu.png') + ')'}"></span><router-link active-class="select" tag="li" :to="item.url">{{item.authName}}</router-link><span class="right-icon" :class="{'dropDown':(index==currentExpendMenu)}"></span>
            </div>
            <ul  :key="item.authId + index" :ref="item.authName" class="submenu" :style="{'height':(index==currentExpendMenu)?item.subHeight:0}">
                <router-link v-for="(v,i) in item.children" :key="i" active-class="select" tag="li" :to="v.url">{{v.authName}}</router-link>
            </ul>
        </template>
    </div>
</template>

<script>
export default {
    data() {
        return {
            leftMenuList:[],
            currentExpendMenu:0,
            isShow: false
        }
    },
    methods: {
        getCurrentExpendByRoute(routeName){
            for(let i = 0;i<this.leftMenuList.length;i++){
                let item = this.leftMenuList[i]
                let tmpArr = item.children.filter((value,index)=>{
                    return ("/"+value.url)==routeName
                })
               if(tmpArr.length!=0){
                   return i
               }
            }
        },
        getLeftMenuList() {
            this.apis.getLeftMenuList()
            .then((data) => {
                if (0 != data.code) {
                  this.$store.commit('show_global_alert',("获取菜单失败： " + data.msg))

                  return
                }

                if (null == data.data) {
                  this.$store.commit('show_global_alert',("获取菜单失败：data or list字段为空"))

                  return
                }
                this.leftMenuList = data.data
                this.currentExpendMenu = -1
            })
            .catch((errMsg)=>{
                this.$store.commit('show_global_alert',("获取菜单失败： "+errMsg))
            })
        }

    },
    created(){
        this.isShow=this.utils.getUrlParam('isShow',false)
        if (!this.isShow) {
            this.getLeftMenuList()
        }
    }
}
</script>

<style scoped lang="less">
    .leftNavContainer{
    position: fixed;
    width: 215px;
    top: 0;
    left: 0;
    height: 100%;
    padding: 83px 0px 0 0px;
    background-color: #e0e7ef;
    z-index: 4;
    .link{
        height: 55px;
        line-height: 55px;
        font-size: 16px;
        color:#1e2127;
        text-align: center;
        cursor: pointer;
        position:relative;
        >.right-icon{
            position:absolute;
            top:50%;
            margin-top: -7px;
            right:13px;
            height: 14px;
            width:14px;
            background: url('../assets/images/leftNav/dropArrow.png') no-repeat center center;
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
    .submenu{
        transition:height .5s ease;
        overflow:hidden;
        >li{
          height: 40px;
          line-height:40px;
          text-align:center;
          width: 100%;
          cursor:pointer;
          font-size:16px;
          background-color: #e3edf8;
          color:#1e2127;
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


