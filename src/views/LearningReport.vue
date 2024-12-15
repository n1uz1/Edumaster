<template>
  <div class="learning-report">
    <header class="header">
      <h1>学习报告</h1>
      <el-button @click="$router.push('/')">返回首页</el-button>
      <div class="user-info">xxxxxxx XXX</div>
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
        // 更多课程进度数据...
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