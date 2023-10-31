<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="head">AdminFlow</div>
        <div class="header-wrapper">
          <el-avatar size="large" class="avater" :src="userInfo.userFace"/>
          <el-dropdown trigger="hover">
            <p style="font-weight: bolder;color: white">{{ userInfo.username }}</p>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="show">个人中心</el-dropdown-item>
                <el-dropdown-item @click="showSetting">基本设置</el-dropdown-item>
                <el-dropdown-item @click="loginOut">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
          <el-aside width="200px">
            <div class="common-layout">
              <el-menu style="height: 100%">
                <el-sub-menu index="1">
                <template #title>
                  <el-icon><Setting /></el-icon>
                  <span>系统管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item>
                    <el-icon><UserFilled /></el-icon>用户管理</el-menu-item>
                  <el-menu-item><el-icon><Tickets /></el-icon>角色管理</el-menu-item>
                  <el-menu-item><el-icon><Collection /></el-icon>权限管理</el-menu-item>
                </el-menu-item-group>
              </el-sub-menu>
                <el-sub-menu>
                  <template #title>
                    <el-icon><Timer /></el-icon>
                    <span>日志管理</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item @click="change" index="/log">
                      <el-icon><EditPen /></el-icon>操作日志</el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>
              </el-menu>
            </div>
        </el-aside>
        <el-main>
          <div v-if="this.$router.currentRoute.value.path==='/home'">
            <div class="welcome-container" style="height: 100px">
              <h2 class="welcome-message">欢迎登录</h2>
              <p class="greeting">亲爱的{{ userInfo.username }}，欢迎进入adminFlow</p>
            </div>
            <div id="main" style="width: 100%;height:350px">
            </div>
          </div>
          <el-breadcrumb v-if="this.$router.currentRoute.value.path!=='/home'">
            <el-breadcrumb-item @click="toHome">返回首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$router.currentRoute.value.name }}</el-breadcrumb-item>
          </el-breadcrumb>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
  <el-dialog
      v-model="upWord"
      title="修改密码"
      width="30%"
      align-center
  >
    <span>
      <el-form-item label="旧密码" size="small">
        <el-input type="text" size="small"/>
      </el-form-item>
      <el-form-item label="新密码" size="small">
        <el-input type="text" size="small"/>
      </el-form-item>
    </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button>取消</el-button>
        <el-button type="primary">
         确定
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-drawer v-model="showUser" title="个人信息" size="23%">
    <el-form>
      <el-form-item label="头像：">
        <el-avatar size="samll" :src="userInfo.userFace"/>
      </el-form-item>
      <el-form-item label="用户名：">{{ userInfo.username }}</el-form-item>
      <el-form-item label="地址：">{{ userInfo.address }}</el-form-item>
      <el-form-item label="电话号码：">{{ userInfo.phone }}</el-form-item>
      <el-form-item label="邮箱地址：">{{ userInfo.email }}</el-form-item>
      <el-form-item label="个性签名：">{{ userInfo.embod }}</el-form-item>
    </el-form>
  </el-drawer>
  <el-dialog v-model="showSet" width="30%" title="修改用户信息">
    <template #default>
      <el-form size="small">
        <el-form-item label="用户名：">
          <el-input v-model="user.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="地址：">
          <el-input v-model="user.address" placeholder="请输入地址"/>
        </el-form-item>
        <el-form-item label="电话号码：">
          <el-input v-model="user.phone" placeholder="请输入电话号码"/>
        </el-form-item>
        <el-form-item label="邮箱地址：">
          <el-input v-model="user.email" placeholder="请输入邮箱地址"/>
        </el-form-item>
        <el-form-item label="个性签名：">
          <el-input v-model="user.embod" placeholder="请输入个性签名"/>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button @click="update" type="primary">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>>

<script>
import request from "@/util/requestUtil";
import * as echarts from 'echarts';
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router"
import store from "@/store";

