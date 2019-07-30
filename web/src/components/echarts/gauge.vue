<template>
  <el-col :xs="12" :sm="12" :lg="8">
    <p class="board-title">{{tips}}</p>
    <div class="gauge" ref="gauge" :style="{height: height, width: width}"></div>
  </el-col>
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons')
export default {
  props: {
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '100%'
    },
    data: {
      type: Number,
      default: 0
    },
    title: {
      type: String,
      default: 'Chart'
    },
    tag: {
      type: String,
      default: '℃'
    },
    min: {
      type: Number,
      default: 0
    },
    max: {
      type: Number,
      default: 100
    },
    tips: {
      type: String,
      default: '实时数据'
    }
  },
  data () {
    return {
      gaugeData: null
    }
  },

  computed: {

  },

  mounted () {
    const that = this
    that.$nextTick(() => {
      that.initChart()
    })
    that.echartsResize()
  },

  watch: {
    data: {
      deep: true,
      handler (newval, oldval) {
        if (this.chart) {
          if (newval) {
            this.setOption(newval, this.title, this.tag, this.min, this.max)
          } else {
            this.setOption(oldval, this.title, this.tag, this.min, this.max)
          }
        }
      }
    }
  },

  methods: {
    initChart () {
      this.chart = echarts.init(this.$refs.gauge, 'macarons')
      this.chart.showLoading()
      this.setOption(this.data, this.title, this.tag, this.min, this.max)
      this.echartsResize()
    },
    setOption (data, title, tag, min, max) {
      this.chart.setOption({
        tooltip: {
          formatter: '{b} : {c}' + tag
        },
        toolbox: {
          show: false
        },
        series: [
          {
            // name: '业务指标',
            type: 'gauge',
            detail: { formatter: '{value}' + tag },
            data: [{ value: data, name: title }],
            min: min,
            max: max,
            axisLine: { show: false },
            title: { offsetCenter: [0, '-20%'], color: '#999', fontSize: '16' }
          }
        ]
      })
    },
    echartsResize () {
      const that = this
      setTimeout(() => {
        window.addEventListener('resize', () => {
          that.chart.resize()
        })
        that.chart.hideLoading()
      }, 500)
    }
  }
}
</script>

<style lang="scss" scoped>
.board-title {
  position: absolute;
  z-index: 1;
  top: 20px;
  margin-left: 30px;
  color: rgba(0, 0, 0, 0.65);
}
.gauge {
  // overflow: hidden;
  color: #666;
  background: #fff;
  box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
  border-color: rgba(0, 0, 0, 0.05);
  margin-top: 18px;
  width: 100%;
  height: 100%;
}
</style>
