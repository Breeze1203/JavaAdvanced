<template>
  <div class="common-layout" style="margin-top: 25px">
    <el-container class="shadow-box">
      <el-aside width="200px">
        <div style="border-bottom: 1px solid;text-align: center">
          <h4>用户架构</h4>
        </div>
        <div style="margin-top: 25px">
          <el-tree @node-click="handleNodeClick" :default-expand-all="true" :props="defaultProps" :data="tree">
            <template #default="{ node, data }">
              <span>{{ node.label }}</span>
              <el-dropdown size="small" trigger="hover">
                <el-icon>
                  <Setting/>
                </el-icon>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="add(data)">添加</el-dropdown-item>
                    <el-dropdown-item @click="Delete(data)">删除</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </template>
          </el-tree>
        </div>
      </el-aside>
      <el-container>
        <el-header>
          <div class="container">
            <el-input size="small" v-model="username" placeholder="用户名" style="width: 180px" type="text"/>
            <el-select size="small"
                       v-model="state"
                       :clearable="true"
                       placeholder="状态"
                       style="width: 100px"
            >
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
            <el-button size="small" @click="search" type="primary">搜索</el-button>
            <el-button size="small" type="primary" @click="UserVisible = true">添加用户</el-button>
          </div>
        </el-header>
        <el-main>
          <el-table stripe border style="width: 100%" :data="userData">
            <el-table-column align="center" prop="id" label="id" width="90"/>
            <el-table-column align="center" prop="username" label="姓名" width="90"/>
            <el-table-column align="center" prop="address" label="地址" width="90"/>
            <el-table-column align="center" prop="phone" label="联系电话" width="190"/>
            <el-table-column align="center" prop="organization.name" label="所属节点" width="90"/>
            <el-table-column align="center" prop="role.nameZh" label="角色" width="90"/>
            <el-table-column align="center" label="头像" width="100">
              <template #default="scope">
                <el-avatar size="small" :src="scope.row.userFace"/>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="embod" label="个性签名" width="180"/>
            <el-table-column align="center" label="状态" width="90">
              <template #default="scope">
                <el-tag type="success" v-if="scope.row.state">启用</el-tag>
                <el-tag type="danger" v-else>禁用</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="email" label="邮箱" width="150"/>
            <el-table-column fixed="right" align="center" label="操作" width="200">
              <template #default="scope">
                <el-popconfirm title="确定要禁用?" @confirm="disableUser(scope.row)" v-if="scope.row.state">
                  <template #reference>
                    <span style="margin-right: 10px;">
                    <el-icon><Lock/></el-icon>禁用
                   </span>
                  </template>
                </el-popconfirm>
                <el-popconfirm title="确定要启用?" @confirm="disableUser(scope.row)" v-else>
                  <template #reference>
                    <span style="margin-right: 10px;">
                    <el-icon><Unlock/></el-icon>启用
                   </span>
                  </template>
                </el-popconfirm>
                <span style="margin-right: 10px;"><el-icon @click="updateUser(scope.row)"><Edit/></el-icon>修改</span>
                <span style="margin-right: 10px;color: red"><el-icon @click="deleteUser(scope.row)"><Delete/></el-icon>删除</span>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
  </div>
  <el-dialog
      v-model="dialogVisible"
      title="增加节点"
      width="25%">
    <el-row>
      <el-col :span="12">
        <span>上级节点：</span>
      </el-col>
      <el-col :span="12">
        <el-input v-model="nodeName" size="small" disabled/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 25px">
      <el-col :span="12">
        <span>名称</span>
      </el-col>
      <el-col :span="12">
        <el-input v-model="addOrganization.name" size="small"/>
      </el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="cancelAdd">取消</el-button>
        <el-button size="small" type="primary" @click="sureAdd">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="UserVisible" :title="dialogTitle" style="width: 33%">
    <el-row>
      <el-col :span="5">
        <span>用户名：</span>
      </el-col>
      <el-col :span="5">
        <el-input :disabled="this.dialogTitle==='修改用户'" style="width: 180px" v-model="user.username" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>联系方式：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 180px" v-model="user.phone" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>联系地址：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 180px" v-model="user.address" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>个性签名：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 180px" v-model="user.embod" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>邮箱：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 180px" v-model="user.email" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px" v-if="dialogTitle==='添加用户'">
      <el-col :span="5">
        <span>密码：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 180px" v-model="user.password" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px" v-if="dialogTitle==='修改用户'">
      <el-col :span="5">
        <span>所属角色：</span>
      </el-col>
      <el-col :span="5">
        <el-select size="small"
                   v-model="rid"
                   :clearable="true"
                   placeholder="所属角色"
                   style="width: 130px"
        >
          <el-option
              v-for="item in roles"
              :key="item.id"
              :label="item.nameZh"
              :value="item.id"
          />
        </el-select>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>所属节点：</span>
      </el-col>
      <el-col :span="5">
        <el-select size="small"
                   v-model="user.organizationId"
                   :clearable="true"
                   placeholder="所属节点"
                   style="width: 130px"
        >
          <el-option
              v-for="item in organization"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelUser">取消</el-button>
        <el-button @click="addUpdateUser">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script>
