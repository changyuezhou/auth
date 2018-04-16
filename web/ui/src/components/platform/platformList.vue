<template>
  <div class="container">
    <head-guild :positions='["授权管理","平台管理"]'></head-guild>
    <v-button @click="addPlatform" class="addBtn" type="primery" text="+ 添加平台"></v-button>
    <v-table :columns="tableColumns" :rows="tableRows">
      <template slot-scope="props">
        <a href="javascript:;" class="editBtn" @click="edit(props.data)">编辑</a>
        <a href="javascript:;" class="removeBtn" @click="remove(props.data)">删除</a>
      </template>
    </v-table>
  
    <v-pagenation :total="total" :display="display" :current="current" @pagechange="pagechange" @pageSizeChange="pageSizeChange">
    </v-pagenation>
  
    <v-add-dialog :isShow="isShowAddDialog" :systemList ="systemList" @dialogSubmited="comfirmAdd" @on-modal-close="isShowAddDialog=false">
    </v-add-dialog>
  
    <v-edit-dialog
              :isShow="isShowEditDialog"
              :systemList ="systemList"
              :defaultData = "curRow"
              @on-modal-close="isShowEditDialog=false"
              @dialogSubmited="confirmUpdate">
      </v-edit-dialog> 
  
    <v-alert :isShow="isShowDeletAlert" @on-alert-close="isShowDeletAlert=false" @on-confirm="confirmRemove" text="确认删除此平台吗？">
    </v-alert>
  </div>
</template>

<script>
import vAddDialog from './platformAdd.vue'
import vEditDialog from './platformUpdate.vue'
export default {
  components: {
    vAddDialog,
    vEditDialog
  },
  data() {
    return {
      tableColumns: [
        { "label": "平台ID", "id": "platformId" },
        { "label": "平台名称", "id": "platformName" },
        { "label": "密钥", "id": "secretKey" },
        { "label": "域名", "id": "platformDomain" },
        { "label": "描述", "id": "description" },
        { "label": "系统名称", "id": "systemName" },
        { "label": "创建者", "id": "createUserName" },
        { "label": "更新时间", "id": "updateTime" },
        { "label": "创建时间", "id": "createTime" },
        { "label": "操作", "id": "operation" }
      ],
      tableRows: [],
      systemList:[],
      total: 0,
      display: 0,
      current: 1,
      curRow: null,
      isShowAddDialog: false,
      isShowEditDialog: false,
      isShowDeletAlert: false,
      canSubmit: true,
    }
  },
  methods: {
    addPlatform() {//添加平台
      this.isShowAddDialog = true
    },
    //确认添加
    comfirmAdd(data) {
      if (this.canSubmit) {
        this.canSubmit = false
        this.apis.addPlatform(data)
          .then((res) => {
            this.isShowAddDialog = false
            this.getTableData()
            this.$store.dispatch('showToast',"添加成功")
            this.canSubmit = true
          })
          .catch((errMsg)=>{
            this.$store.commit('show_global_alert',("添加失败： "+errMsg))
            this.canSubmit = true
          })
      } else { return }
    },

    edit(data) {//编辑账号
      this.curRow = data
      this.isShowEditDialog = true
    },
    //确认修改
    confirmUpdate(data) {
      if(this.canSubmit){
        this.canSubmit = false
        this.apis.updatePlatform(data)
          .then((res)=>{
            this.isShowEditDialog = false
            this.getTableData(this.current,this.display)
            this.$store.dispatch('showToast',"修改成功")
            this.canSubmit = true
          })
          .catch((errMsg)=>{
            this.$store.commit('show_global_alert',("修改失败： "+errMsg))
            this.canSubmit = true
          })
      }else{
        return
      }
    },

    remove(data) {//删除系统
      this.curRow = data
      this.isShowDeletAlert = true
    },
    //确认删除
    confirmRemove() {
      this.isShowDeletAlert = false
      this.apis.deletePlatform(this.curRow.platformId)
        .then((res) => {
          this.$store.dispatch('showToast',"删除成功")
          this.getTableData(this.current, this.display)
        })
        .catch((errMsg) => {
          this.$store.commit('show_global_alert',("删除失败： " + errMsg))
        })
    },

    pagechange(pageNum) {
      this.getTableData(pageNum, this.display)
    },

    pageSizeChange(pageSize) {
      this.getTableData(1, pageSize)
    },

    //获取列表数据
    getTableData(pageNum = 1, pageSize = 10) {
      this.apis.getPlatformList(pageNum, pageSize)
        .then((data) => {
          if(data.data.total_number==0){
            this.$store.commit('show_global_alert',"没有数据")
            return
          }
          this.tableRows = data.data.list.map((v, i) => {
            v.number = i + 1
            return v
          })
          this.total = data.data.total_number
          this.current = data.data.page_number
          this.display = data.data.page_size
        })
        .catch((errMsg) => {
          this.$store.commit('show_global_alert',("错误： " + errMsg))
        })
    }

  },
  mounted() {
    this.getTableData()
    this.apis.getSystemList().then((data)=>{
      this.systemList=data.data.list
    })
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