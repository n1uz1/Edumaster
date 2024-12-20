<template>
  <div class="learning-path">
    <header class="header">
      <div class="header-left">
        <h1>学习路径建议</h1>
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

    <div class="path-content">
      <div class="path-selection">
        <h3>选择学习方向</h3>
        <el-select v-model="selectedPath" placeholder="请选择学习方向" @change="handlePathChange">
          <el-option
            v-for="path in learningPaths"
            :key="path.value"
            :label="path.label"
            :value="path.value"
          />
        </el-select>
      </div>

      <div v-if="selectedPath" class="path-details">
        <div class="path-overview">
          <h3>{{ currentPath.label }}学习路径</h3>
          <p class="path-description">{{ currentPath.description }}</p>
          <div class="path-info">
            <span>预计学习时长：{{ currentPath.duration }}</span>
            <span>难度等级：{{ currentPath.difficulty }}</span>
          </div>
        </div>

        <el-steps :active="1" direction="vertical" class="path-steps">
          <el-step 
            v-for="(stage, index) in currentPath.stages" 
            :key="index"
            :title="stage.title"
            :description="stage.description"
          >
            <template #icon>
              <el-icon v-if="stage.status === 'completed'"><Check /></el-icon>
              <el-icon v-else-if="stage.status === 'in-progress'"><Loading /></el-icon>
              <el-icon v-else><More /></el-icon>
            </template>
            <template #default>
              <div class="stage-content">
                <div class="stage-courses">
                  <h4>推荐课程</h4>
                  <ul>
                    <li v-for="course in stage.courses" :key="course.id">
                      <span>{{ course.name }}</span>
                      <el-button 
                        type="primary" 
                        size="small"
                        @click="viewCourse(course)"
                      >
                        查看课程
                      </el-button>
                    </li>
                  </ul>
                </div>
                <div class="stage-skills">
                  <h4>需掌握技能</h4>
                  <div class="skill-tags">
                    <el-tag
                      v-for="skill in stage.skills"
                      :key="skill"
                      size="small"
                    >
                      {{ skill }}
                    </el-tag>
                  </div>
                </div>
              </div>
            </template>
          </el-step>
        </el-steps>

        <div class="path-actions">
          <el-button type="primary" @click="startLearning">开始学习</el-button>
          <el-button @click="customizePath">自定义路径</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LearningPath',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      selectedPath: '',
      learningPaths: [
        {
          value: 'frontend',
          label: '前端开发',
          description: '从零开始学习前端开发，掌握现代Web开发技术栈',
          duration: '6个月',
          difficulty: '中级',
          stages: [
            {
              title: '基础阶段',
              description: '掌握Web开发基础知识',
              status: 'completed',
              courses: [
                { id: 1, name: 'HTML5基础教程' },
                { id: 2, name: 'CSS3深入学习' }
              ],
              skills: ['HTML5', 'CSS3', '响应式设计']
            },
            {
              title: '进阶阶段',
              description: '学习JavaScript和主流框架',
              status: 'in-progress',
              courses: [
                { id: 3, name: 'JavaScript高级编程' },
                { id: 4, name: 'Vue.js实战课程' }
              ],
              skills: ['JavaScript', 'Vue.js', 'Webpack']
            }
          ]
        }
      ]
    }
  },
  computed: {
    currentPath() {
      return this.learningPaths.find(path => path.value === this.selectedPath) || {}
    }
  },
  methods: {
    handlePathChange(value) {
      console.log('Selected path:', value)
    },
    viewCourse(course) {
      console.log('Viewing course:', course.name)
    },
    startLearning() {
      this.$message.success('开始学习功能待实现')
    },
    customizePath() {
      this.$message.success('自定义路径功能待实现')
    }
  }
}
</script>

<style scoped>
.learning-path {
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

.path-content {
  max-width: 1200px;
  margin: 0 auto;
}

.path-selection {
  margin-bottom: 30px;
}

.path-selection .el-select {
  width: 300px;
}

.path-overview {
  margin-bottom: 30px;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 4px;
}

.path-description {
  margin: 10px 0;
  color: #666;
}

.path-info {
  display: flex;
  gap: 20px;
  color: #409EFF;
}

.path-steps {
  margin: 30px 0;
}

.stage-content {
  padding: 15px;
}

.stage-courses {
  margin-bottom: 15px;
}

.stage-courses ul {
  list-style: none;
  padding: 0;
}

.stage-courses li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

.skill-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.path-actions {
  display: flex;
  gap: 15px;
  justify-content: center;
  margin-top: 30px;
}
</style> 