<template>
  <el-col :xs="24" :sm="24" :lg="12">
    <div class="line" ref="ecLine" :style="{height: height, width: width}"></div>
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
      type: Object,
      default: function () {
        // eslint-disable-next-line no-labels
        data: {}
      }
    },
    title: {
      type: String,
      default: 'Chart'
    },
    max: {
      type: Number,
      default: 1024
    }
  },
  data () {
    return {
      gaugeData: null
    }
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
            this.setOption(newval, this.title, this.max)
          } else {
            this.setOption(oldval, this.title, this.max)
          }
        }
      }
    }
  },

  methods: {
    initChart () {
      this.chart = echarts.init(this.$refs.ecLine, 'macarons')
      this.chart.showLoading()
      this.setOption(this.data, this.title, this.max)
      this.echartsResize()
    },
    setOption (data, title, max) {
      this.chart.setOption({
        title: { text: title, left: '20' },
        toolbox: {
          show: true,
          right: 20,
          feature: {
            dataZoom: {},
            magicType: { type: ['line', 'bar'] },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: data.date
        },
        yAxis: {
          type: 'value',
          max: max,
          axisLabel: {
            formatter: '{value}'
          }
        },
        series: [
          {
            name: title,
            type: 'line',
            data: data.data,
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: '平均值' }
              ]
            }
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
.line {
  // overflow: hidden;
  color: #666;
  background: #fff;
  box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
  border-color: rgba(0, 0, 0, 0.05);
  margin-top: 18px;
  width: 100%;
  height: 100%;
  padding-top: 10px;
}
</style>
