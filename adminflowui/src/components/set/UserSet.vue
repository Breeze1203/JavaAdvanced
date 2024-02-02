<template>
  <div style="margin-top: 30px">
    <el-row :gutter="6" style="margin-bottom: 30px">
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      ><span>用户名:</span></el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7"
      >
        <el-input v-model="username" clearable placeholder="请输入用户名"/>
      </el-col>
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      ><span>用户状态:</span></el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="9" :xl="7"
      >
        <el-select
            v-model="state"
            :clearable="true"
            placeholder="状态"
        >
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-col>
    </el-row>
    <el-row>
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      >
        <el-button type="primary" plain icon="Search" @click="search">搜索</el-button>
      </el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7"
      >
        <el-button plain icon="Refresh" @click="initAllUser">刷新</el-button>
      </el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7"
      >
        <el-button :disabled="!permissions_data.includes('add_user')" plain type="info" @click="UserVisible = true"
                   icon="Plus">添加用户
        </el-button>
      </el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7"
      >
        <el-button :disabled="!permissions_data.includes('export_data')" plain type="success" icon="Upload">导出</el-button>
      </el-col>
    </el-row>
  </div>
  <div style="margin-top: 30px">
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
                      <el-button :disabled="!permissions_data.includes('update_user')" icon="Lock" type="text">禁用</el-button>
                   </span>
            </template>
          </el-popconfirm>
          <el-popconfirm title="确定要启用?" @confirm="disableUser(scope.row)" v-else>
            <template #reference>
                    <span style="margin-right: 10px;">
                    <el-button :disabled="!permissions_data.includes('update_user')" icon="Unlock"
                               type="text">启用</el-button>
                   </span>
            </template>
          </el-popconfirm>
          <el-button :disabled="!permissions_data.includes('update_user')" @click="updateUser(scope.row)" icon="Edit"
                     type="text"><span style="color: #666666">修改</span></el-button>
          <el-button :disabled="!permissions_data.includes('delete_user')" @click="deleteUser(scope.row)" icon="Delete"
                     type="text"><span style="color: red">删除</span></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
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
import {mapState} from "vuex";
import organization_request from "@/api/organization";


export default {
  name: 'UseSet',
  components: {Unlock},
  data() {
    return {
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
      userData: [],//用户表格数据
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
  computed: {
    ...mapState(["permissions_data"])
  },
  methods: {
    initAllOrganization() {
      organization_request.getAllOrganization().then(resp => {
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
    // 初始化所有用户信息
    initAllUser(user) {
      request.initAllUser(user).then(resp => {
        if (resp.data) {
          this.userData = resp.data;
          this.initUser();
        }
      })
    },
    // 按要求搜索用户
    search() {
      this.user.username = this.username;
      this.user.state = this.state;
      this.initAllUser(this.user);
      this.user.state = null;
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
                  request.getUserById(this.user.id).then(r => {
                    if (r.data) {
                      sessionStorage.setItem("user", JSON.stringify(r.data));
                    }
                  })
                }
                if (this.rid != null) {
                  console.log(this.rid);
                  request.updateUserRole(this.rid, this.user.id).then(re => {
                    if (re.data.code === 200) {
                      this.initUser();
                      this.initAllUser(this.user);
                    } else {
                      ElMessage.error(re.data.message);
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


</style>
