<template>
  <div class="main">
    <p>项目介绍：adminflow，基于SpringBoot3.0，Vue3实现，未使用安全框架，优雅实现权限控制功能</p>
    <p>技术栈：SpringBoot,Redis,rocketMQ,Vue3,Axios,Element Plus，Echarts等</p>
    <label>主要特征
      <ul>
        <li>实现两种方式登录，用户名或阿里云搭配redis实现手机验证码登录</li>
        <li>未使用安全框架，自定义RBAC权限访问认证</li>
        <li>生成jwt令牌，利用Redis特性设置令牌过期时间等，所有接口授权才能访问</li>
        <li>使用redis进行会话管理及项目访问人数记录</li>
        <li>rocketMQ搭配阿里云短信模版实现短信业务逻辑，例如，修改密码</li>
        <li>运用SpringAop对关键操作进行日志记录</li>
      </ul>
    </label>
  </div>
</template>

<script>
export default {
  name: 'About'
}

</script>

<style scoped>
.main {
  margin-top: 25px;
  /*box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4); !* 设置阴影效果 *!*/
  /*border: 1px solid #ccc; !* 设置边框 *!*/
  padding: 20px; /* 设置内边距，可根据需要进行调整 */
}
</style>
