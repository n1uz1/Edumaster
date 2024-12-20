<template>
  <div class="learning-report">
    <header class="header">
      <div class="header-left">
        <h1>学习报告</h1>
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
        <el-button @click="$router.push('/')">返回首页</el-button>
        <div class="user-info">xxxxxxx XXX</div>
      </div>
    </header>

    <div class="report-content">
      <div class="report-section">
        <h2>学习概览</h2>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-card class="stat-card">
              <h3>总学习时长</h3>
              <div class="stat-value">{{ stats.totalHours }}小时</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="stat-card">
              <h3>完成课程数</h3>
              <div class="stat-value">{{ stats.completedCourses }}门</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="stat-card">
              <h3>平均成绩</h3>
              <div class="stat-value">{{ stats.averageScore }}分</div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <div class="report-section">
        <h2>学习进度</h2>
        <div class="progress-list">
          <el-card v-for="course in courseProgress" :key="course.id">
            <div class="progress-item">
              <div class="course-info">
                <h4>{{ course.name }}</h4>
                <p>{{ course.description }}</p>
              </div>
              <el-progress 
                :percentage="course.progress"
                :status="course.progress === 100 ? 'success' : ''"
              />
            </div>
          </el-card>
        </div>
      </div>

      <div class="report-section">
        <h2>学习建议</h2>
        <el-card>
          <div class="suggestions">
            <div v-for="(suggestion, index) in suggestions" :key="index" class="suggestion-item">
              <el-icon><InfoFilled /></el-icon>
              <span>{{ suggestion }}</span>
            </div>
          </div>
        </el-card>
      </div>

      <div class="export-section">
        <el-button type="primary" @click="exportReport">导出报告内容</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LearningReport',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      stats: {
        totalHours: 120,
        completedCourses: 5,
        averageScore: 85
      },
      courseProgress: [
        {
          id: 1,
          name: 'Web开发基础',
          description: '学习HTML、CSS和JavaScript基础',
          progress: 80
        }
      ],
      suggestions: [
        '建议加强JavaScript的练习',
        '可以尝试参与更多实战项目',
        '建议多与学习伙伴交流讨论'
      ]
    }
  },
  methods: {
    exportReport() {
      this.$message.success('报告导出功能待实现')
    }
  }
}
</script>

<style scoped>
.learning-report {
  background-color: #e8f5e9;
  min-height: 100vh;
  padding: 20px;
}

.header {
  background-color: #e3f2fd;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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

.report-content {
  max-width: 1200px;
  margin: 0 auto;
}

.report-section {
  margin-bottom: 30px;
}

.stat-card {
  text-align: center;
  padding: 20px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  margin-top: 10px;
}

.progress-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.progress-item {
  padding: 10px;
}

.course-info {
  margin-bottom: 10px;
}

.course-info h4 {
  margin: 0 0 5px 0;
}

.course-info p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.suggestions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.suggestion-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.export-section {
  text-align: center;
  margin-top: 30px;
}
</style> 