import request from "@/util/requestUtil";
import {ElMessage, ElMessageBox} from "element-plus";
import {Unlock} from "@element-plus/icons-vue";


export default {
  name: 'UseSet',
  components: {Unlock},
  data() {
    return {
      tree: [],//组织架构数据
      dialogTitle: '添加用户',
      rid: null,//添加用户时用户的角色
      username: null,
      state: null,
      UserVisible: false,
      organization: [],//所有节点
      roles: [],//用户所具有角色
      user: {
        id: null,
        phone: null,
        address: null,
        username: null,
        password: null,
        embod: null,
        email: null,
        state: null,
        organizationId: null,
        role: null
      },
      dialogVisible: false,//显示增加节点的dialog
      userData: [],//用户表格数据
      nodeName: '',//添加节点的名称
      options: [
        {
          value: true,
          label: '正常',
        },
        {
          value: false,
          label: '锁定',
        }],
      defaultProps: {
        label: 'name',
        children: 'children',
      },
      // 添加节点要用到的对象
      addOrganization: {
        id: null,
        name: null,
        parentId: null,
      }
    }
  },
  methods: {
    initAllOrganization() {
      request.getAllOrganization().then(resp => {
            if (resp.data != null) {
              this.organization = resp.data;
            }
          }
      )
    },
    //初始化user对象
    initUser() {
      this.user = {
        id: null,
        phone: null,
        address: null,
        username: null,
        password: null,
        embod: null,
        email: null,
        state: null,
        organizationId: null,
        role: null
      }
    },
    // 初始化组织架构节点(树状显示)
    initOrganization() {
      request.initOrganization().then(resp => {
        if (resp.data) {
          this.tree = resp.data;
        }
      })
    },
    // 初始化所有用户信息
    initAllUser(user) {
      request.initAllUser(user).then(resp => {
        if (resp.data) {
          this.userData = resp.data;
          this.initUser();
        }
      })
    },
    add(data) {
      this.dialogVisible = true;
      this.nodeName = data.name;
      this.addOrganization.parentId = data.id;
    },
    // 按要求搜索用户
    search() {
      this.user.username = this.username;
      this.user.state = this.state;
      this.initAllUser(this.user);
      this.user.state = null;
    },
    // 确定添加node
    sureAdd() {
      if (this.addOrganization.name === null) {
        ElMessage.error("请输入节点名称");
        return;
      }
      request.addNode(this.addOrganization).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.initOrganization();
          this.addOrganization.name = null
        } else {
          ElMessage.error(resp.data.message);
        }
      })
      this.dialogVisible = false;
    },
    //取消添加组织
    cancelAdd() {
      this.dialogVisible = false;
      this.addOrganization.name = null
    },
    // 删除组织
    Delete(data) {
      if (data.children.length > 0) {
        ElMessage.error("操作失败，该节点下有子节点");
        return;
      }
      ElMessageBox.confirm(
          '此操作将永久删除【' + data.name + '】节点，是否继续?',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      )
          .then(() => {
            request.deleteNode(data.id).then(resp => {
              if (resp.data.code === 200) {
                ElMessage.success(resp.data.message);
                // 重新初始化用户架构
                this.initOrganization();
              } else {
                ElMessage.error(resp.data.message);
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消操作'
            })
          })

    },
    // 是否禁用用户
    disableUser(data) {
      let u = JSON.parse(sessionStorage.getItem("user"));
      if (u.id === data.id) {
        ElMessage.error("自己不能禁止自己");
        return;
      }
      this.user.id = data.id;
      request.updateUser(data).then(resp => {
        if (resp.data.code === 200) {
          // 将user对象，各项值改为null
          this.initUser();
          ElMessage.success(resp.data.message);
          this.initAllUser(this.user);
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 删除用户
    deleteUser(data) {
      let u = JSON.parse(sessionStorage.getItem("user"));
      if (u.id === data.id) {
        ElMessage.error("目前登录用户无法删除");
        return;
      }
      request.deleteUser(JSON.stringify(data.id)).then(resp => {
        if (resp.data.code === 200) {
          this.initAllUser(this.user);
          ElMessage.success(resp.data.message);
        } else {
          ElMessage.error(resp.data.message);
        }
      });
    },
    // 取消添加用户
    cancelUser() {
      this.UserVisible = false;
      this.initUser();
      this.dialogTitle = '添加用户';
    },
    addUpdateUser() {
      if (this.dialogTitle === '添加用户') {
        if (this.user.username === null || this.user.password === null) {
          ElMessage.error("请输入用户名或密码");
          return;
        }
        request.addUser(this.user).then(resp => {
          if (resp.data.code === 200) {
            this.initUser();
            this.initAllUser(this.user);
            this.UserVisible = false;
            ElMessage.success(resp.data.message);
          } else {
            ElMessage.error(resp.data.message);
            this.initUser();
            this.UserVisible = false;
            ElMessage.success(resp.data.message);
          }
        })
      } else {
        this.user.state = null;
        request.updateUser(this.user).then(resp => {
              if (resp.data.code === 200) {
// 如果修改的是当前登录用户，则重新获取当前用户信息，并存入sessionStorage
                if (this.user.id === JSON.parse(sessionStorage.getItem("user")).id) {
                  request.getUserById(this.user.id).then(resp => {
                    if (resp.data.code === 200) {
                      sessionStorage.setItem("user", JSON.stringify(resp.data.user));
                    }
                  })
                }
                if (this.rid != null) {
                  request.updateUserRole(this.rid, this.user.id).then(resp => {
                    if (resp.data.code === 200) {
                      this.initUser();
                      this.initAllUser(this.user);
                    } else {
                      ElMessage.error(resp.data.message);
                    }
                  })
                }
                ElMessage.success(resp.data.message);
              } else {
                ElMessage.error(resp.data.message);
              }
              this.initUser();
              this.initAllUser(this.user);
              this.dialogTitle = '添加用户';
              this.rid = null;
              this.UserVisible = false;
            }
        );
      }
    }
    ,
    // 修改用户
    updateUser(data) {
      this.UserVisible = true;
      this.dialogTitle = '修改用户';
      this.user.id = data.id;
      this.user.username = data.username;
      this.user.phone = data.phone;
      this.user.email = data.email;
      this.user.address = data.address;
      this.user.embod = data.embod;
      this.user.state = data.state;
    }
  },
  watch: {
    username(newValue, oldValue) {
      if (newValue === '') {
        this.initAllUser(this.user);
      }
    },
    state(newValue, oldValue) {
      if (newValue === '') {
        this.initAllUser(this.user);
      }
    }
  },
  mounted() {
    this.initOrganization();
    this.initAllUser(this.user);
    this.initAllOrganization();
    request.getAllRoles().then(resp => {
      if (resp.data != null) {
        this.roles = resp.data;
      }
    })
  }
}
</script>

<style scoped>
.shadow-box {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4); /* 设置阴影效果 */
  border: 1px solid #ccc; /* 设置边框 */
  padding: 20px; /* 设置内边距，可根据需要进行调整 */
}

.container {
  display: flex;
  gap: 10px; /* 设置按钮之间的距离 */
}

</style>