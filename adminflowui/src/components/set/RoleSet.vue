<template>
  <div class="common-layout" style="margin-top: 25px">
    <el-container class="shadow-box">
      <el-aside width="200px">
        <div style="border-bottom: 1px solid;text-align: center">
          <h4>角色管理</h4>
        </div>
        <div class="tag-container" v-for="(item,index) in roles" :key="index">
          <el-tag size="large" class="custom-tag">{{ item.nameZh }}
            <el-dropdown size="small" trigger="hover">
              <el-icon>
                <Setting/>
              </el-icon>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="updateRole(item)">编辑</el-dropdown-item>
                  <el-dropdown-item @click="DeleteRole(item)">删除</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-tag>
        </div>
      </el-aside>
      <el-container>
        <el-header>
          <div class="container">
            <el-input placeholder="请输入角色英文" v-model="role.role_name" style="width: 200px" type="text">
              <template #prepend>role_</template>
            </el-input>
            <el-input v-model="role.nameZh" placeholder="请输入角色中文名称" style="width: 200px;margin-left: 20px" type="text">
            </el-input>
            <el-button @click="addRole" style="margin-left: 20px" type="primary">添加角色
              <el-icon>
                <Plus/>
              </el-icon>
            </el-button>
          </div>
        </el-header>
        <el-main>
          <div>
            <el-tabs @tab-click="handleTabClick" v-model="activity">
              <el-tab-pane v-for="(item, index) in roles" :name="item.id" :key="index" :label="item.nameZh">
                <el-checkbox-group v-model="checked">
                  <el-checkbox v-for="(permission, index) in Permissions" :label="permission.id" :key="index">
                    {{ permission.describe }}
                  </el-checkbox>
                </el-checkbox-group>
                <div style="justify-content: end;display: flex">
                  <el-button type="primary" @click="changePermission(item)">保存</el-button>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
  <el-dialog v-model="updateVisible" title="修改角色" width="40%" center>
    <span>
     <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>角色英文名：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 300px" v-model="role.role_name" size="small"/>
      </el-col>
    </el-row>
      <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>角色中文名：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 300px" v-model="role.nameZh" size="small"/>
      </el-col>
    </el-row>
    </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelUpdate">取消</el-button>
        <el-button type="primary" @click="sureUpdate">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "@/util/requestUtil";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: 'RoleSet',
  data() {
    return {
      roles: [], // 所有角色数组
      Permissions: [], //所有权限数组
      checked: [], //复选框选中的值，
      activity:null,
      updateVisible:false,
      role:{
        id:null,
        role_name:null,
        nameZh:null
      },
      permission: {
        rid: '',
        allId: ''
      },
    }
  },
  methods: {
    handleTabClick(tab) {
      // 将角色id作为tab的name
      request.getPermissionByRole(tab.props.name).then(resp => {
        if (resp.data != null) {
          this.checked = resp.data;
        }
      })
    },
    changePermission(item) {
      // 修改权限
      ElMessageBox.confirm(
          '此操作将会修改【'+item.nameZh+'】权限，是否继续?',
          'Warning',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            this.permission.allId=this.checked;
            this.permission.rid=item.id;
            request.updatePermission(JSON.stringify(this.permission)).then(resp=>{
              if(resp.data.code===200){
                ElMessage.success(resp.data.message);
                this.activity=null;
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
    // 获取所有角色
    getAllRoles() {
      request.getAllRoles().then(resp => {
        if (resp.data != null) {
          this.roles = resp.data;
        }
      })
    },
    // 添加角色
    addRole(){
      request.addRole(this.role).then(resp=>{
        if(resp.data.code===200){
          ElMessage.success(resp.data.message);
          this.role.nameZh=null;
          this.role.role_name=null;
          this.getAllRoles();
        }else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 编辑角色
    updateRole(data) {
      this.updateVisible=true;
      this.role.id=data.id;
      this.role.role_name=data.role_name;
      this.role.nameZh=data.nameZh;
    },
    // 取消修改角色
    cancelUpdate(){
      this.updateVisible=false;
      this.role.id=null;
      this.role.role_name=null;
      this.role.nameZh=null;
    },
    // 确定修改角色
    sureUpdate(){
      request.updateRole(this.role).then(resp=>{
        if(resp.data.code===200){
          ElMessage.success(resp.data.message);
          this.getAllRoles();
        }else {
          ElMessage.error(resp.data.message);
        }
      })
      this.cancelUpdate();
    },
    // 删除角色
    DeleteRole(data) {
      request.deleteRole(data.id).then(resp=>{
        if(resp.data.code===200){
          ElMessage.success(resp.data.message);
          this.getAllRoles();
        }else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 获取所有权限
    initAllPermission() {
      request.getAllPermission().then(resp => {
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4); /* 设置阴影效果 */
  border: 1px solid #ccc; /* 设置边框 */
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