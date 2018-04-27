<template>
  <div class="container">
    <head-guild :positions='["授权管理","用户权限"]'></head-guild>
       <label for="platformId" class="pull-left"><sup></sup>平台：</label>
       <v-selection 
            :vStyle="inputStyle"
            :selections="selections"
            :defaultValue="this.platformList.value"
            @on-change="modalSelectionChange">
        </v-selection>     
    <v-table :columns="tableColumns" :rows="tableRows">
    </v-table>
  
    <v-pagenation :total="total" :display="display" :current="current" @pagechange="pagechange" @pageSizeChange="pageSizeChange">
    </v-pagenation>

    <div class="btnBox clearfix">
        <v-button type="default" class="pull-left btn" :isBigBtn="true" text="返回" @click="goBack"></v-button>
    </div>             
  </div>

  <div class="btnBox clearfix">
      <v-button type="default" class="pull-left btn" :isBigBtn="true" text="返回" @click="goBack"></v-button>
  </div>  
</template>

<script>
export default {
  components: {
  },
  data() {
    return {
      tableColumns: [
        // { "label": "权限ID", "id": "authId" },
        { "label": "权限名称", "id": "authName" },
        { "label": "父权限名称", "id": "authFName" },
        { "label": "路径", "id": "url" },
        { "label": "系统名称", "id": "systemName" },
        { "label": "描述", "id": "description" },
        { "label": "创建者", "id": "createUserName" },
        { "label": "更新时间", "id": "updateTime" },
        { "label": "创建时间", "id": "createTime" },
        { "label": "操作", "id": "operation" }
      ],
      tableRows: [],
      platformList: [],
      total: 0,
      display: 0,
      current: 1
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
  methods: {
    modalSelectionChange(data){
        if(!data.value){
            this.characterTip = false
            this.getTableData(null, 1, 10)
            return
        }
        this.characterTipText = data.label
        this.characterTip = true

        this.getTableData(data.value, 1, 10)
    },   
    //返回
    goBack(){
        this.$router.push({path:"/userMng"})        
    },     
    pagechange(pageNum) {
      this.getTableData(pageNum, this.display)
    },

    pageSizeChange(pageSize) {
      this.getTableData(1, pageSize)
    },

    //获取列表数据
    getTableData(platformId, pageNum = 1, pageSize = 10) {
      this.apis.getUserAuthorityList(this.userId, platformId, pageNum, pageSize)
        .then((data) => {
          if(data.totalNum==0){
            this.tableRows = []
            return
          }
          this.tableRows = data.data.map((v, i) => {
            v.number = i + 1
            return v
          })
          this.total = data.totalNum
          this.current = data.pageNum
          this.display = data.pageSize
        })
        .catch((errMsg) => {
          this.$store.commit('show_global_alert',("错误： " + errMsg))
        })
    }
  },
  mounted() {
    this['userId']=this.utils.getUrlParam('userId')
    this.apis.getPlatformList(1, 10000).then((data)=>{
        this.platformList=data.data
    })  
    this.getTableData(null, 1, 10)   
  }
}
</script>
<style scoped lang='less'>
.container {
  .addBtn {
    margin-top: 15px;
    margin-bottom: 13px;
  }
}


.editBtn {
  color: #309bff;
}

.removeBtn {
  margin-left: 25px;
  color: #e60012;
}

.resetBtn {
  color: #309bff;
  margin-left: 25px;
}

.resetP {
  height: 16px;
  line-height: 16px;
  font-size: 16px;
  padding-left: 90px;
  margin-bottom: 18px;
}
</style>