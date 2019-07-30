<template>
  <div class="detail-container">
    <div class="head-container" v-loading="loading" element-loading-text="拼命加载中...">
      <el-row type="flex" justify="space-between">
        <el-col :span="12">
          <el-form>
            <el-form-item>
              <div style="font-size: 24px">{{detail.groupName}}</div>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12" class="right-btn">
          <el-form>
            <el-form-item>
              <el-button type="primary" @click="$router.back(-1)">返回</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row class="detail-info">
        <el-col :span="8">
          <div style="font-size: 16px;color: #999">设备总数：<span style="color: #666">{{detail.deviceCount}}</span></div>
        </el-col>
        <el-col :span="8">
          <div style="font-size: 16px;color: #999">激活设备：<span style="color: #666">{{detail.deviceActive}}</span></div>
        </el-col>
        <el-col :span="8">
          <div style="font-size: 16px;color: #999">当前在线：<span style="color: #666">{{detail.deviceOnline}}</span></div>
        </el-col>
      </el-row>

      <el-tabs v-model="activeName" :stretch=true @tab-click="tabHandleClick">
        <el-tab-pane label="房间信息" name="group"></el-tab-pane>
        <el-tab-pane v-if="hasPerm('group:deviceList')" label="设备列表" name="device"></el-tab-pane>
      </el-tabs>
    </div>

    <!-- 房间信息 -->
    <div class="tab-panle" v-if="activeName === 'group'" v-loading="loading" element-loading-text="拼命加载中...">
      <el-row>
        <el-col :span="12">
          <h3 style="line-height: 50px;">房间信息</h3>
        </el-col>
        <el-col :span="12" class="right-btn">
          <el-button v-if="hasPerm('group:update')" type="primary" @click="editGroupDetail">编辑</el-button>
        </el-col>
      </el-row>
      <div class="product-info">
        <el-row>
          <el-col :span="3" class="border-right">房间名称</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.groupName}}</el-col>
          <el-col :span="3" class="border-right padding-left">分组ID</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.groupId}}</el-col>
          <el-col :span="3" class="border-right padding-left">创建时间</el-col>
          <el-col :span="5" class="padding-left">{{formateTime()}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="3" class="border-right">设备总数</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.deviceCount}}</el-col>
          <el-col :span="3" class="border-right padding-left">激活设备</el-col>
          <el-col :span="5" class="padding-left">{{detail.deviceActive}}</el-col>
          <el-col :span="3" class="border-right padding-left">当前在线</el-col>
          <el-col :span="5" class="padding-left">{{detail.deviceOnline}}</el-col>
        </el-row>
        <el-row style="border-bottom: 1px solid #ebecec">
          <el-col :span="3" class="border-right">房间描述</el-col>
          <el-col :span="21" class="padding-left">{{detail.groupDesc}}</el-col>
        </el-row>
      </div>
    </div>

    <!-- 设备列表 -->
    <div class="tab-panle" v-if="activeName === 'device'">
      <el-row>
        <el-col :span="12">
          <h3 style="line-height: 50px;">设备列表</h3>
        </el-col>
        <el-col :span="12" class="right-btn">
          <el-button v-if="hasPerm('group:deviceList')" @click="refreshDeviceList">刷新</el-button>
          <el-button type="primary" v-if="hasPerm('group:batchAddDevice')" @click="addDeviceToGroup">添加设备到房间</el-button>
        </el-col>
      </el-row>
      <div class="product-info">
        <!-- 列表 -->
        <el-table :data="deviceData.data" v-loading.body="listLoading" element-loading-text="拼命加载中..." fit highlight-current-row>
          <el-table-column align="center" label="序号" width="80">
            <template slot-scope="scope">
              <span v-text="getIndex(scope.$index)"> </span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="设备名称" prop="deviceName"></el-table-column>
          <el-table-column align="center" label="设备所属产品" prop="productName" width="250"></el-table-column>
          <el-table-column align="center" label="操作" width="200" v-if="hasPerm('device:detail') ||hasPerm('group:batchDeleteDevice') ">
            <template slot-scope="scope">
              <div v-if="scope.row.roleName!='管理员'">
                <el-button circle type="primary" icon="el-icon-view" @click="getDeviceDetail(scope.$index)" v-if="hasPerm('device:detail')"></el-button>
                <el-button circle v-if="hasPerm('group:batchDeleteDevice')" type="danger" icon="el-icon-delete" @click="removeDeviceToGroup(scope.$index)"></el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="deviceData.page" @prev-click="prevPage" @next-click="nextPage" :page-sizes="[10, 20, 30, 50]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="deviceData.total" :background="true"></el-pagination>

      </div>
    </div>

    <!-- 更新房间 -->
    <el-dialog :title="'编辑房间信息'" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="detail" label-position="left" label-width="100px" style='width: 600px; margin-left:50px;'>
        <el-form-item label="房间名称" required>
          <el-input type="text" v-model="detail.groupName" :disabled="true" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="房间描述">
          <el-input type="textarea" v-model="detail.groupDesc" style="width: 250px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="success" v-if="hasPerm('group:batchAddDevice')" @click="updateGroup">确 认</el-button>
      </div>
    </el-dialog>

    <!-- 添加设备到房间 -->
    <el-dialog :title="'添加设备到房间'" :visible.sync="addDeviceDidlog">
      <!-- 列表 -->
      <el-table @selection-change="handleSelectionChange" :data="deviceList" v-loading="addDeviceLoading" element-loading-text="拼命加载中..." fit highlight-current-row>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="left" label="设备名称" prop="deviceName"></el-table-column>
        <el-table-column align="left" label="设备所属产品" :formatter="isProduct" width="250"></el-table-column>
        <el-table-column align="left" label="设备状态" width="150">
          <template slot-scope="scope">
            <el-tag :type="deviceStatus[scope.row.deviceStatus].type">{{deviceStatus[scope.row.deviceStatus].status}}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDeviceDidlog = false">取 消</el-button>
        <el-button type="success" @click="batchAddDeviceToGroup">确 认</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getGroupDetail, updateGroup, getDeviceListByGroup, batchDeleteDeviceToGroup, batchAddDeviceToGroup } from '@/api/group'
