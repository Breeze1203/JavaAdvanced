<template>
  <div style="margin-top: 30px;width: 100%">
    <el-row :gutter="6" style="margin-bottom: 30px">
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1">
        <label>用户菜单权限</label>
      </el-col>
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1">
        <el-button plain icon="Refresh" @click="initPermissions">刷新</el-button>
      </el-col>
    </el-row>
    <el-collapse v-for="(role,index) in roles" accordion v-model="accordion" @change="changeItem">
      <el-collapse-item style="font-size: larger;" :title="role.nameZh" :name="role.id">
        <div>
          <el-tree ref="tree" show-checkbox node-key="menu_id"
                   :default-checked-keys="checkedNodes" :data="this.menuData"
                   :props="this.defaultProps"
          />
        </div>
        <div style="display: flex;justify-content: flex-end;">
          <el-button size="small" plain type="info" @click="cancel">取消修改</el-button>
          <el-button :disabled="!permissions_data.includes('update_menu')" size="small" plain type="danger" @click="sure(role,index)">确认修改</el-button>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
  <div style="margin-top: 10px">
    <el-row :gutter="6" style="margin-bottom: 30px">
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1">
        <label>用户操作权限</label>
      </el-col>
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1">
        <el-button plain icon="Refresh" @click="initPermissions">刷新</el-button>
      </el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7">
        <el-button :disabled="!permissions_data.includes('export_data')" plain type="success" icon="Upload">导出</el-button>
      </el-col>
    </el-row>
    <el-table
        :data="operation"
        style="width: 100%;"
        row-key="menu_id"
        border
        :default-expand-all="false">
      <el-table-column align="center" prop="id" label="唯一标识"></el-table-column>
      <el-table-column align="center" prop="name" label="权限标识"></el-table-column>
      <el-table-column align="center" prop="describe" label="操作权限描述"></el-table-column>
    </el-table>
    <el-pagination style="margin-top: 10px"
                   :page-sizes="[3, 6, 9]"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total=total
                   background
                   :default-page-size="3"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
    />
  </div>
</template>
<script>
import requestUtil from "@/util/requestUtil";
import permissions_request from "@/api/permissions";
import menu_request from "@/api/menu";
import {ElMessage, ElMessageBox} from "element-plus";
import store from "@/store";
import {mapState} from "vuex";

export default {
  name: 'Permission',
  data() {
    return {
      roles: [],
      accordion: -1,
      operation: [],
      total: null,// 操作权限总条数,
      page: 1,
      size: 3,
      menuData: [],
      checkedNodes: [],
      defaultProps: {
        children: 'menus',
        label: 'menu_name',
      }
    }
  },
  computed:{
    ...mapState(['permissions_data'])
  },
  methods: {
    // 初始化所有菜单
    initMenu() {
      menu_request.getAllMenus().then(resp => {
        this.menuData = resp.data;
      })
    },
    // 初始化所有角色
    initRole() {
      requestUtil.getAllRoles().then(resp => {
        this.roles = resp.data;
      })
    },
    // 初始化用户所有操作权限
    initPermissions() {
      permissions_request.getAllPermByPage(this.page, this.size).then(resp => {
        this.operation = resp.data;
      })
    },
    // 初始化操作权限条数
    initCount() {
      permissions_request.getPermissionCount().then(resp => {
        this.total = resp.data;
      })
    },
    // collapse切换
    changeItem(rId) {
      // 切换item时候，里面的数据需及时刷新
      this.initMenu();
      this.checkedNodes = [];
      if (rId == null || rId == '') return;
      menu_request.getMenusChecked(rId).then(resp => {
        this.checkedNodes = resp.data;
      });
    },
    // 取消修改菜单权限
    cancel() {
      this.checkedNodes = [];
      this.accordion = -1;
    },
    // 确认修改
    sure(role,index) {
      if (this.$refs.tree[index].getCheckedKeys().length === 0) {
        ElMessage.error("请选择菜单权限");
        return;
      }
      ElMessageBox.confirm(
          '此操作将会修改【' + role.nameZh + '】的菜单权限,是否继续',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            // 将数组转为字符串
            let nodes = this.$refs.tree[index].getCheckedKeys(true).join(',');
            menu_request.updateRoleMenuId(nodes,role.id).then(resp => {
              if(resp.data.code==200){
                ElMessage.success(resp.data.message);
                // 如果当前修改用户的权限是目前登录用户的权限
                if(role.id==JSON.parse(sessionStorage.getItem("user")).id){
                  // 刷新menu,同时更新vuex
                  menu_request.getMenusByRole(role.id).then(resp=>{
                    store.commit("initMenu",resp.data);
                  })
                }
                this.accordion=-1;
              }else {
                ElMessage.error(resp.data.message);
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '操作取消',
            })
          })
    },
    handleSizeChange(size) {
      this.size = size;
      this.initPermissions();
    },
    handleCurrentChange(page) {
      this.page = page;
      this.initPermissions();
    }
  },
  mounted() {
    this.initRole();
    this.initPermissions();
    this.initCount();
  }
}
</script>
<style scoped>
.collapse {
  margin-top: 30px;
}
</style>
