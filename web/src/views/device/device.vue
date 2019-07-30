<template>
  <div class="app-container">
    <div class="filter-container">
      <el-row type="flex" justify="space-between">
        <el-col :span="24">
          <el-form>
            <el-form-item>
              <div style="font-size: 24px">设备管理</div>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="19">
          <el-col :span="9">
            <el-select v-model="selectProduct" placeholder="请选择" @change='selectOk'>
              <el-option v-for="item in options" :key="item.productKey" :label="item.productName" :value="item.productKey">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <div class="big-title">设备总数：</div>
            <span class="big">{{statusData.deviceCount}}</span>
          </el-col>
          <el-col :span="5">
            <div class="big-title">激活设备：</div>
            <span class="big">{{statusData.activeCount}}</span>
          </el-col>
          <el-col :span="5">
            <div class="big-title">当前在线：</div>
            <span class="big">{{statusData.onlineCount}}</span>
          </el-col>

        </el-col>
        <el-col :span="5" class="right-btn">
          <el-form>
            <el-form-item>
              <el-button icon="el-icon-refresh" v-if="hasPerm('device:list')" @click="refreshData('刷新')">刷新</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-tabs v-model="activeName" :stretch=true>
        <el-tab-pane label="设备列表" name="first"></el-tab-pane>
      </el-tabs>
    </div>

    <el-row>
      <el-col :span="24">
        <el-col :span="12">
          <h3 style="line-height: 50px;">设备列表</h3>
        </el-col>
        <el-col :span="12" class="right-btn">
          <el-button type="primary" style="margin-top: 20px;" v-if="hasPerm('device:add')" @click="addDevice">添加设备</el-button>
        </el-col>

      </el-col>
      <el-col :span="24">
        <!-- 列表 -->
        <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中..." fit highlight-current-row>
          <el-table-column align="center" label="序号" width="80">
            <template slot-scope="scope">
              <span v-text="getIndex(scope.$index)"></span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="设备名称" prop="deviceName"></el-table-column>
          <el-table-column align="center" label="设备所属产品" :formatter="isProduct" width="150"></el-table-column>
          <el-table-column align="center" label="设备状态" width="150">
            <template slot-scope="scope">
              <el-tag :type="deviceStatus[scope.row.deviceStatus].type">{{deviceStatus[scope.row.deviceStatus].status}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="创建时间" :formatter="gMTToStr"></el-table-column>
          <el-table-column align="center" label="操作" width="200" v-if="hasPerm('device:detail') ||hasPerm('device:delete') ">
            <template slot-scope="scope">
              <div v-if="scope.row.roleName!='管理员'">
                <el-button circle type="primary" icon="el-icon-view" @click="getDeviceDetail(scope.$index)" v-if="hasPerm('device:detail')"></el-button>
                <el-button circle v-if="hasPerm('device:delete')" type="danger" icon="el-icon-delete" @click="removeDevice(scope.$index)"></el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 创建产品 -->
    <el-dialog :title="'新建设备'" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="device" label-position="left" label-width="100px" style='width: 600px; margin-left:50px;'>
        <el-form-item label="产品" required>
          <el-select v-model="device.productKey" placeholder="请选择">
            <el-option v-for="item in addForProduct" :key="item.productKey" :label="item.productName" :value="item.productKey">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" required>
          <el-input placeholder="不填写则系统自动生成" type="text" v-model="device.deviceName" style="width: 250px;"></el-input>
          <el-popover placement="top-start" width="200" trigger="hover" content="支持英文字母、数字和特殊字符-_@.:，长度限制4~32">
            <i style="font-size: 20px; color: #999;" slot="reference" class="el-icon-question"></i>
          </el-popover>
        </el-form-item>
        <el-form-item label="备注名称">
          <el-input type="text" v-model="device.nickname" style="width: 250px;"></el-input>
          <el-popover placement="top-start" width="200" trigger="hover" content="支持中文、英文字母、数字和下划线，4~32个字符，中文算两个">
            <i style="font-size: 20px; color: #999;" slot="reference" class="el-icon-question"></i>
          </el-popover>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="success" @click="createDevice">创 建</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllDeviceStatus, queryDeviceByProduct, getDeviceList, addDevice, deleteDevice } from '@/api/device'
