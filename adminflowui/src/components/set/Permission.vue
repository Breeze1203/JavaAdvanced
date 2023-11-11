<template>
  <div class="shadow">
  <el-table :data="permissions" border style="width: 100%">
    <el-table-column align="center" prop="id" label="id" width="200" />
    <el-table-column align="center" prop="name" label="权限名称" width="200" />
    <el-table-column align="center" prop="describe" label="权限描述" />
  </el-table>
  </div>
</template>
<script>
import request from "@/util/requestUtil";
export default {
  name:'Permission',
  data(){
    return{
      permissions:[]
    }
  },
  methods:{
    // 初始化权限资源
    initPermissions(){
      request.getAllPermission().then(resp=>{
        if(resp.data!=null){
          this.permissions=resp.data;
        }
      })
    }
  },
  mounted() {
    this.initPermissions();
  }
}
</script>
<style scoped>
.shadow {
  margin-top: 30px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4); /* 设置阴影效果 */
  border: 1px solid #ccc; /* 设置边框 */
  padding: 20px; /* 设置内边距，可根据需要进行调整 */
}
</style>