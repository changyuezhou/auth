<template>
    <div class="container">
        <head-guild :positions='["授权配置", "用户配置"]'></head-guild>
       <label for="platformId" class="pull-left"><sup></sup>平台：</label>
       <v-selection 
            :vStyle="inputStyle"
            :selections="selections"
            :defaultValue="this.platformList.value"
            @on-change="modalSelectionChange">
        </v-selection>        
        <div class="table">
            <table>
                <thead>
                    <tr>
                        <td>平台</td>
                        <td>列表</td>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(value,key) in this.tableData" :key="key">
                        <td>
                            <label for="platformName" class="pull-left">   {{value.platformName}}</label>
                        </td>                     
                        <td>
                            <v-check-box 
                                :vStyle="vStyle" 
                                :selections="[value]"
                                @on-change="item_change">
                            </v-check-box>
                        </td>                       
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="btnBox clearfix">
            <v-button type="primery" class="pull-left btn" text="确认提交" @click="submit"></v-button>
            <v-button type="default" class="pull-left btn" :isBigBtn="true" text="返回" @click="goBack"></v-button>
        </div>

    </div>
</template>

<script>
    export default{
        data(){
            return {
                userId:this.$route.query.userId-0,
                type: this.$route.query.type-0,
                desc: this.$route.query.desc-0,
                vStyle:{'margin-bottom':0},
                tableData:[],
                grant_list:[],
                platformList:[],
                canSubmit:true,
            }
        },
          computed:{
              selections(){
                  let tmpArr=[{label:"=请选择=",value:""}]
                  this.platformList.forEach((v,i)=>{
                      tmpArr.push({label:v.platformName,value:v.platformId})
                  })
                  return tmpArr
              }            
          },         
        methods:{
            modalSelectionChange(data){
                if(!data.value){
                    this.characterTip = false
                    this.getTableData()
                    return
                }
                this.characterTipText = data.label
                this.characterTip = true

                this.getTableData(data.value)
            },        
            //模块点击事件
            item_change(data){
                let rowData = data.item
                    rowData.chosed=!rowData.chosed
            },
            IsKeyInSet(id, set) {
              let is_in = false
              set.forEach((v,i)=>{
                if (v == id) {
                  is_in = true
                }
              })

              return is_in
            },
            diff_set(left, right) {
              let set = []
              left.forEach((v,i)=>{
                if (!this.IsKeyInSet(v, right)) {
                  set.push(v)
                }
              })

              return set
            },
            //确认提交
            submit() {
                if (this.canSubmit) {
                    this.canSubmit = false
                    let tmpArr = []
                    for (let key in this.tableData) {
                        if (this.tableData[key].chosed) {                       
                            tmpArr.push(this.tableData[key].value)
                        }
                    }

                    //判断是否有修改
                    if (tmpArr.sort().join('') == this.grant_list.sort().join('')) {
                        this.$store.commit('show_global_alert','没有任何修改')
                        this.canSubmit = true
                        return
                    }

                    let del = this.diff_set(this.grant_list, tmpArr)
                    let add = this.diff_set(tmpArr, this.grant_list)

                    this.grant_list = tmpArr

                    if (1 == this.type) {
                        if (0 < add.length) {
                            this.apis.addUserGroup(this.userId, add)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/userMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })
                        }

                        if (0 < del.length) {
                            this.apis.delUserGroup(this.userId, del)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/userMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })                        
                        }
                    }

                    if (2 == this.type) {
                        if (0 < add.length) {
                            this.apis.addUserRole(this.userId, add)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/userMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })
                        }

                        if (0 < del.length) {
                            this.apis.delUserRole(this.userId, del)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/userMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })                        
                        }
                    }

                    if (3 == this.type) {
                        if (0 < add.length) {
                            this.apis.addUserOrganization(this.userId, add)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/userMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })
                        }

                        if (0 < del.length) {
                            this.apis.delUserOrganization(this.userId, del)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/userMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })                        
                        }
                    }                    
                } else { return }
            },
            //返回
            goBack(){
                this.$router.push({path:"/userMng"})        
            },
            isItemNameInList(name, platformId, item_list, type) {
                let is_selected = false
                item_list.forEach((v,i)=>{
                  if (1 == type) {
                    if (name == v.groupName && platformId == v.platformId) {
                        is_selected = true
                    }
                  }

                  if (2 == type) {
                    if (name == v.roleName && platformId == v.platformId) {
                        is_selected = true
                    }
                  }


                  if (3 == type) {
                    if (name == v.organizationName && platformId == v.platformId) {
                        is_selected = true
                    }
                  }                  
                })

                return is_selected
            },
            //用来格式化请求获得的数据（必须在获取所有权限和角色拥有权限后使用）
            fomatTableData(all_list, grant_list){
                //父子菜单分离
                all_list.forEach((v,i)=>{
                    if (1 == this.type) {
                        v["chosed"] = this.isItemNameInList(v.groupName, v.platformId,  grant_list, this.type)
                        v["label"] = v.groupName
                        v["value"] = v.groupId
                    }

                    if (2 == this.type) {
                        v["chosed"] = this.isItemNameInList(v.roleName, v.platformId, grant_list, this.type)
                        v["label"] = v.roleName
                        v["value"] = v.roleId
                    }

                    if (3 == this.type) {
                        v["chosed"] = this.isItemNameInList(v.organizationName, v.platformId, grant_list, this.type)
                        v["label"] = v.organizationName
                        v["value"] = v.organizationId
                    }

                    v["index"] = i
                })

                return all_list
            },
            Insert2GrantList(grant_list, type) {
                if (null == grant_list) {
                  return
                }
                grant_list.forEach((v,i)=>{
                  if (1 == type) {
                    this.grant_list.push(v.groupId)
                  }
                  if (2 == type) {
                    this.grant_list.push(v.roleId)
                  }
                  if (3 == type) {
                    this.grant_list.push(v.organizationId)
                  }                                    
                })            
            },
            getTableData(platformId) {
                (async ()=>{
                    //1.获取所有列表
                    let all_list = null
                    let grant_list = null
                    this['userId']=this.utils.getUrlParam('userId')
                    this['type']=this.utils.getUrlParam('type')

                    if (1 == this.type) {
                        if (null != platformId) {
                            all_list = (await this.apis.getGroupByPlatformId(platformId, 1, 10000)).data
                        } else {
                            all_list = (await this.apis.getGroupList()).data
                        }
                        grant_list = (await this.apis.getUserGroup(this.userId)).data
                    }
                    if (2 == this.type) {
                        if (null != platformId) {
                            all_list = (await this.apis.getRoleByPlatformId(platformId, 1, 10000)).data
                        } else {                    
                            all_list = (await this.apis.getRoleList()).data
                        }
                        grant_list = (await this.apis.getUserRole(this.userId)).data
                    }
                    if (3 == this.type) {
                        if (null != platformId) {
                            all_list = (await this.apis.getOrganizationByPlatformId(platformId, 1, 10000)).data
                        } else {
                            all_list = (await this.apis.getOrganizationList()).data
                        }
                        grant_list = (await this.apis.getUserOrganization(this.userId)).data
                    }

                    if (null != grant_list) {
                        this.Insert2GrantList(grant_list, this.type)
                    }

                    this.tableData = this.fomatTableData(all_list, grant_list)
                })()
                .then(()=>{
                })
                .catch((errMsg)=>{
                    this.$store.commit('show_global_alert',("错误"+errMsg))
                })            
            }
        },
        mounted(){
            this.apis.getPlatformList(1, 10000).then((data)=>{
                this.platformList=data.data
            })

            this.getTableData()        
        }
    }

</script>
<style scoped lang="less">
    .container{
        .table{
            width: 98%;
            margin-top: 14px;
            >table{
                width:100%;
                border: 1px solid #b3c3c9;
                >thead{
                    text-align: center;
                    background-color: #e3edf8;
                    >tr{
                        height: 50px;
                        td:first-of-type{
                            width:9.3%
                        }
                        td:nth-of-type(2){
                            width:24.4%;
                        }
                    }
                }
                >tbody{
                    >tr{
                        height:50px;
                        border-top: 1px solid #b3c3c9;
                        >td:not(:last-of-type){
                            text-align:center;
                            border-right: 1px solid #b3c3c9;
                        }
                        >td:last-of-type{
                            padding-left: 50px;
                        }
                        >td:nth-of-type(2){
                            text-align: left !important;
                            padding-left: 50px;
                        }
                    }
                    >tr:hover{
                        background-color: #f1f7fd;
                    }
                }
            }
        }
        .btnBox{
            margin-top:30px;
            .btn:last-of-type{
                margin-left:60px;
            }
        }
    }
    
</style>