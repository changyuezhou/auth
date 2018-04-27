<template>
    <div class="container">
        <head-guild :positions='["授权配置", "权限列表"]'></head-guild>
        <div class="table">
            <table>
                <thead>
                    <tr>
                        <td>模块</td>
                        <td>权限列表</td>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(value,key) in this.tableData" :key="key">
                        <td>
                            <v-check-box 
                                :vStyle="vStyle" 
                                :selections="[value]"
                                @on-change="f_auth_change">
                            </v-check-box>
                        </td>
                        <td>
                            <v-check-box 
                                :vStyle="vStyle" 
                                :selections="value.children"
                                @on-change="auth_change">
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
                id:this.$route.query.id-0,
                platformId:this.$route.query.platformId-0,
                type: this.$route.query.type-0,
                desc: this.$route.query.desc-0,
                vStyle:{'margin-bottom':0},
                tableData:[],
                grant_list:[],
                canSubmit:true,
            }
        },
        methods:{
            //模块点击事件
            f_auth_change(data){
                let rowData = data.item
                    rowData.chosed=!rowData.chosed
                    rowData.children.forEach((v,i)=>{
                    v.chosed = rowData.chosed
                })
            },
            //权限列表点击事件
            auth_change(data){
                //获取其父模块key
                let f_index = data.item.parent
                data.item.chosed = !data.item.chosed

                //判断子选项是否已全选
                if(data.item.chosed){
                    let isAllChecked = true
                    let parent_item = this.tableData[f_index]
                    for(let i = 0;i < parent_item.children.length;i++){
                        if(!parent_item.children[i].chosed){
                            isAllChecked = false
                            break
                        }
                    }
                    if(isAllChecked){
                        this.tableData[f_index].chosed = true
                    }
                } else {
                    //取消父级的选择
                    this.tableData[f_index].chosed=false
                }            
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
                            tmpArr.push(this.tableData[key].authId)
                        }
                        if (null != this.tableData[key].children) {
                            this.tableData[key].children.forEach((v, i) => {
                                if (v.chosed) {
                                    tmpArr.push(v.authId)
                                }
                            })
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

                    if (1 == this.type) {
                        if (0 < add.length) {
                            this.apis.addGroupAuthority(this.id, add)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/groupMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })
                        }

                        if (0 < del.length) {
                            this.apis.delGroupAuthority(this.id, del)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/groupMng'})
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
                            this.apis.addRoleAuthority(this.id, add)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/roleMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })
                        }

                        if (0 < del.length) {
                            this.apis.delRoleAuthority(this.id, del)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/roleMng'})
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
                            this.apis.addOrganizationAuthority(this.id, add)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/orgMng'})
                                },2100)
                            })
                            .catch((errMsg) => {
                                this.$store.commit('show_global_alert',("错误： "+errMsg))
                                this.canSubmit = true
                            })
                        }

                        if (0 < del.length) {
                            this.apis.delOrganizationAuthority(this.id, del)
                            .then((res) => {
                                this.$store.dispatch('showToast','修改成功')
                                this.canSubmit = true
                                setTimeout(()=>{
                                    this.$router.push({path:'/orgMng'})
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
                if (1 == this.type) {
                    this.$router.push({path:"/groupMng"})
                }

                if (2 == this.type) {
                    this.$router.push({path:"/roleMng"})
                }

                if (3 == this.type) {
                    this.$router.push({path:"/orgMng"})
                }                
            },
            isAuthInList(authName, auth_list) {
                let is_selected = false
                auth_list.forEach((v,i)=>{
                  if (authName == v.authName) {
                    is_selected = true
                  }

                  if (null != v.children) {
                    v.children.forEach((item, j) => {
                        if (authName == item.authName) {
                            is_selected = true
                        }                    
                    })
                  }
                })

                return is_selected
            },
            //用来格式化请求获得的数据（必须在获取所有权限和角色拥有权限后使用）
            fomatTableData(auth_list, grant_auth_list){
                //父子菜单分离
                auth_list.forEach((v,i)=>{
                    v["chosed"] = this.isAuthInList(v.authName, grant_auth_list)
                    v["index"] = i
                    v["label"] = v.authName
                    v["value"] = v.authId
                    if (null != v.children) {
                        v.children.forEach((item, j) => {
                          item["chosed"] = this.isAuthInList(item.authName, grant_auth_list)
                          item["index"] = j
                          item["parent"] = i
                          item["label"] = item.authName
                          item["value"] = item.authId                          
                        })
                    }
                })

                return auth_list
            },
            Insert2GrantList(grant_auth_list) {
                if (null == grant_auth_list) {
                  return
                }
                grant_auth_list.forEach((v,i)=>{
                  this.grant_list.push(v.authId)
                  if (null != v.children) {
                    v.children.forEach((item, j) => {
                      this.grant_list.push(item.authId)                   
                    })
                  }
                })            
            }
        },
        mounted(){
            (async ()=>{
                this['id']=this.utils.getUrlParam('Id')
                this['type']=this.utils.getUrlParam('type')
                this['platformId']=this.utils.getUrlParam('platformId')

                //1.获取所有权限列表
                let all_auth_list = (await this.apis.getAuthorityTreeByPlatformId(this.platformId)).data
                let grant_auth_list = null
                if (1 == this.type) {
                    grant_auth_list = (await this.apis.getGroupAuthorityList(this.id, 1, 10000)).data
                }
                if (2 == this.type) {
                    grant_auth_list = (await this.apis.getRoleAuthorityList(this.id, 1, 10000)).data
                }
                if (3 == this.type) {
                    grant_auth_list = (await this.apis.getOrganizationAuthorityList(this.id, 1, 10000)).data
                }

                if (null != grant_auth_list) {
                    this.Insert2GrantList(grant_auth_list)
                }
                this.tableData = this.fomatTableData(all_auth_list, grant_auth_list)
            })()
            .then(()=>{
            })
            .catch((errMsg)=>{
                this.$store.commit('show_global_alert',("错误"+errMsg))
            })         
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