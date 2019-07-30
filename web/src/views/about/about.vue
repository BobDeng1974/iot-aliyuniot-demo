<template>
  <div class="about-container">
    <h3>{{data.title}}</h3>
    <el-row>
      <el-col :span="10" :offset="7">
        <p v-html="data.content"></p>
      </el-col>
    </el-row>

  </div>

</template>

<script>
import { getAboutList } from '@/api/about'
export default {
  data () {
    return {
      data: {}
    }
  },
  mounted () {
    this.getAboutList()
  },
  methods: {
    getAboutList () {
      getAboutList(1, 10).then(res => {
        this.data = res.data[0]
      })
    }
  }
}
</script>

<style scoped>
.about-container {
  padding: 10% 22px 22px 22px;
}
h3 {
  text-align: center;
  letter-spacing: 2px;
}
p {
  line-height: 30px;
  /* text-indent: 20px; */
}
</style>
