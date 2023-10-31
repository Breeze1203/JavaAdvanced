<template>
  <div class="login">
    <div id="login-box">
      <h1 class="welcome">Welcome</h1>
      <el-form>
        <el-form-item>
          <el-input prefix-icon="Avatar" type="text" v-model="username" placeholder="username" size="small"/>
        </el-form-item>
        <el-form-item>
          <el-input prefix-icon="Avatar" type="password" v-model="password" placeholder="password" size="small"/>
        </el-form-item>
        <el-form-item>
          <el-checkbox id="check" v-model="remember"><p style="font-size: 15px;font-weight: 700;color: #fff;">
            记住密码</p></el-checkbox>
        </el-form-item>
      </el-form>
      <el-button @click="login">Login</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/util/requestUtil";
import {ElMessage} from 'element-plus';
import store from "@/store";


export default {
  name: 'Login',
  data() {
    return {
      username: null,
      password: null,
      remember: false
    }
  },
  methods: {
    login() {
      if (this.username != null && this.password != null) {
        let rem = this.remember.toString();
        request.login(this.username, this.password, rem).then(resp => {
          if (resp.data.code === 200) {
            let token = this.getCookieValue(this.username + 'token');
            // 将token存入到store
            store.commit('getToken', token);
            // 获取当前用户信息
            let user=JSON.stringify(resp.data.user);
            sessionStorage.setItem("user",user);
            this.$router.push('/home');
          } else {
            ElMessage.error(resp.data.message);
          }
        })
      }else {
        ElMessage.error("用户名或密码不能为空");
      }
    }
    ,
    // 获取cookie
    getCookieValue(cookie) {
      //  按冒号分割cookie;
      let cookies = document.cookie.split(";");
      for (let i = 0; i < cookies.length; i++) {
        let strings = cookies[i].trim().split("=");
        if (strings[0] === cookie) {
          return strings[1];
        }
      }
    }
  }
  ,
  mounted() {
    let c1 = this.getCookieValue("username");
    let c2 = this.getCookieValue("password");
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
  background-image: url("@/assets/background.jpg");
  background-size: cover;
  display: flex; /* 将div1设置为Flex容器 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.welcome {
  animation: rainbow 10s infinite;
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
  width: 25%;
  height: auto;
  margin: 10%;
  /*margin: 0 auto;*/
  /*margin-top: 13%;*/
  text-align: center;
  padding: 20px 50px;
}

#login-box h1 {
  color: #fff;
}

#check {
}

#login-box button {
  margin-top: 15px;
  width: 190px;
  height: 30px;
  font-size: 20px;
  font-weight: 700;
  color: #fff;
  background-image: linear-gradient(to right, #74ebd5 0%, #9face6 100%);
  border: 0;
  border-radius: 15px;
}

</style>