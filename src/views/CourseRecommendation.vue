<template>
  <div class="course-recommendation">
    <header class="header">
      <div class="header-left">
        <h1>课程推荐</h1>
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

    <div class="recommendation-content">
      <div class="interest-tags">
        <h3>您的兴趣领域</h3>
        <div class="tags-container">
          <el-tag
            v-for="tag in userInterests"
            :key="tag"
            closable
            @close="removeInterest(tag)"
          >
            {{ tag }}
          </el-tag>
          <el-button size="small" @click="showAddInterest">+ 添加兴趣</el-button>
        </div>
      </div>

      <div class="recommendation-section">
        <h3>基于您的兴趣推荐</h3>
        <el-row :gutter="20">
          <el-col :span="8" v-for="course in recommendedCourses" :key="course.id">
            <el-card class="course-card">
              <img :src="course.image" class="course-image">
              <div class="course-info">
                <h4>{{ course.name }}</h4>
                <p>{{ course.description }}</p>
                <div class="course-meta">
                  <span>{{ course.duration }}</span>
                  <span>{{ course.level }}</span>
                </div>
                <div class="course-tags">
                  <el-tag size="small" v-for="tag in course.tags" :key="tag">
                    {{ tag }}
                  </el-tag>
                </div>
                <el-button type="primary" @click="viewCourse(course)">查看详情</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <div class="popular-section">
        <h3>热门推荐</h3>
        <el-table :data="popularCourses" style="width: 100%">
          <el-table-column prop="name" label="课程名称" />
          <el-table-column prop="instructor" label="讲师" />
          <el-table-column prop="rating" label="评分">
            <template #default="scope">
              <el-rate
                v-model="scope.row.rating"
                disabled
                show-score
                text-color="#ff9900"
              />
            </template>
          </el-table-column>
          <el-table-column prop="enrollCount" label="报名人数" />
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button
                type="text"
                size="small"
                @click="viewCourse(scope.row)"
              >
                查看详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 添加兴趣弹窗 -->
    <el-dialog v-model="dialogVisible" title="添加兴趣领域" width="30%">
      <el-select
        v-model="selectedInterests"
        multiple
        filterable
        placeholder="请选择兴趣领域"
      >
        <el-option
          v-for="item in availableInterests"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addInterests">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'CourseRecommendation',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      userInterests: ['Web开发', 'Python', '人工智能'],
      dialogVisible: false,
      selectedInterests: [],
      availableInterests: [
        { value: 'java', label: 'Java开发' },
        { value: 'python', label: 'Python' },
        { value: 'ai', label: '人工智能' }
      ],
      recommendedCourses: [
        {
          id: 1,
          name: 'Vue.js实战课程',
          description: '从入门到精通的Vue.js课程',
          image: '/course1.jpg',
          duration: '30课时',
          level: '中级',
          tags: ['Vue', 'JavaScript', '前端开发']
        }
      ],
      popularCourses: [
        {
          id: 1,
          name: 'Python数据分析',
          instructor: '李四',
          rating: 4.5,
          enrollCount: 1200
        }
      ]
    }
  },
  methods: {
    removeInterest(tag) {
      this.userInterests = this.userInterests.filter(t => t !== tag)
    },
    showAddInterest() {
      this.dialogVisible = true
    },
    addInterests() {
      this.dialogVisible = false
    },
    viewCourse(course) {
      console.log('Viewing course:', course.name)
    }
  }
}
</script>

<style scoped>
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

.course-recommendation {
  background-color: #e8f5e9;
  min-height: 100vh;
  padding: 20px;
}

.interest-tags {
  margin-bottom: 30px;
}

.tags-container {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  align-items: center;
}

.recommendation-section,
.popular-section {
  margin-bottom: 40px;
}

.course-card {
  margin-bottom: 20px;
}

.course-image {
  width: 100%;
  height: 160px;
  object-fit: cover;
}

.course-info {
  padding: 15px;
}

.course-info h4 {
  margin: 0 0 10px 0;
}

.course-meta {
  display: flex;
  justify-content: space-between;
  color: #666;
  margin: 10px 0;
}

.course-tags {
  margin: 10px 0;
  display: flex;
  gap: 5px;
}
</style> 