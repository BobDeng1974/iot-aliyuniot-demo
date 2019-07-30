<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" v-if="hasPerm('permission:add')" @click="addPermission">添加</el-button>
          <el-button type="success" icon="el-icon-refresh" v-if="hasPerm('permission:list')" @click="refreshData('刷新')">刷新</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 列表 -->
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中..." border fit highlight-current-row>
      <el-table-column align="center" label="ID" prop="id" width="80"></el-table-column>
      <el-table-column align="center" label="菜单码" width="120">
        <template slot-scope="scope">
          <el-tag v-text="scope.row.menuCode"></el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="菜单名称" prop="menuName"></el-table-column>
      <el-table-column align="center" label="权限码">
        <template slot-scope="scope">
          <el-tag v-text="scope.row.permissionCode"></el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="权限名称" prop="permissionName"></el-table-column>
      <el-table-column align="center" label="是否必选" prop="requiredPermission" :formatter="isSelect"></el-table-column>
      <el-table-column align="center" label="管理" width="150" v-if="hasPerm('permission:update') ||hasPerm('permission:delete') ">
        <template slot-scope="scope">
          <div v-if="scope.row.roleName!='管理员'">
            <el-button circle type="primary" icon="el-icon-edit" @click="showUpdate(scope.$index)" v-if="hasPerm('permission:update')"></el-button>
            <el-button circle v-if="hasPerm('permission:delete')" type="danger" icon="el-icon-delete" @click="removePermission(scope.$index)"></el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="cursor.offset" @prev-click="prevPage" @next-click="nextPage" :page-sizes="[10, 20, 30, 50]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="cursor.total" :background="true"></el-pagination>

    <!-- 新增权限对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="permission" label-position="left" label-width="100px" style='width: 600px; margin-left:50px;'>
        <el-form-item label="权限ID" v-if="dialogStatus=='create'" required>
          <el-input type="number" v-model.number="permission.id" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="菜单码" required>
          <el-input type="text" v-model="permission.menuCode" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="菜单名称" required>
          <el-input type="text" v-model="permission.menuName" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="权限码" required>
          <el-input type="text" v-model="permission.permissionCode" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="权限名称" required>
          <el-input type="text" v-model="permission.permissionName" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="是否必选" required>
          <el-radio v-model="permission.requiredPermission" label="1">必选</el-radio>
          <el-radio v-model="permission.requiredPermission" label="2">可选</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createPermission">创 建</el-button>
        <el-button type="primary" v-else @click="updatePermission">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getPermissionList, createPermission, updatePermission, deletePermission } from '@/api/permission'
export default {
  data () {
    return {
      list: [],
      cursor: {},
      pageNum: 1,
      pageSize: 10,
      params: {},
      listLoading: false, // 数据加载等待动画
      dialogStatus: 'create',
      dialogFormVisible: false,
      textMap: {
        update: '编辑',
        create: '新建权限'
      },
      permission: {
        id: null,
        menuCode: '',
        menuName: '',
        permissionCode: '',
        permissionName: '',
        requiredPermission: null
      },
      options: [
        {
          value: '1',
          lable: '必选'
        },
        {
          value: '2',
          lable: '可选'
        }
      ]
    }
  },

  mounted () {
    this.getPermissionList()
  },
  methods: {
    getPermissionList () {
      this.listLoading = true
      getPermissionList(this.pageNum, this.pageSize, this.params).then((result) => {
        this.listLoading = false
        this.list = result.data
        this.cursor = result.cursor
      })
    },
    isSelect (val) {
      if (val.requiredPermission === 1) {
        return '是'
      } else if (val.requiredPermission === 2) {
        return '否'
      } else {
        return '未知'
      }
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.getPermissionList()
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getPermissionList()
    },
    prevPage () {
      this.pageNum += 1
      this.getPermissionList()
    },
    nextPage () {
      this.pageNum -= 1
      this.getPermissionList()
    },
    /**
     * 添加权限
     */
    addPermission () {
      this.permission.id = null
      this.permission.menuCode = ''
      this.permission.menuName = ''
      this.permission.permissionCode = ''
      this.permission.permissionName = ''
      this.permission.requiredPermission = null
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    /**
     * 确认创建
     */
    createPermission () {
      createPermission(this.createRequestParams(this.permission)).then((res) => {
        this.refreshData(res.message)
      })
    },

    /**
     * 更新按钮
     */
    updatePermission () {
      updatePermission(this.createRequestParams(this.permission)).then((res) => {
        this.refreshData(res.message)
      })
    },

    /**
     * 拼接请求参数
     */
    createRequestParams (data) {
      let params = {
        id: Number(data.id),
        menuCode: data.menuCode,
        menuName: data.menuName,
        permissionCode: data.permissionCode,
        permissionName: data.permissionName,
        requiredPermission: Number(data.requiredPermission)
      }
      return params
    },

    /**
     * 显示更新对话框
     */
    showUpdate ($index) {
      let permission = this.list[$index]
      this.permission.id = permission.id
      this.permission.menuCode = permission.menuCode
      this.permission.menuName = permission.menuName
      this.permission.permissionCode = permission.permissionCode
      this.permission.permissionName = permission.permissionName
      this.permission.requiredPermission = String(permission.requiredPermission)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },

    /**
     * 删除权限
     */
    removePermission ($index) {
      let that = this
      this.$confirm('此操作将永久删除此权限, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
        showClose: false
      }).then((action) => {
        let permission = that.list[$index]
        that.deletePermission(permission.id).then((res) => {
          that.refreshData(res.message)
        })
      }).catch(() => {

      })
    },

    /**
     * 删除权限
     */
    deletePermission (id) {
      deletePermission(id).then((res) => {
        this.refreshData(res.message)
      })
    },

    /**
     * 创建成功关闭窗口并刷新数据
     */
    refreshData (msg) {
      this.$message({
        message: msg,
        type: 'success',
        center: true
      })
      this.dialogFormVisible = false
      this.getPermissionList()
    }
  }
}
</script>

<style lang="scss" scoped>
.el-pagination {
  text-align: center;
  margin: 20px 0;
}
</style>
