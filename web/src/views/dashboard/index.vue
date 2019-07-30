<template>
  <div class="dashboard-container">
    <div class="dashboard-editor-container">
      <div class="system-panel">
        <el-row :gutter="40" class="panel-group">
          <div class="tips" style="margin-left: 20px">设备概览</div>
          <!-- <el-col :span="12"> -->
          <el-col :xs="24" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-people">
                <svg-icon icon-class="iot" class-name="card-panel-icon" />
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">设备总量</div>
                <count-to :start-val="0" :end-val="deviceStatus.deviceCount" :duration="2600" class="card-panel-num" />
              </div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-message">
                <svg-icon icon-class="device-jihuo" class-name="card-panel-icon" />
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">激活设备</div>
                <count-to :start-val="0" :end-val="deviceStatus.activeCount" :duration="3000" class="card-panel-num" />
              </div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-money">
                <svg-icon icon-class="device-online" class-name="card-panel-icon" />
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">在线设备</div>
                <count-to :start-val="0" :end-val="deviceStatus.onlineCount" :duration="3200" class="card-panel-num" />
              </div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-shopping">
                <svg-icon icon-class="device-error" class-name="card-panel-icon" />
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">离线设备</div>
                <count-to :start-val="0" :end-val="deviceStatus.errorCount" :duration="3600" class="card-panel-num" />
              </div>
            </div>
          </el-col>
          <!-- </el-col> -->

        </el-row>
      </div>

      <!-- 系统信息面板 -->
      <div class="system-panel">
        <div class="tips">系统状态</div>
        <el-row class="system-content">
          <el-col :xs="12" :sm="12" :lg="6">
            <div class="system-info-tips">CPU使用率</div>
            <el-progress type="circle" :percentage="systemStatus.cpuUsed"></el-progress>
            <div class="system-info-tips">{{systemStatus.cpuTotal}} 核心</div>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6">
            <div class="system-info-tips">内存使用率</div>
            <el-progress type="circle" :percentage="systemStatus.memRate"></el-progress>
            <div class="system-info-tips">{{systemStatus.memUsed}}/{{systemStatus.memTotal}}(MB)</div>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6">
            <div class="system-info-tips">SWAP交换空间</div>
            <el-progress type="circle" :percentage="0"></el-progress>
            <div class="system-info-tips">{{systemStatus.swapUsed}}/{{systemStatus.swapTotal}}(MB)</div>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6">
            <div class="system-info-tips">本地硬盘</div>
            <el-progress type="circle" :percentage="systemStatus.sdRate"></el-progress>
            <div class="system-info-tips">{{systemStatus.sdUsed}}G/{{systemStatus.sdTotal}}G</div>
          </el-col>
        </el-row>

      </div>

      <!-- 系统信息 -->
      <div class="system-panel">
        <div class="tips">服务器信息</div>
        <el-row class="system-content">
          <el-col :span="6">
            <div class="grid-content">主机名称</div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content">{{systeminfo.name}}</div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content">IP地址</div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content">{{systeminfo.IP}}</div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content">系统架构</div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content">{{systeminfo.osArch}}</div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content">系统名称</div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content">{{systeminfo.osName}} {{systeminfo.osVersion}}</div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
import SockJs from 'sockjs-client'
import Stomp from 'stompjs'
import Config from '@/config'
import { getSystemInfo } from '@/api/system'
// import Gauge from '@/components/echarts/gauge'
import { getAllDeviceStatus } from '@/api/device'
import CountTo from 'vue-count-to'
export default {
  name: 'dashboard',
  components: { CountTo },
  data () {
    return {
      temp: 0,
      humi: 0,
      lux: 0,
      deviceStatus: { deviceCount: 0, activeCount: 0, onlineCount: 0, errorCount: 0 },
      systeminfo: {},
      systemStatus: {}
    }
  },
  mounted () {
    this.initWebSocket()
    this.getAllDeviceStatus()
    this.getSystemInfo()
  },

  beforeDestroy () {
    this.disConnect()
    clearInterval(this.timer)
  },

  methods: {
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
            message: 'Websocket掉线了!',
            type: 'warning'
          })
          that.connection()
        }
      }, 30000)
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
        this.stompClient.subscribe(Config.systemInfoTopic, (res) => {
          let data = JSON.parse(res.body)
          this.systemStatus = data
        }, (err) => {
          console.log('出错啦：' + err)
          this.$notify({
            title: '提示',
            message: 'Websocket连接失败!',
            type: 'error'
          })
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
    },

    getAllDeviceStatus () {
      getAllDeviceStatus().then(res => {
        this.deviceStatus.deviceCount = res.data.deviceCount
        this.deviceStatus.activeCount = res.data.activeCount
        this.deviceStatus.onlineCount = res.data.onlineCount
        this.deviceStatus.errorCount = res.data.activeCount - res.data.onlineCount
      })
    },

    checkData (info) {
      this.$alert(info, '告警提示', {
        // confirmButtonText: '确定',
        type: 'warning',
        center: true,
        showConfirmButton: false
      }).catch(() => {
        this.$notify({
          type: 'info',
          title: '提示',
          message: '已签收告警信息'
        })
      })
    },

    /**
     * 获取系统信息
     */
    getSystemInfo () {
      getSystemInfo().then(res => {
        this.systeminfo = res.data
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-container {
  background-color: rgb(240, 242, 245);
  width: 100%;
  height: 100%;
}
.dashboard-editor-container {
  padding: 0 22px 22px 22px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
  .tips {
    border-left: 5px solid #34bfa3;
    margin: 0 0 20px 5px;
    padding-left: 5px;
  }
}
.panel-group {
  padding-top: 18px;
  .card-panel-col {
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shopping {
      color: #34bfa3;
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      text-align: center;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

.info {
  margin: 20px 0;
  .grid-content {
    background: #34bfa3;
  }
}

.system-content {
  // height: 200px;
  background: #fff;
  margin-bottom: 20px;
  text-align: center;
  padding: 20px 0;
}
.system-info-tips {
  padding: 10px 0;
  color: #999;
}

.grid-content {
  padding: 20px 0;
  // margin: 0 20px;
  border: 1px solid #ccc;
}
</style>
