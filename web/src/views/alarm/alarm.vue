<template>
  <div class="alarm-container">
    <div class="alarm-editor-container">
      <div class="system-panel">
        <div class="tips">环境实时监控</div>
        <el-row :gutter="40" class="system-content">
          <gauge :height="'350px'" :title="'温度'" :min="-10" :tips="'环境温度(实时)'" :data="nodeMcu.mtemp"></gauge>
          <gauge :height="'350px'" :title="'湿度'" :tag="'%'" :tips="'环境湿度(实时)'" :data="nodeMcu.mhumi"></gauge>
          <gauge :height="'350px'" :title="'光照度'" :tag="'Lx'" :max="65500" :tips="'环境亮度(实时)'" :data="nodeMcu.mlux"></gauge>
        </el-row>
      </div>
    </div>

    <div class="alarm-editor-container">
      <div class="system-panel">
        <div class="tips">人体实时监控</div>
        <el-row :gutter="40" class="system-content">
          <ecLine :title="'实时心率监测'" :height="'350px'" :data="wemos.mhumi"></ecLine>
          <ecLine :title="'人体实时温度'" :height="'350px'" :data="wemos.animalTemperature" :max="50"></ecLine>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
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
    this.initWebSocket()
  },

  beforeDestroy () {
    this.disConnect()
    clearInterval(this.timer)
  },

  methods: {

    /**
     * @description 初始化连接
     */
    initWebSocket () {
      this.connection()
      let that = this
      this.timer = setInterval(() => {
        try {
          that.stompClient.send('test')
          this.checkNodeMcuData()
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
    },

    /**
     * @description 检查数据
     */
    checkNodeMcuData () {
      if (this.nodeMcu.mtemp > 38) {
        this.checkData('当前室温过高，请注意！')
      } else if (this.nodeMcu.mtemp < -3) {
        this.checkData('当前室温过低，请注意！')
      }
      if (this.nodeMcu.mhumi > 65) {
        this.checkData('当前环境湿度过高，请注意！')
      } else if (this.nodeMcu.mhumi < 45) {
        this.checkData('当前环境湿度过低，请注意！')
      }
      if (this.nodeMcu.mlux > 65) {
        this.checkData('当前环境湿度过高，请注意！')
      } else if (this.nodeMcu.mlux < 45) {
        this.checkData('当前环境湿度过低，请注意！')
      }
    },
    /**
     * @description 检查数据并告警
     */
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
    }
  }
}
</script>

<style scoped>
.alarm-editor-container {
  padding: 22px;
  background-color: rgb(240, 242, 245);
}
.tips {
  border-left: 5px solid #34bfa3;
  padding-left: 5px;
}
</style>
