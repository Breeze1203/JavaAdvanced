<template>
  <div class="common-layout">
    <el-container>
      <el-aside>
        <el-menu style="border-right: none;"
                 v-for="(item,index) in this.routes" :key="index" @select="handleOpen">
          <el-sub-menu :index="1">
            <template #title>
              <span>{{ item.name }}</span>
            </template>
            <el-menu-item v-for="(child,indexj) in item.children" :index="child.path">{{ child.name }}</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>Header</el-header>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      routes: [
        {name: 'Alice', children: [{name: 'aoa', path: '/a'}]},
        {name: 'Bob', children: [{name: 'Bob', path: '/b'}]},
      ]
    }
  }
}
</script>

<style scoped>

</style>