import { getProductList } from '@/api/product'
import { parseTime, GMTToStr } from '@/utils'
export default {
  data () {
    return {
      data: {},
      statusData: {},
      listLoading: false,
      options: [
        {
          productKey: 'null',
          productName: '全部产品'
        }
      ],
      list: [],
      selectProduct: 'null',
      activeName: 'first',
      pageNum: 1,
      pageSize: 10,
      dialogFormVisible: false,
      device: {
        productKey: '',
        deviceName: '',
        nickname: ''
      },
      addForProduct: [],
      elTagType: '',
      deviceStatus: {
        'ONLINE': { 'status': '在线', 'type': '' },
        'OFFLINE': { 'status': '离线', 'type': 'danger' },
        'UNACTIVE': { 'status': '未激活', 'type': 'warning' },
        'DISABLE': { 'status': '已禁用', 'type': 'info' }
      }
    }
  },

  mounted () {
    this.getProductList()
    this.getAllDeviceStatus()
    this.getDeviceList()
  },

  methods: {
    /**
     * 设置产品序号
     */
    getIndex ($index) {
      // 表格序号
      return $index + 1
    },

    /**
     * 日期时间格式化函数
     */
    formateTime (val) {
      return parseTime(val.gmtCreate)
    },
    gMTToStr (val) {
      return GMTToStr(val.gmtCreate)
    },

    /**
     * 获取产品列表，装载进select
     */
    getProductList () {
      getProductList(1, 100).then(res => {
        this.options.splice(1, this.options.length - 1)
        for (let i = 0; i < res.data.list.length; i++) {
          this.options.push(res.data.list[i])
        }
      })
    },

    /**
     * select事件
     */
    selectOk (val) {
      if (val === 'null') {
        this.getAllDeviceStatus()
        this.getDeviceList()
      } else {
        this.getAllDeviceStatus(val)
        this.queryDeviceByProduct(val, this.pageNum, this.pageSize)
      }
    },

    /**
     * 获取设备状态
     */
    getAllDeviceStatus (productKey) {
      getAllDeviceStatus(productKey).then(res => {
        this.statusData = res.data
      })
    },
    /**
     * 获取全部设备列表
     */
    getDeviceList () {
      this.listLoading = true
      getDeviceList(this.pageNum, this.pageSize).then(res => {
        this.list = res.data
        this.listLoading = false
      })
    },

    /**
     * 查询指定产品设备列表
     */
    queryDeviceByProduct (productKey, pageNum, pageSize) {
      this.listLoading = true
      queryDeviceByProduct(productKey, pageNum, pageSize).then(res => {
        this.list = res.data
        this.listLoading = false
      })
    },

    /**
     * 查看设备详情
     */
    getDeviceDetail ($index) {
      let device = this.list[$index]
      this.$router.push({ path: 'device/detail/' + device.iotId })
    },

    /**
     * 添加设备按钮打开对话框
     */
    addDevice () {
      this.dialogFormVisible = true
      this.selectProductForAddDevice()
    },

    /**
     * 添加设备时获取产品列表
     */
    selectProductForAddDevice () {
      getProductList(1, 100).then(res => {
        this.addForProduct = res.data.list
      })
    },

    /**
     * 确认添加设备
     */
    createDevice () {
      addDevice(this.device).then(res => {
        this.dialogFormVisible = false
        this.refreshData()
      })
    },
    /**
     * 删除设备
     */
    removeDevice ($index) {
      let device = this.list[$index]
      this.$confirm('此操作将永久删除设备且不可恢复, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
        showClose: false,
        callback: action => {
          if (action === 'confirm') {
            this.deleteDevice(device.iotId)
          }
        }
      })
    },

    /**
     * 确删除设备
     */
    deleteDevice (iotId) {
      deleteDevice(iotId).then(res => {
        this.refreshData()
      })
    },
    /**
     * 格式化设备所属产品
     */
    isProduct (val) {
      for (let i = 0; i < this.options.length; i++) {
        if (val.productKey === this.options[i].productKey) {
          return this.options[i].productName
        }
      }
    },
    /**
     * 刷新数据
     */
    refreshData () {
      this.listLoading = true
      setTimeout(() => {
        this.getProductList()
        this.getDeviceList()
        this.getAllDeviceStatus()
      }, 3000)
    }
  }
}
</script>

<style lang="css">
/**
* 分页器居中
*/
.el-pagination {
  text-align: center;
  margin: 20px 0;
}

.right-btn {
  text-align: right;
}

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
.big-title {
  color: #999;
}
.big {
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #373d41;
}
.right-btn {
  text-align: right;
}
</style>
