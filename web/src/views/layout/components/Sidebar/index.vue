<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <scroll-bar>
      <el-menu mode="vertical" :unique-opened="false" :default-active="activeMenu" :collapse="isCollapse" :collapse-transition="false" background-color="#304156" text-color="#fff" active-text-color="#409EFF">
        <sidebar-item :routes="permission_routers"></sidebar-item>
      </el-menu>
    </scroll-bar>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import ScrollBar from '@/components/ScrollBar'
import Logo from './Logo'

export default {
  components: { SidebarItem, ScrollBar, Logo },
  computed: {
    ...mapGetters([
      'permission_routers',
      'sidebar'
    ]),
    isCollapse () {
      return !this.sidebar.opened
    },
    showLogo () {
      return this.$store.state.setting.sidebarLogo
    },
    activeMenu () {
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    }
  }
}
</script>
