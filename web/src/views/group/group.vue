<template>
  <div class="app-container">
    <div class="filter-container">
      <el-row type="flex" justify="space-between">
        <el-col :span="24">
          <el-form>
            <el-form-item>
              <div style="font-size: 24px">房间管理</div>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-tabs v-model="activeName" :stretch=true>
        <el-tab-pane label="全部房间" name="first"></el-tab-pane>
      </el-tabs>
    </div>

    <el-row>
      <el-col :span="24">
        <el-col :span="12">
          <h3 style="line-height: 50px;">房间列表</h3>
        </el-col>
        <el-col :span="12" class="right-btn">
          <el-button type="success" icon="el-icon-refresh" v-if="hasPerm('group:list')" @click="refreshData('刷新')">刷新</el-button>
          <el-button type="primary" icon="el-icon-plus" v-if="hasPerm('group:add')" @click="addGroup">添加房间</el-button>
        </el-col>
      </el-col>

      <!-- 列表 -->
      <el-table :data="data.data" v-loading.body="listLoading" element-loading-text="拼命加载中..." fit highlight-current-row>
        <el-table-column align="center" label="序号" width="80">
          <template slot-scope="scope">
            <span v-text="getIndex(scope.$index)"> </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="房间名称" prop="groupName"></el-table-column>
        <el-table-column align="center" label="房间ID" width="250">
          <template slot-scope="scope">
            <el-tag v-text="scope.row.groupId"></el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="添加时间" prop="utcCreate" :formatter="formateTime"></el-table-column>
        <el-table-column align="center" label="操作" width="200" v-if="hasPerm('group:detail') ||hasPerm('group:delete') ">
          <template slot-scope="scope">
            <div v-if="scope.row.roleName!='管理员'">
              <el-button circle type="primary" icon="el-icon-view" @click="getProductDetail(scope.$index)" v-if="hasPerm('group:detail')"></el-button>
              <el-button circle v-if="hasPerm('group:delete')" type="danger" icon="el-icon-delete" @click="removeGroup(scope.$index)"></el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="data.currentPage" @prev-click="prevPage" @next-click="nextPage" :page-sizes="[10, 20, 30, 50]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="data.total" :background="true"></el-pagination>
    </el-row>
    <!-- 创建房间 -->
    <el-dialog :title="'新建房间'" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="group" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="房间名称" required>
          <el-input type="text" v-model="group.groupName" style="width: 250px;"></el-input>
          <el-popover placement="top-start" width="200" trigger="hover" content="分组名称支持中文、英文字母、数字和下划线，长度限制4~30，中文算2位">
            <i style="font-size: 20px; color: #999;" slot="reference" class="el-icon-question"></i>
          </el-popover>
        </el-form-item>
        <el-form-item label="房间描述">
          <el-input type="textarea" v-model="group.groupDesc" style="width: 250px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="success" @click="createGroup">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addGroup, deleteGroup, getGroupList } from '@/api/group'
import { utc2beijing } from '@/utils'
export default {
  data () {
    return {
      data: {},
      group: {
        groupName: '',
        groupDesc: ''
      },
      dialogFormVisible: false,
      listLoading: false,
      pageNum: 1,
      pageSize: 10,
      activeName: 'first'
    }
  },

  mounted () {
    this.getGroupList()
  },
  methods: {

    /**
     * @description 查询分组列表
     */
    getGroupList () {
      this.listLoading = true
      setTimeout(() => {
        getGroupList(this.pageNum, this.pageSize).then(res => {
          this.data = res
          this.listLoading = false
        })
      }, 1000)
    },
    /**
     * @description 确认创建房间
     */
    createGroup () {
      addGroup(this.group).then(res => {
        this.refreshData()
      })
    },

    /**
     * @description 创建房间按钮
     */
    addGroup () {
      this.group.groupName = ''
      this.group.groupDesc = ''
      this.dialogFormVisible = true
    },

    /**
     * @description 删除房间
     */
    removeGroup ($index) {
      let group = this.data.data[$index]
      this.$confirm('此操作将永久删除房间且不可恢复, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
        showClose: false,
        callback: action => {
          if (action === 'confirm') {
            this.deleteGroup(group.groupId)
          }
        }
      })
    },
    /**
     * @description 确认删除
     */
    deleteGroup (groupId) {
      deleteGroup(groupId).then(res => {
        this.refreshData()
      })
    },

    /**
     * @description 查看房间详情
     */
    getProductDetail ($index) {
      let group = this.data.data[$index]
      this.$router.push({ path: 'group/detail/' + group.groupId })
    },
    /**
     * 设置产品序号
     */
    getIndex ($index) {
      // 表格序号
      return $index + 1
    },

    /**
     * @description 格式化时间
     */
    formateTime (val) {
      return utc2beijing(val.utcCreate)
    },

    /**
     * @description 刷新按钮
     */
    refreshData () {
      this.dialogFormVisible = false
      this.getGroupList()
    },
    /**
     * 分页器
     */
    handleSizeChange (val) {
      this.pageSize = val
      this.getGroupList()
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getGroupList()
    },
    prevPage () {
      this.pageNum += 1
      this.getGroupList()
    },
    nextPage () {
      this.pageNum -= 1
      this.getGroupList()
    }
  }
}
</script>

<style lang="scss" scoped>
/**
* 分页器居中
*/
.el-pagination {
  text-align: center;
  margin: 20px 0;
}
.right-btn {
  text-align: right;
  margin-top: 20px;
}
</style>