export default {
  name: "Home",
  data() {
    return {
      user: {
        id: null,
        username: null,
        password: null,
        address: null,
        phone: null,
        email: null,
        embod: null
      },
      showUser: false,
      showSet: false,
      upWord: false,
      userInfo:JSON.parse(sessionStorage.getItem("user"))
    }
  },
  methods: {
    // 退出登录
    loginOut() {
      ElMessageBox.confirm(
          "此操作将退出登录,是否继续",
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      )
          .then(() => {
            console.log(this.userInfo.username);
            request.loginOut(this.userInfo.username).then(resp => {
              if (resp.data.code === 200) {
                console.log(resp.data);
                // 删除用户信息
                sessionStorage.removeItem("user");
                // 删除token
                store.state.token=null;
                router.push("/");
                ElMessage.success(resp.data.message);
              } else {
                ElMessage.error("网络出现异常，请稍后再试");
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '已取消操作'
            })
          })
    },
    // 展开菜单
    change(data) {
      console.log(data.index);
      router.push(data.index);
    },
    show() {
      this.showUser = true;
    }
    ,
    showSetting() {
      this.showSet = true;
    }
    ,
    // 将user里面某些属性变为空
    init() {
      this.user.username = null;
      this.user.password = null;
      this.user.embod = null;
      this.user.email = null;
      this.user.phone = null;
      this.user.address = null;
    }
    ,
    // 取消修改用户
    cancel() {
      this.init();
      ElMessage.info("取消修改");
      this.showSet = false;
    }
    ,
    // 确定修改用户
    update() {
      let user = JSON.stringify(this.user);

    },
    //修改密码显示dialog
    upPass() {
      this.upWord = true;
    },
    // 回到 /home路径
    toHome(){
      this.initCount();
      router.push('/home');
    },
    // 初始化折现图
    initCount() {
      request.getCount().then(resp => {
        if (resp.data != null) {
          // 基于准备好的dom，初始化echarts实例
          let myChart = echarts.init(document.getElementById('main'));
          window.addEventListener('resize', function () {
            myChart.resize({
              width: 800,
              height: 300
            });
          });
          const option = {
            tooltip: {
              trigger: 'axis'
            },
            xAxis: {
              type: 'category',
              name: '日期',
              boundaryGap: false,
              data: resp.data.numbers
            },
            yAxis: {
              type: 'value',
              name: '访问人数',
            },
            series: [
              {
                name: '登录人数',
                type: 'line',
                smooth: true,
                data: resp.data.count
              },
            ]
          };
          // 绘制图表
          myChart.setOption(option);
        }
      })
    }
  },
  mounted() {
    this.initCount();
  },
}
</script>
<style scoped>
.head {
  height: 70px;
  line-height: 70px;
  font-family: "Fira Code Medium", sans-serif;
  font-variation-settings: 'wght' 700, 'slnt' 50;
  font-size: 20px;
  text-shadow: 4px 6px 8px rgba(0, 0, 0, 0.5);
  text-decoration: rgba(73, 73, 245, 0.25);
  animation: rainbow 10s infinite;
  font-stretch: extra-condensed;
}

.header {
  display: flex;
  justify-content: space-between;
  background-color: rgba(0, 0, 0, 0.65);
  border-bottom: 1px solid rgba(70, 43, 26, 0.85);
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  align-items: center;
}

.avater {
  margin-right: 10px;
}

.welcome-container {
  padding: 20px;
  text-align: center;
}

.welcome-message {
  animation: rainbow 10s infinite;
  font-size: 24px;
  color: #333;
}

.greeting {
  text-align: center;
  font-size: 18px;
  color: #666;
  margin-bottom: 10px;
}

.header-wrapper {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 20px;
}

@keyframes rainbow {
  10% {
    color: red
  }
  14% {
    color: orange
  }
  28% {
    color: yellow
  }
  42% {
    color: green
  }
  57% {
    color: blue
  }
  71% {
    color: indigo
  }
  85% {
    color: violet
  }
  100% {
    color: red
  }
}

.dialog-footer button:first-child {
  margin-right: 5px;
}

.mainView {
  margin-top: 10px;
}

</style>