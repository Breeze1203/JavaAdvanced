<template>
  <div style="margin-top: 25px;width: 100%">
    <div style="width: 100%;margin-top:30px;">
      <div>
        <el-input placeholder="请输入角色英文" v-model="role.role_name" style="width: 200px" type="text">
          <template #prepend>role_</template>
        </el-input>
        <el-input v-model="role.nameZh" placeholder="请输入角色中文名称" style="width: 200px;margin-left: 20px"
                  type="text">
        </el-input>
        <el-input
            style="width: 200px;margin-left: 20px"
            v-model="role.description"
            maxlength="30"
            placeholder="输入描述"
            show-word-limit
            type="text"
        />
        <el-button :disabled="!permissions_data.includes('add_role')" @click="addRole" plain style="margin-left: 30px"
                   type="primary">添加角色
          <el-icon>
            <Plus/>
          </el-icon>
        </el-button>
        <el-button @click="getAllRoles" plain style="margin-left: 30px" type="success">刷新
          <el-icon>
            <Refresh/>
          </el-icon>
        </el-button>
        <el-button @click="cancelUpdate" plain style="margin-left: 30px" type="info">重置
          <el-icon>
            <Refresh/>
          </el-icon>
        </el-button>
      </div>
      <div style="width: 100%;margin-top: 40px">
        <el-tabs @tab-click="handleTabClick" v-model="activity">
          <el-tab-pane v-for="(item, index) in roles" :name="item.id" :key="index" :label="item.nameZh">
            <el-checkbox-group v-model="checked">
              <el-checkbox v-for="(permission, index) in Permissions" :label="permission.id" :key="index">
                {{ permission.describe }}
              </el-checkbox>
            </el-checkbox-group>
            <div style="justify-content: end;display: flex">
              <el-button :disabled="!permissions_data.includes('update_per')" plain type="primary"
                         @click="changePermission(item)">保存
              </el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div style="margin-top: 30px">
        <el-table :data="roles" border style="width: 100%">
          <el-table-column align="center" prop="id" label="唯一标识" width="100"/>
          <el-table-column align="center" prop="role_name" label="角色标识" width="180"/>
          <el-table-column align="center" prop="nameZh" width="180" label="角色"/>
          <el-table-column align="center" prop="create_time" width="180" label="创建时间"/>
          <el-table-column align="center" prop="description" width="250" label="描述"/>
          <el-table-column fixed="right" align="center" label="操作" width="250">
            <template #default="scope">
              <el-button :disabled="!permissions_data.includes('update_role')" @click="updateRole(scope.row)"
                         icon="Edit"
                         type="text"><span style="color: #666666">修改</span></el-button>
              <el-button :disabled="!permissions_data.includes('delete_role')" @click="DeleteRole(scope.row)"
                         icon="Delete"
                         type="text"><span style="color: red">删除</span></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
  <el-dialog v-model="updateVisible" title="修改角色" width="35%" center>
    <span>
     <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>角色英文名：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 200px" v-model="role.role_name" size="small"/>
      </el-col>
    </el-row>
      <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>角色中文名：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 200px" v-model="role.nameZh" size="small"/>
      </el-col>
    </el-row>
      <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>角色描述：</span>
      </el-col>
      <el-col :span="5">
        <el-input
            style="width: 200px;margin-left: 20px"
            v-model="role.description"
            maxlength="30"
            placeholder="输入描述"
            show-word-limit
            type="text"
        />
      </el-col>
    </el-row>
    </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelUpdate">取消</el-button>
        <el-button :disabled="!permissions_data.includes('update_role')" type="primary" @click="sureUpdate">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "@/util/requestUtil";
import permissions_request from "@/api/permissions";
import {ElMessage, ElMessageBox} from "element-plus";
import {Refresh} from "@element-plus/icons-vue";
import store from "@/store";
import {mapState} from "vuex";

export default {
  name: 'RoleSet',
  components: {Refresh},
  data() {
    return {
      roles: [], // 所有角色数组
      Permissions: [], //所有权限数组
      checked: [], //复选框选中的值，
      activity: null,
      updateVisible: false,
      role: {
        id: null,
        role_name: null,
        nameZh: null,
        create_time: null,
        description: null
      },
      permission: {
        rid: '',
        allId: ''
      },
    }
  },
  computed: {
    ...mapState(['permissions_data'])
  },
  methods: {
    handleTabClick(tab) {
      this.checked = [];
      // 将角色id作为tab的name
      permissions_request.getPermissionByRole(tab.props.name).then(resp => {
        if (resp.data != null) {
          for (let i = 0; i < resp.data.length; i++) {
            this.checked.push(resp.data[i].id);
          }
        }
      })
    },
    changePermission(item) {
      // 修改权限
      ElMessageBox.confirm(
          '此操作将会修改【' + item.nameZh + '】权限，是否继续?',
          'Warning',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            this.permission.allId = this.checked;
            this.permission.rid = item.id;
            permissions_request.updatePermission(JSON.stringify(this.permission)).then(resp => {
              if (resp.data.code === 200) {
                if (item.id === JSON.parse(sessionStorage.getItem("user")).role.id) {
                  permissions_request.getPermissionByRole(item.id).then(resp => {
                    if (resp.data != null) {
                      const permissions = [];
                      for (let i = 0; i < resp.data.length; i++) {
                        permissions.push(resp.data[i].name);
                      }
                      store.commit('initPermission', permissions);
                    }
                  })
                }
                ElMessage.success(resp.data.message);
                this.activity = null;
              } else {
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
    // 获取所有角色
    getAllRoles() {
      request.getAllRoles().then(resp => {
        if (resp.data != null) {
          this.roles = resp.data;
        }
      })
    },
    // 添加角色
    addRole() {
      if (this.role.nameZh == null || this.role.role_name == null) {
        ElMessage.error("请输入要添加的角色名称");
        return;
      }
      request.addRole(this.role).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.role.nameZh = null;
          this.role.role_name = null;
          this.role.description=null;
          this.getAllRoles();
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 编辑角色
    updateRole(data) {
      this.updateVisible = true;
      this.role.id = data.id;
      this.role.role_name = data.role_name;
      this.role.nameZh = data.nameZh;
      this.role.description = data.description;
    },
    // 取消修改角色
    cancelUpdate() {
      this.updateVisible = false;
      this.role.id = null;
      this.role.role_name = null;
      this.role.nameZh = null;
      this.role.description = null;
    },
    // 确定修改角色
    sureUpdate() {
      request.updateRole(this.role).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.getAllRoles();
        } else {
          ElMessage.error(resp.data.message);
        }
      })
      this.cancelUpdate();
    },
    // 删除角色
    DeleteRole(data) {
      request.deleteRole(data.id).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.getAllRoles();
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 获取所有权限
    initAllPermission() {
      permissions_request.getAllPermission().then(resp => {
        if (resp.data != null) {
          this.Permissions = resp.data;
        }

      })
    },
  },
  mounted() {
    this.getAllRoles();
    this.initAllPermission();
  }
}

</script>

<style scoped>
.shadow-box {
  /*box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4); !* 设置阴影效果 *!*/
  /*border: 1px solid #ccc; !* 设置边框 *!*/
  padding: 20px; /* 设置内边距，可根据需要进行调整 */
}

.tag-container {
  display: flex;
  flex-direction: column;
}

.custom-tag {
  border: none;
  background-color: #ffffff; /* 修改为你想要的颜色值 */
  font-size: 14px; /* 修改为你想要的字体大小 */
  color: black;
  text-align: left;
}

.container {
  display: flex;
}

</style>
