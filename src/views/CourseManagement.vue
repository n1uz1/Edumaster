<template>
  <div class="course-management">
    <header class="header">
      <div class="header-left">
        <h1>课程管理</h1>
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
        <div class="header-buttons">
          <el-button @click="$router.push('/')">返回首页</el-button>
          <el-button @click="handlePublishCourse">发布课程</el-button>
          <el-button @click="handleDeleteCourse">删除课程</el-button>
        </div>
        <div class="user-info">xxxxxxx XXX</div>
      </div>
    </header>

    <div class="search-section">
      <el-input
        v-model="searchQuery"
        placeholder="搜索课程"
        prefix-icon="el-icon-search"
      />
    </div>

    <div class="course-list">
      <el-table :data="filteredCourses" style="width: 100%">
        <el-table-column prop="name" label="课程名称" />
        <el-table-column prop="instructor" label="讲师" />
        <el-table-column prop="duration" label="课程时长" />
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button
                @click="viewCourseDetail(scope.row.id)"
                type="text"
                size="small"
              >
                查看详情
              </el-button>
              <el-button
                type="primary"
                size="small"
                @click="goToCourseDetail(scope.row.id)"
              >
                进入课程
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        :current-page="currentPage"
        :page-size="10"
        :total="totalCourses"
        layout="prev, pager, next"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'CourseManagement',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      searchQuery: '',
      currentPage: 1,
      totalCourses: 100,
      courses: [
        {
          id: 1,
          name: 'Vue.js基础教程',
          instructor: '张老师',
          duration: '30课时'
        },
        {
          id: 2,
          name: 'React入门到精通',
          instructor: '李老师',
          duration: '40课时'
        }
      ]
    }
  },
  computed: {
    filteredCourses() {
      return this.courses.filter(course =>
        course.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      )
    }
  },
  methods: {
    handlePublishCourse() {
      this.$message.success('发布课程功能待实现')
    },
    handleDeleteCourse() {
      this.$message.success('删除课程功能待实现')
    },
    viewCourseDetail(id) {
      console.log('查看课程详情:', id)
    },
    goToCourseDetail(id) {
      this.$router.push(`/course-detail/${id}`)
    },
    handlePageChange(page) {
      this.currentPage = page
    }
  }
}
</script>

<style scoped>
.course-management {
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

.header-buttons {
  display: flex;
  gap: 10px;
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

.search-section {
  margin-bottom: 20px;
}

.operation-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
}
</style> 