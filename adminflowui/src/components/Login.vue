<template>
  <div class="login">
    <div id="login-box">
      <div class="left-div">
      </div>
      <div class="right-div">
        <h1 class="welcome">AdminFlow</h1>
        <el-tabs v-model="activeName">
          <el-tab-pane label="用户名登录" name="first">
            <el-form>
              <el-form-item size="default">
                <el-input prefix-icon="Avatar" type="text" v-model="username" placeholder="username"/>
              </el-form-item>
              <el-form-item size="default">
                <el-input prefix-icon="Lock" type="password" :show-password="true" v-model="password"
                          placeholder="password">
                </el-input>
              </el-form-item>
              <div style="display: flex; justify-content: space-between;height: 30px">
                <el-input v-model="Verification" type="text" placeholder="请输入验证码">
                </el-input>
                <canvas @click="generateCode" ref="canvas" style="height: 100%;width: 100%;"></canvas>
              </div>
              <el-form-item size="default">
                <el-checkbox id="check" v-model="remember"><p style="font-size: 15px;font-weight: 700">
                  记住我</p></el-checkbox>
              </el-form-item>
            </el-form>
            <el-button @click="loginByName" class="loginButton">Login</el-button>
          </el-tab-pane>
          <el-tab-pane label="验证码登录" name="two">
            <el-form>
              <el-form-item>
                <el-input prefix-icon="Avatar" type="text" v-model="phoneNumber" placeholder="请输入手机号"/>
              </el-form-item>
              <div style="display: flex; justify-content: space-between;height: 30px">
                <el-input prefix-icon="Cellphone" v-model="verificationCode" type="text" placeholder="请输入验证码"/>
                <el-button @click="getCode" style="width: 140px;border: none">获取验证码</el-button>
              </div>
              <el-form-item>
                <el-checkbox id="check" v-model="rememberWithPhone"><p style="font-size: 15px;font-weight: 700">
                  记住我</p></el-checkbox>
              </el-form-item>
            </el-form>
            <el-button @click="loginByPhone" class="loginButton">Login</el-button>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/util/requestUtil";
import {ElMessage} from 'element-plus';
import {getCookie} from "@/util/cookieUtil";
import router from "@/router";
import store from "@/store";


export default {
  name: 'Login',
  data() {
    return {
      username: null,
      password: null,
      remember: false,
      activeName: 'first',
      Verification: null,//验证码表单
      code: null,
      verificationCode: null,
      rememberWithPhone: false,//验证码登录是否记住密码
      phoneNumber: null
    }
  },
  methods: {
    // 用户名登录
    loginByName() {
      if (this.username != null && this.password != null) {
        if (this.Verification == null) {
          ElMessage.error("请输入验证码");
          return;
        }
        if (this.Verification !== this.code) {
          ElMessage.error("验证码错误，请重新输入");
          this.Verification = null;
          this.generateCode();
          return;
        }
        let rem = this.remember.toString();
        request.login(this.username, this.password, rem).then(resp => {
          if (resp.data.code === 200) {
            let user = JSON.stringify(resp.data.user);
            sessionStorage.setItem("user", user);
            router.push('/home');
          } else {
            ElMessage.error(resp.data.message);
          }
        })
      } else {
        ElMessage.error("用户名或密码不能为空");
      }
    },
    // 短信验证码登录
    loginByPhone() {
      if (this.verificationCode == null) {
        ElMessage.error("请输入短信验证码");
        return;
      }
      request.loginByPhone(this.phoneNumber, this.verificationCode, this.rememberWithPhone).then(resp => {
        if(resp.data.code===200){
          // 获取当前用户信息
          let user = JSON.stringify(resp.data.user);
          sessionStorage.setItem("user", user);
          router.push('/home');
          this.phoneNumber=null;
          this.verificationCode=null;
        }else {
          ElMessage.error(resp.data.message);
        }
      })
    }
    ,//获取验证码
    getCode() {
      if (this.phoneNumber == null) {
        ElMessage.error("请输入手机号");
        return;
      }
      request.getCode(this.phoneNumber).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 生成验证码
    generateCode() {
      const canvas = this.$refs.canvas;
      const context = canvas.getContext('2d');
      const width = canvas.width;
      const height = canvas.height;
      // 生成随机验证码
      const code = this.generateRandomCode();
      this.code = code;
      // 绘制验证码
      context.clearRect(0, 0, width, height);

      // 随机生成彩色字体
      let colors = ["#FF0000", "#00FF00", "#0000FF", "#FFA500", "#800080"];
      let randomColor = colors[Math.floor(Math.random() * colors.length)];
      context.font = '60px Arial';
      context.fillStyle = randomColor;
      context.fillText(code, 90, 120);

// 添加干扰线
      for (let i = 0; i < 5; i++) {
        // 随机位置和颜色
        let x1 = Math.random() * width;
        let y1 = Math.random() * height;
        let x2 = Math.random() * width;
        let y2 = Math.random() * height;
        let lineColor = colors[Math.floor(Math.random() * colors.length)];

        // 绘制线段
        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.strokeStyle = lineColor;
        context.lineWidth = 1;
        context.stroke();
      }
    },
    generateRandomCode() {
      // 随机生成一个四位数验证码
      let code = '';
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      for (let i = 0; i < 4; i++) {
        const randomIndex = Math.floor(Math.random() * characters.length);
        code += characters.charAt(randomIndex);
      }
      return code;
    },
  }
  ,
  mounted() {
    this.generateCode();
    let c1 = getCookie("username");
    let c2 = getCookie("password");
    if (c1 !== null && c2 !== null) {
      this.username = c1;
      this.password = c2;
    }
  }
}
</script>

<style scoped>
.login {
  margin-top: 0;
  padding: 0;
  position: relative;
  background-size: cover;
  display: flex; /* 将div1设置为Flex容器 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.welcome {
  animation: rainbow 10s infinite;
}

.left-div {
  float: left;
  width: 50%;
  height: 100%;
  top: 0;
  background-image: url('@/assets/background.jpeg');
  margin-right: 15px; /* 调整左右两个子元素之间的间距 */
  background-size: cover; /* 完全覆盖容器 */
}

.right-div {
  float: right;
  width: 50%;
  height: 100%;
  margin-right: 15px; /* 调整左右两个子元素之间的间距 */
  top: 0;
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


#login-box {
  width: 50%;
  height: 370px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: space-between;
  margin: 10%;
  padding: 30px 20px;
}

#login-box h1 {
  color: #fff;
}


.loginButton {
  margin-top: 15px;
  width: 100%;
  height: 30px;
  font-size: 20px;
  font-weight: 700;
  color: #fff;
  background-image: linear-gradient(to right, #74ebd5 0%, #9face6 100%);
  border: 0;
  border-radius: 15px;
}

</style>
