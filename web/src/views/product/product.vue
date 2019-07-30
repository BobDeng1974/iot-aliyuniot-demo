<template>
  <div class="app-container">
    <div class="filter-container">
      <el-row type="flex" justify="space-between">
        <el-col :span="24">
          <el-form>
            <el-form-item>
              <div style="font-size: 24px">产品管理</div>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>

    <el-row>
      <el-col :span="24">
        <el-col :span="12">产品列表</el-col>

        <el-col :span="12" class="right-btn">
          <el-form>
            <el-form-item>
              <el-button type="success" icon="el-icon-refresh" v-if="hasPerm('product:list')" @click="refreshData('刷新')">刷新</el-button>
              <el-button type="primary" icon="el-icon-plus" v-if="hasPerm('product:add')" @click="addProduct">创建产品</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-col>
      <el-col :span="24">
        <!-- 列表 -->
        <el-table :data="data.list" v-loading.body="listLoading" element-loading-text="拼命加载中..." fit highlight-current-row>
          <el-table-column align="center" label="序号" width="80">
            <template slot-scope="scope">
              <span v-text="getIndex(scope.$index)"> </span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="产品名称" prop="productName"></el-table-column>
          <el-table-column align="center" label="ProductKey" width="150">
            <template slot-scope="scope">
              <el-tag v-text="scope.row.productKey"></el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="节点类型" width="150" :formatter="isSelect"></el-table-column>
          <el-table-column align="center" label="添加时间" prop="permissionName" :formatter="formateTime"></el-table-column>
          <el-table-column align="center" label="操作" width="200" v-if="hasPerm('product:detail') ||hasPerm('product:delete') ">
            <template slot-scope="scope">
              <div v-if="scope.row.roleName!='管理员'">
                <el-button circle type="primary" icon="el-icon-view" @click="getProductDetail(scope.$index)" v-if="hasPerm('product:detail')"></el-button>
                <el-button circle v-if="hasPerm('product:delete')" type="danger" icon="el-icon-delete" @click="removeProduct(scope.$index)"></el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="data.currentPage" @prev-click="prevPage" @next-click="nextPage" :page-sizes="[10, 20, 30, 50]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="data.total" :background="true"></el-pagination>

      </el-col>
    </el-row>

    <!-- 创建产品 -->
    <el-dialog :title="'新建产品'" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="product" label-position="left" label-width="100px" style='width: 600px; margin-left:50px;'>
        <el-form-item label="产品名称" required>
          <el-input type="text" v-model="product.productName" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="节点类型" required>
          <el-radio v-model="product.nodeType" label="0">设备</el-radio>
          <el-radio v-model="product.nodeType" label="1">网关</el-radio>
        </el-form-item>
        <el-form-item label="数据格式" required>
          <el-radio v-model="product.dataFormat" label="0">透传/自定义格式</el-radio>
          <el-radio v-model="product.dataFormat" label="1">ICA 标准数据格式 (Alink JSON)</el-radio>
        </el-form-item>
        <el-form-item label="产品描述">
          <el-input type="textarea" v-model="product.description" style="width: 250px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="success" @click="createProduct">创 建</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getProductList, createProduct, deleteProduct } from '@/api/product'
import { parseTime } from '@/utils'
export default {
  data () {
    return {
      listLoading: false,
      dialogFormVisible: false,
      pageNum: 1,
      pageSize: 10,
      data: {},
      product: {
        productName: '',
        nodeType: '',
        dataFormat: '',
        description: ''
      }
    }
  },

  mounted () {
    this.getProductList()
  },

  methods: {
    getProductList () {
      this.listLoading = true
      getProductList(this.pageNum, this.pageSize).then((res) => {
        this.data = res.data
        this.listLoading = false
      })
    },

    /**
     * 添加产品
     */
    addProduct () {
      this.product.productName = ''
      this.product.nodeType = ''
      this.product.dataFormat = ''
      this.product.description = ''
      this.dialogFormVisible = true
    },

    /**
     * 确认创建产品
     */
    createProduct () {
      createProduct(this.createRequestParams(this.product)).then(res => {
        this.refreshData('创建成功')
      })
    },

    /**
     *删除产品
     */
    removeProduct ($index) {
      let that = this
      let product = that.data.list[$index]
      this.$confirm('此操作将永久删除产品且不可恢复, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
        showClose: false,
        callback: action => {
          if (action === 'confirm') {
            this.deleteProduct(product.productKey)
          }
        }
      })
    },

    deleteProduct (productKey) {
      deleteProduct(productKey).then(res => {
        this.refreshData()
      })
    },

    getProductDetail ($index) {
      let that = this
      let product = that.data.list[$index]
      this.$router.push({ path: 'product/detail/' + product.productKey })
      // getProductDetail(product.productKey).then(res => {
      //   console.log(res)
      // })
    },

    /**
     * 设置产品序号
     */
    getIndex ($index) {
      // 表格序号
      return $index + 1
    },

    /**
     * 格式化节点类型
     */
    isSelect (val) {
      if (val.nodeType === 0) {
        return '设备'
      } else if (val.nodeType === 1) {
        return '网关'
      } else {
        return '未知'
      }
    },

    formateTime (val) {
      return parseTime(val.gmtCreate)
    },

    /**
     * 分页器
     */
    handleSizeChange (val) {
      this.pageSize = val
      this.getProductList()
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getProductList()
    },
    prevPage () {
      this.pageNum += 1
      this.getProductList()
    },
    nextPage () {
      this.pageNum -= 1
      this.getProductList()
    },

    /**
     * 拼接请求参数
     */
    createRequestParams (data) {
      let params = {
        productName: data.productName,
        nodeType: Number(data.nodeType),
        dataFormat: Number(data.dataFormat),
        description: data.description
      }
      return params
    },

    /**
     * 刷新数据
     */
    refreshData () {
      this.dialogFormVisible = false
      setTimeout(() => {
        this.getProductList()
      }, 1500)
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
}
</style>
