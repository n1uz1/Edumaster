<template>
  <div class="home">
    <header class="header">
      <h1>首页</h1>
      <div class="user-info">xxxxxxx XXX</div>
    </header>

    <div class="menu-container">
      <div class="menu-section" 
           @mouseenter="showLearningMenu = true" 
           @mouseleave="showLearningMenu = false">
        <h2>课程学习</h2>
        <div class="sub-menu" v-show="showLearningMenu">
          <router-link to="/course-management">课程管理</router-link>
          <router-link to="/assignments">在线作业与考试</router-link>
          <router-link to="/social-learning">社交学习</router-link>
          <router-link to="/learning-report">生成学习报告</router-link>
          <router-link to="/discussion">互动与讨论</router-link>
        </div>
      </div>

      <div class="menu-section"
           @mouseenter="showAdviceMenu = true"
           @mouseleave="showAdviceMenu = false">
        <h2>学习建议</h2>
        <div class="sub-menu" v-show="showAdviceMenu">
          <router-link to="/course-recommendation">用户课程推荐</router-link>
          <router-link to="/learning-path">学习路径建议</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false
    }
  }
}
</script>

<style scoped>
.home {
  background-color: #e8f5e9;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #e3f2fd;
  padding: 15px 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header h1 {
  margin: 0;
  font-size: 24px;
}

.user-info {
  font-size: 16px;
}

.menu-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 50px;
}

.menu-section {
  position: relative;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
  background-color: white;
}

.menu-section h2 {
  margin: 0;
  text-align: center;
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
}

a {
  text-decoration: none;
  color: #333;
  padding: 5px 10px;
}

a:hover {
  background: #f0f0f0;
  border-radius: 4px;
}
</style> 