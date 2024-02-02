<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="head">AdminFlow</div>
        <div class="header-wrapper">
          <div style="margin-right: 30px;">
            <el-icon size="23" v-if="isCollapse!=true" style="color: #ffffff" @click="isCollapse=!isCollapse">
              <Fold/>
            </el-icon>
            <el-icon size="23" v-if="isCollapse!=false" style="color: #ffffff" @click="isCollapse=!isCollapse">
              <Expand/>
            </el-icon>
          </div>
          <div style="margin-right: 30px;">
            <el-icon size="23" style="color: #ffffff" @click="showMessage=true">
              <Edit/>
            </el-icon>
          </div>
          <div style="margin-right: 30px;">
            <el-icon size="23" @click="fill" style="color: #ffffff">
              <FullScreen/>
            </el-icon>
          </div>
          <div style="margin-right: 30px;">
            <el-badge :value="messCount" type="info" :max="10">
              <el-dropdown trigger="click" @click="initMess">
                <el-icon size="23" style="color: #ffffff;border: none">
                  <Bell/>
                </el-icon>
                <template #dropdown>
                  <el-dropdown-menu>
                    <div style="width: 300px;">
                      <div v-for="(item,index) in messages" :key="index"
                           style="border-bottom: 1px solid;margin-left: 10px;margin-right:10px;display: flex;">
                        <div v-if="item.send_id===userInfo.id">
                          <el-avatar size="small" :src="userInfo.userFace"/>
                        </div>
                        <div v-if="item.send_id===userInfo.id"
                             style="width: 100%;margin-left: 10px">
                          <el-row>我发送给{{ item.receive_user.username }}：{{ item.content }}</el-row>
                          <el-row>
                            <el-col style="color: orange" :span="9">{{ item.time }}</el-col>
                            <el-col :span="10">
                              <span style="color: red" @click="deleteMess(item.id)">删除</span>
                            </el-col>
                          </el-row>
                        </div>
                        <div v-if="item.send_id!==userInfo.id">
                          <el-avatar size="small" :src="item.send_user.userFace"/>
                        </div>
                        <div v-if="item.send_id!==userInfo.id"
                             style="width: 100%;margin-left: 10px">
                          <el-row>{{ item.send_user.username }}发送给我：{{ item.content }}</el-row>
                          <el-row>
                            <el-col style="color: orange" :span="9">{{ item.time }}</el-col>
                            <el-col :span="10">
                              <el-space>
                                <span style="color: red" @click="deleteMess(item.id)">删除</span>
                                <span style="color: dimgray" @click="replyMessage(item.send_id)">回复</span>
                                <span v-if="item.state===false" style="color: darkcyan"
                                      @click="havaRead(item.id)">已读</span>
                              </el-space>
                            </el-col>
                          </el-row>
                        </div>
                      </div>
                    </div>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </el-badge>
          </div>
          <el-avatar size="large" class="avater" :src="userInfo.userFace"/>
          <el-dropdown trigger="hover">
            <p style="font-weight: bolder;color: white">{{ userInfo.username }}</p>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="showUser=true">
                  <el-icon>
                    <User/>
                  </el-icon>
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="this.showSet = true;">
                  <el-icon>
                    <Lock/>
                  </el-icon>
                  密码设置
                </el-dropdown-item>
                <el-dropdown-item @click="loginOut">
                  <el-icon>
                    <DCaret/>
                  </el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <div>
            <el-menu :collapse="isCollapse" v-for="(item,index) in menu" :key="index" @select="handleOpen">
              <el-sub-menu :index="index">
                <template #title>
                  <el-icon>
                    <component :is="item.icon"/>
                  </el-icon>
                  <span>{{ item.menu_name }}</span>
                </template>
                <el-menu-item v-for="(child,index) in item.menus" @click=change :index="child.path">
                  <el-icon>
                    <component :is="child.icon"/>
                  </el-icon>
                  {{ child.menu_name }}
                </el-menu-item>
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
          <div>
            <el-breadcrumb v-if="this.$router.currentRoute.value.path!=='/home'">
              <el-breadcrumb-item @click="toHome">首页</el-breadcrumb-item>
              <el-breadcrumb-item>{{ this.$router.currentRoute.value.name }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div v-if="this.$router.currentRoute.value.path!=='/home'"
               style="border-bottom:1px solid #cccccc;margin-top: 20px">
            <el-tag
                v-for="tag in Tags"
                class="mx-1"
                :key="tag"
                closable
                @click="handleChange(tag)"
                @close="handleClose(tag)"
            >
              {{ tag }}
            </el-tag>
          </div>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
  <el-drawer v-model="showUser" title="个人信息" size="23%">
    <el-descriptions style="height: 100%"
                     :column="1"
                     size="large"
                     border
    >
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon size="25">
              <el-icon>
                <Picture/>
              </el-icon>
            </el-icon>
          </div>
        </template>
        <el-avatar size="samll" :src="userInfo.userFace"/>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div>
            <el-icon size="25">
              <User/>
            </el-icon>
          </div>
        </template>
        {{ userInfo.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div>
            <el-icon size="25">
              <Location/>
            </el-icon>
          </div>
        </template>
        {{ userInfo.address }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon size="25">
              <iphone/>
            </el-icon>
          </div>
        </template>
        {{ userInfo.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div>
            <el-icon size="25">
              <Promotion/>
            </el-icon>
          </div>
        </template>
        {{ userInfo.email }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div>
            <el-icon size="25">
              <Tickets/>
            </el-icon>
          </div>
        </template>
        {{ userInfo.embod }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon size="25">
              <OfficeBuilding/>
            </el-icon>
          </div>
        </template>
        {{ userInfo.organization.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon size="25">
              <Avatar/>
            </el-icon>
          </div>
        </template>
        {{ userInfo.role.nameZh }}
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
  <el-dialog v-model="showSet" width="30%" title="修改用户密码">
    <template #default>
      <el-form size="small">
        <el-form-item label="新密码：">
          <el-input type="password" v-model="userInfo.password" placeholder="请输入新密码"/>
        </el-form-item>
        <el-form-item label="确认密码：">
          <el-input type="password" v-model="newPassword" placeholder="请再次输入密码"/>
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
  <el-dialog style="margin-top: 10%" v-model="showMessage" width="30%" title="发送私信">
    <el-form>
      <el-form-item label="发送方">
        <el-input size="small" v-model="userInfo.username" disabled/>
      </el-form-item>
      <el-form-item label="接收方">
        <el-select placeholder="请选择接收方" v-model="replyUser" size="small">
          <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="内容">
        <el-input
            :autosize="{ minRows: 2, maxRows: 4 }"
            type="textarea"
            placeholder="请输入私信内容"
            v-model="content"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="cancelReply">取消</el-button>
        <el-button type="primary" @click="sureReply">
          发送
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
import {Delete} from "@element-plus/icons-vue";
import store from "@/store";
import menu_request from "@/api/menu";
import {mapState} from "vuex";
import permissions_request from "@/api/permissions";




export default {
  name: "Home",
  components: {Delete},
  data() {
    return {
      isCollapse: false,//menu是否展开
      showMessage: false,
      newPassword: null,
      showUser: false,
      showSet: false,
      userInfo:JSON.parse(sessionStorage.getItem("user")),
      users: [],// 除当前登录用户外的所有用户
      content: null,
      messCount: 0, // 未读消息数量
      replyUser: null, // 回复的用户
      messages: [],
    }
  },
  computed: {
    ...mapState(["menu"]),
    ...mapState(["Tags"]),
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
            request.loginOut(this.userInfo.id).then(resp => {
              if (resp.data.code === 200) {
                // 删除用户信息
                sessionStorage.removeItem("user");
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
    // 取消修改密码
    cancel() {
      this.userInfo.password = null;
      this.newPassword = null
      ElMessage.info("取消修改");
      this.showSet = false;
    }
    ,
    // 确定修改用户
    update() {
      if (this.userInfo.password == null || this.newPassword == null) {
        ElMessage.error("请输入密码");
        return;
      }
      if (this.userInfo.password !== this.newPassword) {
        ElMessage.error("两次密码不一致，请重新输入");
        this.userInfo.password = null;
        this.newPassword = null;
        return;
      }
      request.updatePassword(this.userInfo).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          request.loginOut(this.userInfo.id).then(resp => {
            if (resp.data.code === 200) {
              // 删除用户信息
              sessionStorage.removeItem("user");
              ElMessage.success("密码修改成功，请重新登录");
              router.push("/");
            } else {
              ElMessage.error("网络出现异常，请稍后再试");
            }
          })
        } else {
          ElMessage.error(resp.data.message);
        }
        this.showSet = false;
      })
    },
    // 展开菜单
    change(data) {
      router.push(data.index);
      const routes = this.$router.options.routes;
      for (let i = 0; i < routes[1].children.length; i++) {
        if (routes[1].children[i].path === data.index) {
          if (!this.Tags.includes(routes[1].children[i].name)) {
            this.Tags.push(routes[1].children[i].name);
          }
          break; // 找到目标项后，退出循环
        }
      }
    },
    // 跳到关于项目介绍
    toHome() {
      router.push('/home');
      this.initCount();
    },
    // 全屏显示
    fill() {
      if (document.fullscreenElement) {
        document.exitFullscreen();
      } else {
        document.documentElement.requestFullscreen();
      }
    }
    ,
    // 初始化折现图
    initCount() {
      request.getCount().then(resp => {
        if (resp.data != null) {
          const date = Object.values(resp.data.numbers);
          date.unshift('日期')
          const count = Object.values(resp.data.count);
          count.unshift('项目访问人数')
          let chartDom = document.getElementById('main');
          let myChart = echarts.init(chartDom);
          let option;
          option = {
            legend: {},
            tooltip: {},
            dataset: {
              source: [
                date,
                count
              ]
            },
            xAxis: [
              {type: 'category', gridIndex: 0},
              {type: 'category', gridIndex: 1}
            ],
            yAxis: [{gridIndex: 0}, {gridIndex: 1}],
            grid: [{bottom: '55%'}, {top: '55%'}],
            series: [
              {type: 'bar', seriesLayoutBy: 'row'},
              {type: 'bar', xAxisIndex: 1, yAxisIndex: 1},
              {type: 'bar', xAxisIndex: 1, yAxisIndex: 1},
              {type: 'bar', xAxisIndex: 1, yAxisIndex: 1},
              {type: 'bar', xAxisIndex: 1, yAxisIndex: 1}
            ]
          };
          option && myChart.setOption(option);
        }
      })
    }, // 切换tag
    handleChange(tag) {
      if (tag === '首页') {
        router.push('/home');
        this.initCount();
      } else {
        let routes = this.$router.options.routes;
        for (let i = 0; i < routes[1].children.length; i++) {
          if (routes[1].children[i].name === tag) {
            router.push(routes[1].children[i].path);
            break;
          }
        }
      }
    },
    // 关闭tag
    handleClose(tag) {
      let Tag = this.Tags.filter(item => item !== tag);
      store.commit("initTag",Tag)
      if (this.Tags.length <= 1) {
        this.initCount();
        router.push('/home');
      } else {
        router.go(-1);
      }
    },
    // 消息初始化
    initMess() {
      // 消息初始化
      request.MessageInit(JSON.parse(sessionStorage.getItem("user")).id).then(resp => {
        if (resp) {
          this.messages = resp.data;
          this.messCount = 0;
          for (let i = 0; i < resp.data.length; i++) {
            if (resp.data[i].state === false && resp.data[i].send_id !== this.userInfo.id) {
              this.messCount++;
            }
          }
        }
      })
    },
    // 取消发私信
    cancelReply() {
      this.showMessage = false;
      this.replyUser = null;
    },
    // 发私信
    replyMessage(data) {
      this.replyUser = data;
      this.showMessage = true;
    },
    // 确认发私信
    sureReply() {
      if (this.content == null) {
        ElMessage.error("请输入私信内容");
        return;
      }
      request.sendMess(this.userInfo.id, this.replyUser, this.content).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.initMess();
        } else {
          ElMessage.error(resp.data.message);
        }
      });
      this.showMessage = false;
      this.replyUser = null;
      this.content = null;
    },
    // 消息已读
    havaRead(data) {
      request.upState(data).then(resp => {
        if (resp.data.code === 200) {
          this.initMess();
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 删除消息
    deleteMess(id) {
      // 这里的删除消息并不是去数据库真正删除消息，只是在后端返回来的消息过滤一下
      request.deleteMessage(this.userInfo.id, id).then(resp => {
        if (resp.data.code === 200) {
          this.initMess();
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    },

  },
  mounted() {
    // 折线图初始化
    this.initCount();
    request.WithOutUser(JSON.parse(sessionStorage.getItem("user")).id).then(resp => {
      if (resp.data) {
        this.users = resp.data;
      }
    });
    menu_request.getMenusByRole(JSON.parse(sessionStorage.getItem("user")).role.id).then(resp => {
      store.commit("initMenu", resp.data);
    });
    this.initMess();
    // 获取权限
    permissions_request.getPermissionByRole(JSON.parse(sessionStorage.getItem("user")).role.id).then(resp => {
      if (resp.data != null) {
        const permissions = [];
        for (let i = 0; i < resp.data.length; i++) {
          permissions.push(resp.data[i].name);
        }
        store.commit('initPermission', permissions);
      }
    })
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
  padding: 10px;
  height: 100px;
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

.mx-1 {
  margin-right: 1rem;
}

</style>
