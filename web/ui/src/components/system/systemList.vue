<template>
  <div class="container">
    <head-guild :positions='["授权管理","系统管理"]'></head-guild>
    <v-button @click="addSystem" class="addBtn" type="primery" text="+ 添加系统"></v-button>
    <v-table :columns="tableColumns" :rows="tableRows">
      <template slot-scope="props">
        <a href="javascript:;" class="editBtn" @click="edit(props.data)">编辑</a>
        <a href="javascript:;" class="removeBtn" @click="remove(props.data)">删除</a>
      </template>
    </v-table>
  
    <v-pagenation :total="total" :display="display" :current="current" @pagechange="pagechange" @pageSizeChange="pageSizeChange">
    </v-pagenation>
  
    <v-add-dialog :isShow="isShowAddDialog" @dialogSubmited="comfirmAdd" @on-modal-close="isShowAddDialog=false">
    </v-add-dialog>
  
    <v-edit-dialog
              :isShow="isShowEditDialog"
              :defaultData = "curRow"
              @on-modal-close="isShowEditDialog=false"
              @dialogSubmited="confirmUpdate">
      </v-edit-dialog> 
  
    <v-alert :isShow="isShowDeletAlert" @on-alert-close="isShowDeletAlert=false" @on-confirm="confirmRemove" text="确认删除此系统吗？">
    </v-alert>
  </div>
</template>

<script>
import vAddDialog from './systemAdd.vue'
import vEditDialog from './systemUpdate.vue'
export default {
  components: {
    vAddDialog,
    vEditDialog
  },
  data() {
    return {
      tableColumns: [
        // { "label": "系统ID", "id": "systemId" },
        { "label": "系统名称", "id": "systemName" },
        { "label": "描述", "id": "description" },
        { "label": "创建者", "id": "createUserName" },
        { "label": "更新时间", "id": "updateTime" },
        { "label": "创建时间", "id": "createTime" },
        { "label": "操作", "id": "operation" }
      ],
      tableRows: [],
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
    addSystem() {//添加系统
      this.isShowAddDialog = true
    },
    //确认添加
    comfirmAdd(data) {
      if (this.canSubmit) {
        this.canSubmit = false
        this.apis.addSystem(data)
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
        this.apis.updateSystem(data)
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
      this.apis.deleteSystem(this.curRow.systemId)
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
      this.apis.getSystemList(pageNum, pageSize)
        .then((data) => {
          if(data.totalNum==0){
            this.$store.commit('show_global_alert',"没有数据")
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
    this.getTableData()
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