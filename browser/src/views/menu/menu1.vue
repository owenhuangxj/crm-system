<template>
  <div class="login-container">
    <h1>{{pageName}}</h1>
    <!-- Admin can see this -->
    <el-tag v-permission="['root']">root</el-tag>

    <!-- Editor can see this -->
    <el-tag v-permission="['manager']">manager</el-tag>

    <!-- Editor can see this -->
    <el-tag v-permission="['root','manager']">Both root or manager can see this</el-tag>
  </div>

</template>

<script>
  // 当然你也可以为了方便使用，将它注册到全局
  import permission from '@/directive/permission/index' // 权限判断指令
  export default {
    directives: { permission },
    name: 'Menu1',
    data() {
      return {
        pageName: '业绩管理'
      }
    }
  }
</script>

