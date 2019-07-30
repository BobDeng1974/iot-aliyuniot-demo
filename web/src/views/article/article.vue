<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('about:add')">添加</el-button>
          <el-button type="success" icon="plus" @click="refreshData" v-if="hasPerm('about:list')">刷新</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 列表 -->
    <el-table :data="about" v-loading.body="listLoading" element-loading-text="拼命加载中..." border fit highlight-current-row>
      <el-table-column align="center" label="ID" prop="id" width="80"></el-table-column>
      <el-table-column align="center" label="标题" prop="title" width="120"></el-table-column>
      <el-table-column align="center" label="内容" prop="content"></el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" :formatter="formatTime" width="200"></el-table-column>
      <el-table-column align="center" label="管理" width="120" v-if="hasPerm('about:delete') ">
        <template slot-scope="scope">
          <div v-if="scope.row.roleName!='管理员'">
            <el-button circle type="primary" icon="el-icon-edit" @click="showUpdate(scope.$index)" v-if="hasPerm('about:update')"></el-button>
            <el-button circle v-if="hasPerm('about:delete')" type="danger" icon="el-icon-delete" @click="removeAbout(scope.$index)"></el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="cursor.offset" @prev-click="prevPage" @next-click="nextPage" :page-sizes="[10, 20, 30, 50]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="cursor.total" :background="true"></el-pagination>

    <!-- 新增权限对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempArticle" label-position="left" label-width="100px" style='width: 600px; margin-left:50px;'>
        <el-form-item label="标题" required>
          <el-input type="text" v-model="tempArticle.title" style="width: 250px;"></el-input>
        </el-form-item>
        <el-form-item label="内容" required>
          <el-input type="textarea" v-model="tempArticle.content" style="width: 250px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="success" v-if="dialogStatus=='create'" @click="createAbout">保 存</el-button>
        <el-button type="primary" v-else @click="updateAbout">更 新</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getAboutList, createAbout, deleteAbout, updateAbout } from '@/api/about'
import { utc2beijing } from '@/utils'
export default {
  data () {
    return {
      about: [], // 表格的数据
      cursor: {},
      listLoading: false, // 数据加载等待动画
      pageNum: 1, // 页码
      pageSize: 10, // 每页条数
      dialogFormVisible: false,
      dialogStatus: 'create',
      textMap: {
        update: '更新文章',
        create: '新建文章'
      },
      tempArticle: {
        content: '',
        title: ''
      },
      aboutId: ''
    }
  },
  created () {
    this.getList()
  },
  methods: {
    getList () {
      this.listLoading = true
      setTimeout(() => {
        getAboutList(this.pageNum, this.pageSize).then(res => {
          this.about = res.data
          this.cursor = res.cursor
        })
        this.listLoading = false
      }, 1500)
    },

    handleSizeChange (val) {
      this.pageSize = val
      this.getAboutList()
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getAboutList()
    },
    prevPage () {
      this.pageNum += 1
      this.getAboutList()
    },
    nextPage () {
      this.pageNum -= 1
      this.getAboutList()
    },

    removeAbout ($index) {
      deleteAbout(this.about[$index].id).then(res => {
        this.refreshData()
      })
    },

    showCreate () {
      this.dialogFormVisible = true
      this.tempArticle.title = ''
      this.tempArticle.content = ''
      this.dialogStatus = 'create'
    },

    showUpdate ($index) {
      let data = this.about[$index]
      this.tempArticle.title = data.title
      this.tempArticle.content = data.content
      this.aboutId = data.id
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },

    updateAbout () {
      updateAbout(this.aboutId, this.tempArticle).then(res => {
        this.refreshData()
        console.log(res)
      })
    },

    createAbout () {
      createAbout(this.tempArticle).then(res => {
        this.refreshData()
      })
    },
    refreshData () {
      this.dialogFormVisible = false
      this.getList()
    },
    formatTime (val) {
      return utc2beijing(val.createTime)
    }
  }
}
</script>

<style scoped>
.el-pagination {
  text-align: center;
  margin: 20px 0;
}
</style>
