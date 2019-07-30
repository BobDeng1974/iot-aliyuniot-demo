<template>
  <div class="detail-container">
    <div class="head-container" v-loading="loading">
      <el-row type="flex" justify="space-between">
        <el-col :span="12">
          <el-form>
            <el-form-item>
              <div style="font-size: 24px">{{detail.deviceName}}</div>
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
        <el-col :span="7">
          <div style="font-size: 16px;color: #999">产品：<span style="color: #666">{{detail.productName}}</span></div>
        </el-col>
        <el-col :span="7">
          <div style="font-size: 16px;color: #999">ProductKey：<span style="color: #666">{{detail.productKey}}</span></div>
        </el-col>
        <el-col :span="10">
          <div style="font-size: 16px;color: #999; overflow: hidden">DeviceSecret：<span style="color: #666">{{detail.deviceSecret}}</span></div>
        </el-col>
      </el-row>
      <el-tabs v-model="activeName" :stretch=true>
        <el-tab-pane label="设备信息" name="first"></el-tab-pane>
        <el-tab-pane label="设备数据" name="two"></el-tab-pane>
      </el-tabs>
    </div>

    <div v-if="activeName === 'first'" class="tab-panle" v-loading="loading">
      <el-row>
        <el-col :span="12">
          <h3 style="margin-bottom: 30px;">设备信息</h3>
        </el-col>
      </el-row>
      <div class="product-info">
        <el-row>
          <el-col :span="3" class="border-right">设备名称</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.productName}}</el-col>
          <el-col :span="3" class="border-right padding-left">ProductKey</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.productKey}}</el-col>
          <el-col :span="3" class="border-right padding-left">区域</el-col>
          <el-col :span="5" class="padding-left">{{deviceAddress[detail.region]}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="3" class="border-right">节点类型</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.nodeType === 0 ? '设备':'网关'}}</el-col>
          <el-col :span="3" class="border-right padding-left">DeviceName</el-col>
          <el-col :span="5" class="padding-left">{{detail.deviceName}}</el-col>
          <el-col :span="3" class="border-right padding-left">DeviceSecret</el-col>
          <el-col :span="5" class="padding-left">{{detail.deviceSecret}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="3" class="border-right">备注名称</el-col>
          <el-col :span="5" class="border-right padding-left">{{detail.nickname ===undefined || detail.nickname ==='' || detail.nickname ==='null' ? '-':detail.nickname}}</el-col>
          <el-col :span="3" class="border-right padding-left">IP地址</el-col>
          <el-col :span="5" class="padding-left">{{detail.ipAddress === undefined || detail.ipAddress ==='' || detail.ipAddress ==='null' ? '-':detail.ipAddress}}</el-col>
          <el-col :span="3" class="border-right padding-left">固件版本</el-col>
          <el-col :span="5" class="padding-left">{{detail.firmwareVersion === undefined || detail.firmwareVersion ==='' || detail.firmwareVersion ==='null' ? '-':detail.firmwareVersion}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="3" class="border-right">添加时间</el-col>
          <el-col :span="5" class="border-right padding-left">{{formateTime(detail.gmtCreate)}}</el-col>
          <el-col :span="3" class="border-right padding-left">激活时间</el-col>
          <el-col :span="5" class="border-right padding-left">{{formateTime(detail.gmtActive)}}</el-col>
          <el-col :span="3" class="border-right padding-left">最后上线时间</el-col>
          <el-col :span="5" class="padding-left">{{formateTime(detail.gmtOnline)}}</el-col>
        </el-row>
        <el-row style="border-bottom: 1px solid #ebecec">
          <el-col :span="3" class="border-right">当前状态</el-col>
          <el-col :span="21" class="padding-left">{{detailStatus()}}</el-col>
        </el-row>
      </div>
    </div>

    <div v-else class="tab-panle" v-loading="loading">
      <el-row>
        <el-col :span="12">
          <h3 style="margin-bottom: 30px;">设备数据</h3>
        </el-col>
      </el-row>
      <div class="product-info">
        <div class="alarm-editor-container">
          <div class="system-panel">
            <el-row :gutter="40" class="system-content">
              <gauge :height="'350px'" :title="'温度'" :min="-10" :tips="'环境温度(实时)'" :data="nodeMcu.mtemp"></gauge>
              <gauge :height="'350px'" :title="'湿度'" :tag="'%'" :tips="'环境湿度(实时)'" :data="nodeMcu.mhumi"></gauge>
              <gauge :height="'350px'" :title="'光照度'" :tag="'Lx'" :max="65500" :tips="'环境亮度(实时)'" :data="nodeMcu.mlux"></gauge>
            </el-row>
          </div>
        </div>

        <div class="alarm-editor-container">
          <div class="system-panel">
            <el-row :gutter="40" class="system-content">
              <ecLine :title="'实时心率监测'" :height="'350px'" :data="wemos.mhumi" :max="2000"></ecLine>
              <ecLine :title="'人体实时温度'" :height="'350px'" :data="wemos.animalTemperature" :max="50"></ecLine>
            </el-row>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { getDeviceDetail } from '@/api/device'
import SockJs from 'sockjs-client'
import Stomp from 'stompjs'
import Config from '@/config'
import Gauge from '@/components/echarts/gauge'
import ecLine from '@/components/echarts/ec-line'
import { parseTime } from '@/utils'
export default {
  components: { Gauge, ecLine },
  data () {
    return {
      detail: {},
      activeName: 'first',
      loading: false,
      deviceAddress: {
        'cn-qingdao': '华北 1(青岛)',
        'cn-beijing': '华北 2(北京)',
        'cn-zhangjiakou': '华北 3(张家口)',
        'cn-huhehaote': '华北 5(呼和浩特)',
        'cn-hangzhou': '华东 1(杭州)',
        'cn-shanghai': '华东 2(上海)',
        'cn-shenzhen': '华南 1(深圳)'
      },
      nodeMcu: {
        mtemp: 0,
        mhumi: 0,
        mlux: 0
      },
      wemos: {
        mhumi: {
          date: [],
          data: []
        },
        animalTemperature: {
          date: [],
          data: []
        }
      }
    }
  },

  mounted () {
    this.getDeviceDetail()
    this.initWebSocket()
  },

  beforeDestroy () {
    this.disConnect()
    clearInterval(this.timer)
  },

  created () {

  },

  computed: {

  },

  methods: {
    /**
     * @description 查询产品详情
     */
    getDeviceDetail () {
      this.loading = true
      setTimeout(() => {
        getDeviceDetail(this.$route.params.iotId).then(res => {
          this.detail = res.data
        })
        this.loading = false
      }, 1500)
    },
    formateTime (val) {
      if (val === '' || val === null || val === undefined) {
        return '-'
      }
      return parseTime(val)
    },
    detailStatus () {
      switch (this.detail.status) {
        case 'ONLINE':
          return '在线'
        case 'OFFLINE':
          return '离线'
        case 'UNACTIVE':
          return '未激活'
        case 'DISABLE':
          return '已禁用'
        default:
          break
      }
    },
    /**
     * @description 初始化连接
     */
    initWebSocket () {
      this.connection()
      let that = this
      this.timer = setInterval(() => {
        try {
          that.stompClient.send('test')
        } catch (err) {
          console.log('Socket掉线了：' + err)
          this.$notify({
            title: '提示',
            message: 'Websocket掉线了，正在重新连接!',
            type: 'warning'
          })
          that.connection()
        }
      }, 1000 * 60 * 2)
    },

    /**
     * @description 建立连接
     */
    connection () {
      this.socket = new SockJs(Config.webSocketUrl)
      this.stompClient = Stomp.over(this.socket)
      this.stompClient.connect({}, (frame) => {
        this.$notify({
          title: '提示',
          message: 'Websocket连接成功!',
          type: 'success'
        })
        // 订阅温湿度监控数据
        this.subscribeNodeMcu()
        this.subscribeWemos()
      })
    },

    /**
     * @description 订阅温湿度监控
     */
    subscribeNodeMcu () {
      this.stompClient.subscribe(Config.nodeMcuTopic, (res) => {
        let data = JSON.parse(res.body)
        this.nodeMcu.mtemp = data.items.mtemp.value
        this.nodeMcu.mhumi = data.items.mhumi.value
        this.nodeMcu.mlux = data.items.mlux.value
      }, (err) => {
        console.log('出错啦：' + err)
        this.$notify({
          title: '提示',
          message: 'Websocket连接失败!',
          type: 'error'
        })
      })
    },

    /**
     * @description 订阅人体监测设备
     */
    subscribeWemos () {
      this.stompClient.subscribe(Config.wemosTopic, (res) => {
        let data = JSON.parse(res.body)
        this.wemos.mhumi.date.push(parseTime(data.items.mhumi.time, '{h}:{i}:{s}'))
        this.wemos.mhumi.data.push(data.items.mhumi.value)
        this.wemos.animalTemperature.date.push(parseTime(data.items.AnimalTemperature.time, '{h}:{i}:{s}'))
        this.wemos.animalTemperature.data.push(data.items.AnimalTemperature.value)
      }, (err) => {
        console.log('出错啦：' + err)
        this.$notify({
          title: '提示',
          message: 'Websocket连接失败!',
          type: 'error'
        })
      })
    },

    /**
     * @description 关闭连接
     */
    disConnect () {
      if (this.stompClient != null) {
        this.stompClient.disconnect()
      }
      console.log('连接已关闭！')
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
  margin-bottom: 20px;
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
</style>
