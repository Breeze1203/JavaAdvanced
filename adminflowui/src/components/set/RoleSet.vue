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
                  <el-dropdown-item @click="DeleteRole(index)">删除</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-tag>
        </div>
      </el-aside>
      <el-container>
        <el-header>
          <div class="container">
            <el-input placeholder="请输入角色英文" style="width: 200px" type="text">
              <template #prepend>role_</template>
            </el-input>
            <el-input placeholder="请输入角色中文名称" style="width: 200px;margin-left: 20px" type="text">
            </el-input>
            <el-button style="margin-left: 20px" type="primary">添加角色
              <el-icon>
                <Plus/>
              </el-icon>
            </el-button>
          </div>
        </el-header>
        <el-main>
          <div>
            <el-collapse accordion v-model="activeNames" v-for="(item,index) in roles" :key="index">
              <el-collapse-item :title="item.nameZh" @click="changeItem(item)">
                <span>权限设置</span>
                <el-checkbox-group v-for="(permission,index) in Permissions" v-model="checked">
                  <el-checkbox :label="permission.describe"/>
                </el-checkbox-group>
                <div style="display: flex;justify-content: end">
                  <el-button size="small" type="default">取消</el-button>
                  <el-button size="small" type="primary" @click="updatePermission">保存</el-button>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import request from "@/util/requestUtil";

export default {
  name: 'RoleSet',
  data() {
    return {
      roles: [], // 所有角色数组
      Permissions: [], //所有权限数组
      checked: [],//复选框选中的值
      activeNames: -1
    }
  },
  methods: {
    // 切换手风琴时
    changeItem(item) {
      this.checked = [];
      request.getPermissionByRole(item.id).then(resp => {
        if (resp.data != null) {
          this.checked = resp.data;
        }
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
    // 编辑角色
    updateRole(data) {
      console.log(data);
    },
    // 删除角色
    DeleteRole(data) {
      console.log(data);
    },
    // 获取所有权限
    initAllPermission() {
      request.getAllPermission().then(resp => {
        if (resp.data != null) {
          this.Permissions = resp.data;
        }

      })
    },
    // 修改权限
    updatePermission() {
      console.log(this.checked);
    }
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