import { getDeviceList } from '@/api/device'
import { getProductList } from '@/api/product'
import { utc2beijing } from '@/utils'
export default {
  data () {
    return {
      detail: {},
      deviceData: {},
      activeName: 'group',
      dialogFormVisible: false,
      addDeviceDidlog: false,
      listLoading: false,
      addDeviceLoading: false,
      loading: false,
      pageNum: 1,
      pageSize: 10,
      deviceList: [],
      productList: [],
      addDeviceList: [],
      deviceStatus: {
        'ONLINE': { 'status': '在线', 'type': '' },
        'OFFLINE': { 'status': '离线', 'type': 'danger' },
        'UNACTIVE': { 'status': '未激活', 'type': 'warning' },
        'DISABLE': { 'status': '已禁用', 'type': 'info' }
      }
    }
  },

  mounted () {
    this.getGroupDetail()
  },

  created () {

  },

  computed: {

  },

  methods: {
    /**
     * @description 查询产品详情
     */
    getGroupDetail () {
      this.loading = true
      setTimeout(() => {
        getGroupDetail(this.$route.params.groupId).then(res => {
          this.detail = res.data
          this.loading = false
        })
      }, 2000)
    },
    /**
     * @description 格式化UTC时间
     */
    formateTime () {
      return utc2beijing(this.detail.utcCreate)
    },
    /**
     * @description 编辑分组
     */
    editGroupDetail () {
      this.dialogFormVisible = true
    },
    /**
     * @description 确认更新分组
     */
    updateGroup () {
      updateGroup(this.detail.groupId, this.detail).then(res => {
        this.dialogFormVisible = false
        this.getGroupDetail()
      })
    },

    /**
     * @description 查询房间的设备
     */
    getDeviceListByGroup () {
      this.listLoading = true
      setTimeout(() => {
        getDeviceListByGroup(this.detail.groupId, this.pageNum, this.pageSize).then(res => {
          this.deviceData = res
          this.listLoading = false
        })
      }, 1500)
    },

    /**
     * @description Tab切换事件
     */
    tabHandleClick (tab, event) {
      if (tab.name === 'group') {
        this.getGroupDetail()
      }
      if (tab.name === 'device') {
        this.getDeviceListByGroup()
      }
    },

    /**
     * @description 刷新设备列表
     */
    refreshDeviceList () {
      setTimeout(() => {
        this.getDeviceListByGroup()
        this.getGroupDetail()
      }, 1500)
    },

    /**
     * @description 绑定设备到房间
     */
    addDeviceToGroup () {
      this.deviceList = []
      this.addDeviceDidlog = true
      this.addDeviceLoading = true
      setTimeout(() => {
        getProductList(1, 100).then(res => {
          this.productList = res.data.list
        })
        getDeviceList(1, 100).then(res => {
          this.deviceList = res.data
        })
        this.addDeviceLoading = false
      }, 2000)
    },

    handleSelectionChange (val) {
      this.addDeviceList = val
    },

    /**
     * @description 确认添加设备到房间按钮
     */
    batchAddDeviceToGroup () {
      batchAddDeviceToGroup(this.detail.groupId, this.addDeviceList).then(res => {
        this.addDeviceDidlog = false
        this.refreshDeviceList()
      })
    },

    /**
     * 格式化设备所属产品
     */
    isProduct (val) {
      for (let i = 0; i < this.deviceList.length; i++) {
        if (val.productKey === this.productList[i].productKey) {
          return this.productList[i].productName
        }
      }
    },

    /**
     * 删除设备
     */
    removeDeviceToGroup ($index) {
      let device = this.deviceData.data[$index]
      let params = [{ productKey: device.productKey, deviceName: device.deviceName }]
      this.$confirm('此操作将从分组移除设备, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
        showClose: false,
        callback: action => {
          if (action === 'confirm') {
            this.deleteDeviceToGroup(params)
          }
        }
      })
    },
    /**
     * @description 从房间移除设备
     */
    deleteDeviceToGroup (params) {
      batchDeleteDeviceToGroup(this.detail.groupId, params).then(res => {
        this.refreshDeviceList()
      })
    },

    getDeviceDetail ($index) {
      let device = this.deviceData.data[$index]
      this.$router.push({ path: '/device/detail/' + device.iotId })
    },

    /**
     * 设置产品序号
     */
    getIndex ($index) {
      // 表格序号
      return $index + 1
    },

    /**
     * 分页器
     */
    handleSizeChange (val) {
      this.pageSize = val
      this.getDeviceListByGroup()
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getDeviceListByGroup()
    },
    prevPage () {
      this.pageNum += 1
      this.getDeviceListByGroup()
    },
    nextPage () {
      this.pageNum -= 1
      this.getDeviceListByGroup()
    }

  }

}
</script>

<style lang="scss">
.el-tabs__item {
  font-size: 16px !important;
  color: #999 !important;
  height: 50px;
  line-height: 50px;
}
.el-tabs__item.is-active {
  color: #409eff !important;
  font-size: 16px !important;
}
.el-tabs__nav-wrap::after {
  height: 1px;
}
.detail-container {
  padding: 20px;
  background: #f2f3f5;
}
.head-container {
  background: #ffffff;
  margin-left: -20px;
  margin-right: -20px;
  margin-top: -20px;
  padding: 20px;
}
.detail-info {
  margin-bottom: 20px;
}
.tab-panle {
  background: #ffffff;
  margin-top: 20px;
  padding: 30px 20px;
}
.right-btn {
  text-align: right;
  margin-top: 20px;
}
.product-info .el-row {
  line-height: 50px;
  border-top: 1px solid #ebecec;
  border-left: 1px solid #ebecec;
  border-right: 1px solid #ebecec;
  color: #74777a;
  padding-left: 20px;
}
.border-right {
  border-right: 1px solid #ebecec;
}
.padding-left {
  padding-left: 20px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
/**
* 分页器居中
*/
.el-pagination {
  text-align: center;
  margin: 20px 0;
}
</style>
