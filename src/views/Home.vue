<template>
  <div class="home">
    <header class="header">
      <div class="header-left">
        <h1>首页</h1>
      </div>
      <div class="header-center">
        <el-button 
          class="menu-button" 
          @mouseenter="showLearningMenu = true"
          @mouseleave="showLearningMenu = false"
        >
          课程学习
          <div class="sub-menu" v-show="showLearningMenu">
            <router-link to="/course-management">课程管理</router-link>
            <router-link to="/assignments">在线作业与考试</router-link>
            <router-link to="/social-learning">社交学习</router-link>
            <router-link to="/learning-report">生成学习报告</router-link>
            <router-link to="/discussion">互动与讨论</router-link>
          </div>
        </el-button>
        <el-button 
          class="menu-button"
          @mouseenter="showAdviceMenu = true"
          @mouseleave="showAdviceMenu = false"
        >
          学习建议
          <div class="sub-menu" v-show="showAdviceMenu">
            <router-link to="/course-recommendation">用户课程推荐</router-link>
            <router-link to="/learning-path">学习路径建议</router-link>
          </div>
        </el-button>
      </div>
      <div class="header-right">
        <el-button type="primary" @click="showLoginDialog = true">登录</el-button>
        <span class="user-info" v-if="isLoggedIn">xxxxxxx XXX</span>
      </div>
    </header>

    <!-- 登录弹窗 -->
    <el-dialog v-model="showLoginDialog" title="用户登录" width="30%">
      <el-form :model="loginForm" label-width="80px">
        <el-form-item label="账号">
          <el-input v-model="loginForm.username" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showLoginDialog = false">取消</el-button>
          <el-button type="primary" @click="handleLogin">登录</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      showLoginDialog: false,
      isLoggedIn: false,
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    handleLogin() {
      // 这里添加登录逻辑
      if (this.loginForm.username && this.loginForm.password) {
        this.isLoggedIn = true
        this.showLoginDialog = false
        this.$message.success('登录成功')
      } else {
        this.$message.error('请输入账号和密码')
      }
    }
  }
}
</script>

<style scoped>
.home {
  background-color: #e8f5e9;
  min-height: 100vh;
}

.header {
  background-color: #e3f2fd;
  padding: 15px 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left h1 {
  margin: 0;
  font-size: 24px;
}

.header-center {
  display: flex;
  gap: 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.menu-button {
  position: relative;
  height: 40px;
}

.sub-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  z-index: 1000;
  min-width: 150px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  margin-top: 5px;
}

.user-info {
  font-size: 16px;
}

a {
  text-decoration: none;
  color: #333;
  padding: 5px 10px;
  display: block;
  white-space: nowrap;
}

a:hover {
  background: #f0f0f0;
  border-radius: 4px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}
</style> 