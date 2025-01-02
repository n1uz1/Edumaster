<template>
  <div class="course-recommendation">
    <header class="header">
      <div class="header-left">
        <h1>课程推荐</h1>
      </div>
      <div class="header-center">
        <el-button 
          @click="$router.push('/course-management')"
        >
          课程管理
        </el-button>
        <el-button 
          @click="$router.push('/course-recommendation')"
        >
          课程推荐
        </el-button>
      </div>
      <div class="header-right">
        <el-button @click="$router.push('/')">返回首页</el-button>
        <div class="user-info">张成业</div>
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
        <h2>基于您的兴趣推荐</h2>
        <div class="course-list">
          <el-card v-for="course in recommendedCourses" :key="course.id" class="course-item">
            <div class="course-info">
              <h3>{{ course.title }}</h3>
              <p>{{ course.description }}</p>
              <div class="course-meta">
                <span>讲师: {{ course.instructor }}</span>
              </div>
            </div>
            <div class="course-actions">
              <el-button
                type="primary"
                size="small"
                @click="$router.push({
                  path: `/course-detail/${course.id}`,
                  query: {
                    title: course.title,
                    description: course.description,
                    courseId: course.id
                  }
                })"
              >
                进入课程
              </el-button>
            </div>
          </el-card>
        </div>
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
      userInterests: ['Spring Boot', 'Spring Cloud', 'Spring Security'],
      dialogVisible: false,
      selectedInterests: [],
      availableInterests: [
        { value: 'spring-boot', label: 'Spring Boot' },
        { value: 'spring-cloud', label: 'Spring Cloud' },
        { value: 'spring-security', label: 'Spring Security' },
        { value: 'spring-data', label: 'Spring Data' },
        { value: 'spring-mvc', label: 'Spring MVC' }
      ],
      recommendedCourses: [
        {
          id: 1,
          title: 'Spring Boot入门实战',
          description: '从零开始学习Spring Boot，包含核心概念、自动配置、数据访问等重要知识',
          instructor: '张老师'
        },
        {
          id: 2,
          title: 'Spring Cloud微服务架构',
          description: '深入学习Spring Cloud微服务开发，包括服务注册、配置中心、服务调用等',
          instructor: '李老师'
        },
        {
          id: 3,
          title: 'Spring Security安全框架',
          description: '全面掌握Spring Security，包括认证授权、安全配置、OAuth2等安全特性',
          instructor: '王老师'
        },
        {
          id: 4,
          title: 'Spring全家桶实战',
          description: '整合Spring Boot、Spring Cloud、Spring Security等技术栈的综合实战课程',
          instructor: '赵老师'
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
    },
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
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.course-info h3 {
  margin: 0 0 10px 0;
  font-size: 18px;
  color: #303133;
}

.description {
  margin: 10px 0;
  color: #606266;
  font-size: 14px;
  line-height: 1.4;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.course-id {
  color: #909399;
  font-size: 14px;
  margin: 5px 0;
}

.instructor {
  color: #909399;
  font-size: 14px;
  margin: 5px 0;
}

.course-actions {
  margin-top: auto;
  text-align: right;
}
</style> 