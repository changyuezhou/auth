<template>
    <div class="leftNavContainer">
        <template v-for="(item,index) in leftMenuList"> 
            <div  :key="index" class="link" :class="{'active':(index==currentExpendMenu)}" @click="currentExpendMenu=index">
                <span class="leftIcon" :style="{backgroundImage: 'url(' + item.icon + ')'}"></span> {{item.title}}
                <span class="right-icon" :class="{'dropDown':(index==currentExpendMenu)}"></span>
            </div>
            <ul  :key="index" :ref="item.label" class="submenu" :style="{'height':(index==currentExpendMenu)?item.subHeight:0}">
                <router-link v-for="(v,i) in item.subMenu" :key="i" active-class="select" tag="li" :to="v.router">{{v.title}}</router-link>
            </ul>
        </template>
    </div>
</template>

<script>
export default {
    data() {
        return {
            leftMenuList:[],
            currentExpendMenu:0
        }
    },
    methods: {
        formatLeftMenu(array){
            let f_auths = {},
                s_auths = [],
                finalData = []
            //分离父子菜单
            array.forEach((v,i)=>{
                if(v.auth_level!=0&&!f_auths[v.auth_f_id]){
                    f_auths[v.auth_f_id] = {subMenu:[]}
                    Object.assign(f_auths[v.auth_f_id],v,this.left_menu.left_menu_config.f_auths[v.auth_f_id])
                }
                if(v.auth_level!=0){
                    let tmpObj = {}
                    Object.assign(tmpObj,v,this.left_menu.left_menu_config.s_auths[v.auth_id])
                    s_auths.push(tmpObj)
                }
            })
            //父子菜单按对应关系关联
            s_auths.forEach((v,i)=>{
                if(f_auths[v.auth_f_id]){
                    f_auths[v.auth_f_id].subMenu.push(v)
                }
            })
            //格式化最终数据
            for(let key in f_auths){
                let item = f_auths[key]
                if(item.subMenu.length>0){
                     item.subHeight=item.subMenu.length*40+"px"
                    finalData[item.index] = item
                }   
            }
            return finalData.filter((v,i)=>{
                return v!=undefined
            })
        },
        getCurrentExpendByRoute(routeName){
            for(let i = 0;i<this.leftMenuList.length;i++){
                let item = this.leftMenuList[i]
                let tmpArr = item.subMenu.filter((value,index)=>{
                    return ("/"+value.router)==routeName
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

                if (null == data.data || null == data.data.list) {
                  this.$store.commit('show_global_alert',("获取菜单失败：data or list字段为空"))

                  return
                }
                this.leftMenuList=this.formatLeftMenu(data.data.list)
                 //第一次进入默认进到第一级的第一个菜单
                if(this.$route.path=='/'){
                    this.$router.push(this.leftMenuList[0].subMenu[0].router)
                }else{
                    this.currentExpendMenu=this.getCurrentExpendByRoute(this.$route.path)
                }
            })
            .catch((errMsg)=>{
                this.$store.commit('show_global_alert',("获取菜单失败： "+errMsg))
            })
        }

    },
    created(){
        this.getLeftMenuList()
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


