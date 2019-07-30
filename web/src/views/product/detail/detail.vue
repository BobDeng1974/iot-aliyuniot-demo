<template>
  <div class="detail-container">
    <div class="head-container" v-loading="loading">
      <el-row type="flex" justify="space-between">
        <el-col :span="12">
          <el-form>
            <el-form-item>
              <div style="font-size: 24px">{{detail.productName}}</div>
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
          <div style="font-size: 16px;color: #999">ProductKey：<span style="color: #666">{{detail.productKey}}</span></div>
        </el-col>
        <el-col :span="8">
          <div style="font-size: 16px;color: #999">ProductSecret：<span style="color: #666">{{detail.productSecret}}</span></div>
        </el-col>
        <el-col :span="8">
          <div style="font-size: 16px;color: #999">设备数：<span style="color: #666">{{detail.deviceCount}}</span></div>
        </el-col>
      </el-row>
      <el-tabs v-model="activeName" :stretch=true>
        <el-tab-pane></el-tab-pane>
        <el-tab-pane label="产品信息" name="first"></el-tab-pane>
      </el-tabs>
    </div>

    <div class="tab-panle" v-loading="loading">
      <el-row>
        <el-col :span="12">
          <h3 style="line-height: 50px;">产品信息</h3>
        </el-col>
        <el-col :span="12" class="right-btn">
          <el-button v-if="hasPerm('product:update')" type="primary" @click="editProductDetail">编辑</el-button>
        </el-col>
      </el-row>
      <div class="product-info">
        <el-row>
          <el-col :span="3" class="border-right">产品名称</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.productName}}</el-col>
          <el-col :span="3" class="border-right padding-left">节点类型</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.nodeType === 0 ? '设备':'网关'}}</el-col>
          <el-col :span="3" class="border-right padding-left">创建时间</el-col>
          <el-col :span="5" class="padding-left">{{formateTime(detail.gmtCreate)}}</el-col>
        </el-row>
        <el-row v-if="!detail.categoryName == ''">
          <el-col :span="3" class="border-right">所属分类</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.categoryName}}</el-col>
          <el-col :span="3" class="border-right padding-left">数据格式</el-col>
          <el-col :span="13" class="padding-left">{{detail.dataFormat === 0 ? '透传模式':'Alink JSON'}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="3" class="border-right">动态注册</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.id2 === 0 ? '已开启':'已关闭'}}</el-col>
          <el-col :span="3" class="border-right padding-left">ProductSecret</el-col>
          <el-col :span="13" class="padding-left">{{detail.productSecret}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="3" class="border-right">状态</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.productStatus === 'DEVELOPMENT_STATUS' ? '开发中':'产品已发布'}}</el-col>
          <el-col :span="3" class="border-right padding-left">是否接入网关</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.nodeType === 0 ? '设备':'网关'}}</el-col>
          <el-col :span="3" class="border-right padding-left">联网协议</el-col>
          <el-col :span="5" class="padding-left">{{netType()}}</el-col>
        </el-row>
        <el-row style="border-bottom: 1px solid #ebecec">
          <el-col :span="3" class="border-right">产品描述</el-col>
          <el-col :span="21" class="padding-left">{{detail.description}}</el-col>
        </el-row>
      </div>
    </div>

    <!-- 更新产品 -->
    <el-dialog :title="'编辑产品信息'" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="productDetail" label-position="left" label-width="100px" style='width: 600px; margin-left:50px;'>
        <el-form-item label="产品名称" required>
          <el-input type="text" v-model="productDetail.productName" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="产品描述">
          <el-input type="textarea" v-model="productDetail.description" style="width: 250px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="success" @click="updateProduct">确 认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getProductDetail, updateProduct } from '@/api/product'
import { parseTime } from '@/utils'
export default {
  data () {
    return {
      detail: {},
      activeName: 'first',
      loading: false,
      dialogFormVisible: false,
      productDetail: {
        productName: '',
        description: ''
      }
    }
  },

  mounted () {
    this.getProductDetail()
  },

  created () {

  },

  computed: {

  },

  methods: {
    getProductDetail () {
      this.loading = true
      setTimeout(() => {
        getProductDetail(this.$route.params.productKey).then(res => {
          this.detail = res.data
        })
        this.loading = false
      }, 1500)
    },
    formateTime (val) {
      return parseTime(val)
    },
    netType () {
      switch (this.detail.netType) {
        case 3:
          return 'WIFI'
        case 6:
          return 'Cellular (2G/3G/4G)蜂窝网'
        case 7:
          return 'Ethernet 以太网'
        case 8:
          return '其他'
        default:
          break
      }
    },
    editProductDetail () {
      this.productDetail.productName = this.detail.productName
      this.productDetail.description = this.detail.description
      this.dialogFormVisible = true
    },
    updateProduct () {
      updateProduct(this.detail.productKey, this.productDetail).then(res => {
        this.dialogFormVisible = false
        this.getProductDetail()
      })
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
}
</style>
