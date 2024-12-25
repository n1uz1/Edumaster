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
            <router-link to="/learning-forum">学习论坛</router-link>
          </div>
        </el-button>
      </div>
      <div class="header-right">
        <div class="header-buttons">
          <el-button @click="$router.push('/')">返回首页</el-button>
          <el-button @click="handleViewJoinedCourses">我的课程</el-button>
          <el-button @click="handlePublishCourse">发布课程</el-button>
          <el-button @click="handleAddCourseFile">添加课程文件</el-button>
          <el-button @click="handleEditCourse">编辑课程</el-button>
          <el-button @click="handleDeleteCourse">删除课程</el-button>
        </div>
        <div class="user-info">xxxxxxx XXX</div>
      </div>
    </header>

    <div class="search-section">
      <div class="top-actions">
        <div class="search-area">
          <el-input
            v-model="searchQuery"
            placeholder="搜索课程"
          />
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </div>
        <div class="upload-section">
          <el-upload
            class="upload-demo"
            action="http://localhost:8081/uploadFile"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :show-file-list="false"
          >
            <el-button type="primary">上传文件</el-button>
          </el-upload>
          <span v-if="fileUrl" class="file-url">文件URL: {{ fileUrl }}</span>
        </div>
      </div>
    </div>

    <div class="course-list">
      <el-table :data="courses" style="width: 100%">
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

    <!-- 发布课程窗 -->
    <el-dialog v-model="publishDialogVisible" title="发布课程" width="40%">
      <el-form :model="newCourse" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="newCourse.title" placeholder="请输入课程名称"/>
        </el-form-item>
        <el-form-item label="课程概要">
          <el-input
            v-model="newCourse.description"
            type="textarea"
            :rows="4"
            placeholder="请输入课程概要"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="publishDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitCourse">发布</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 删除课程弹窗 -->
    <el-dialog v-model="deleteDialogVisible" title="删除课程" width="40%">
      <div class="delete-course-content">
        <p class="delete-tip">请选择要删除的课程：</p>
        <el-table
          :data="myPublishedCourses"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="title" label="课程名称" />
          <el-table-column prop="description" label="课程概要" show-overflow-tooltip />
        </el-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="deleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="confirmDelete" :disabled="!selectedCourse">删除</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑课程选择弹窗 -->
    <el-dialog v-model="editSelectDialogVisible" title="选择要编辑的课程" width="40%">
      <div class="edit-course-content">
        <p class="edit-tip">请选择要编辑的课程：</p>
        <el-table
          :data="myPublishedCourses"
          style="width: 100%"
          @selection-change="handleEditSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="title" label="课程名称" />
          <el-table-column prop="description" label="课程概要" show-overflow-tooltip />
        </el-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editSelectDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="showEditForm" :disabled="!selectedEditCourse">
            编辑
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑课程表单弹窗 -->
    <el-dialog v-model="editFormDialogVisible" title="编辑课程信息" width="40%">
      <el-form :model="editingCourse" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="editingCourse.title" placeholder="请输入课程名称"/>
        </el-form-item>
        <el-form-item label="课程概要">
          <el-input
            v-model="editingCourse.description"
            type="textarea"
            :rows="4"
            placeholder="请输入课程概要"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editFormDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitEdit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 课程详情弹窗 -->
    <el-dialog v-model="detailDialogVisible" title="课���详情" width="40%">
      <div class="course-detail-content" v-if="currentCourseDetail">
        <div class="detail-item">
          <label>课程名称：</label>
          <span>{{ currentCourseDetail.title }}</span>
        </div>
        <div class="detail-item">
          <label>课程概要：</label>
          <p>{{ currentCourseDetail.description }}</p>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 已加入课程弹窗 -->
    <el-dialog v-model="joinedCoursesDialogVisible" title="我加入的课程" width="50%">
      <div class="joined-courses-content">
        <el-table :data="joinedCourses" style="width: 100%">
          <el-table-column prop="title" label="课程名称" />
          <el-table-column prop="description" label="课程描述" show-overflow-tooltip />
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-button
                type="primary"
                size="small"
                @click="goToCourseDetail(scope.row.id)"
              >
                进入课程
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="joinedCoursesDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 添加课程文件弹窗 -->
    <el-dialog v-model="addFileDialogVisible" title="添加课程文件" width="50%">
      <div class="add-file-content">
        <div class="course-selection">
          <h3>选择课程</h3>
          <el-table
            :data="myPublishedCourses"
            style="width: 100%"
            @selection-change="handleFileSelectionChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="title" label="课程名称" />
            <el-table-column prop="description" label="课程概要" show-overflow-tooltip />
          </el-table>
        </div>
        
        <div class="file-upload" v-if="selectedCourseForFile">
          <h3>上传文件</h3>
          <el-upload
            class="upload-demo"
            action="http://localhost:8081/uploadFile"
            :on-success="handleFileUploadSuccess"
            :on-error="handleFileUploadError"
            :show-file-list="false"
            :disabled="!selectedCourseForFile"
          >
            <el-button type="primary" :disabled="!selectedCourseForFile">
              选择文件
            </el-button>
          </el-upload>
          <div v-if="tempFileUrl" class="file-info">
            <p>文件上传成功，URL: {{ tempFileUrl }}</p>
            <el-button type="success" @click="submitCourseLesson">
              确认添加到课程
            </el-button>
          </div>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelAddFile">取消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CourseManagement',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      fileUrl: '',
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
      ],
      publishDialogVisible: false,
      newCourse: {
        title: '',
        description: '',
        creatorId: 1  // 这里暂时hardcode为1，实际应该从用户登录信息中获取
      },
      deleteDialogVisible: false,
      myPublishedCourses: [
        {
          id: 1,
          title: 'Spring Boot入门',
          description: '这是一门学习Spring Boot基础的课程'
        },
        {
          id: 2,
          title: 'Vue.js进阶',
          description: 'Vue.js高级特性与实战应用'
        }
      ],
      selectedCourse: null,
      editSelectDialogVisible: false,
      editFormDialogVisible: false,
      selectedEditCourse: null,
      editingCourse: {
        id: null,
        title: '',
        description: '',
        creatorId: 1
      },
      detailDialogVisible: false,
      currentCourseDetail: null,
      joinedCoursesDialogVisible: false,
      joinedCourses: [],
      userId: 1, // 当前用户ID，实际应该从用户登录信息中获取
      addFileDialogVisible: false,
      selectedCourseForFile: null,
      tempFileUrl: '',
    }
  },
  methods: {
    handlePublishCourse() {
      this.publishDialogVisible = true
    },
    handleDeleteCourse() {
      this.deleteDialogVisible = true
    },
    async viewCourseDetail(id) {
      try {
        const response = await axios.get(`http://localhost:8081/courses/${id}`)
        if (response.data && response.data.code === 200) {
          const courseData = response.data.data
          this.currentCourseDetail = {
            courseId: courseData.courseId,
            title: courseData.title,
            description: courseData.description,
            creatorId: courseData.creatorId
          }
          this.detailDialogVisible = true
        } else {
          this.$message.error(response.data.message || '获取课程详情失败')
        }
      } catch (error) {
        this.$message.error('获取课程详情失败：' + error.message)
      }
    },
    goToCourseDetail(courseId) {
      this.$router.push(`/course/${courseId}/lesson/1`)
    },
    handlePageChange(page) {
      this.currentPage = page
    },
    handleUploadSuccess(response) {
      if (response && response.url) {
        this.fileUrl = response.url
        this.$message.success(response.message || '文件上传成功')
      } else {
        this.$message.error('上传失败：返回的URL为空')
      }
    },
    handleUploadError(error) {
      this.$message.error('文件上传失败：' + error.message)
    },
    async submitCourse() {
      try {
        const courseData = {
          title: this.newCourse.title,
          description: this.newCourse.description,
          creatorId: 1  // 这里可以根据实际登录用户ID设置
        }
        
        const response = await axios.post('http://localhost:8081/courses', courseData)
        
        if (response.data && response.data.code === 201) {
          this.$message.success(response.data.message || '课程发布成功')
          this.publishDialogVisible = false
          // 重置表单
          this.newCourse.title = ''
          this.newCourse.description = ''
          // 这里可以添加刷新课程列表的逻辑
          await this.loadCourses() // 刷新课程列表
        } else {
          this.$message.error(response.data.message || '课程发布失败')
        }
      } catch (error) {
        this.$message.error('课程发布失败：' + error.message)
      }
    },
    handleSelectionChange(selection) {
      // 由于我们只允许单选，所以只取第一个选中的课程
      this.selectedCourse = selection[0]
    },
    async confirmDelete() {
      if (!this.selectedCourse) {
        this.$message.warning('请选择要删除的课程')
        return
      }

      try {
        const response = await axios.delete(`http://localhost:8081/courses/${this.selectedCourse.id}`)
        if (response.data && response.data.code) {
          switch (response.data.code) {
            case 201:
              this.$message.success(response.data.message || '课程删除成功')
              this.deleteDialogVisible = false
              // 从列表中移除已删除的课程
              this.myPublishedCourses = this.myPublishedCourses.filter(
                course => course.id !== this.selectedCourse.id
              )
              this.selectedCourse = null
              break
            case 404:
              this.$message.error(response.data.message || '课程未找到')
              break
            default:
              this.$message.error(response.data.message || '删除失败')
          }
        } else {
          this.$message.error('删除失败：服务器返回数据格式错误')
        }
      } catch (error) {
        this.$message.error('删除课程失败：' + error.message)
      }
    },
    handleEditCourse() {
      this.editSelectDialogVisible = true
    },
    handleEditSelectionChange(selection) {
      this.selectedEditCourse = selection[0]
    },
    showEditForm() {
      if (this.selectedEditCourse) {
        this.editingCourse = {
          id: this.selectedEditCourse.id,
          title: this.selectedEditCourse.title,
          description: this.selectedEditCourse.description,
          creatorId: this.selectedEditCourse.creatorId || 1
        }
        this.editSelectDialogVisible = false
        this.editFormDialogVisible = true
      }
    },
    async submitEdit() {
      try {
        const response = await axios.put(
          `http://localhost:8081/courses/${this.editingCourse.id}`,
          {
            title: this.editingCourse.title,
            description: this.editingCourse.description,
            creatorId: this.editingCourse.creatorId
          }
        )
        
        if (response.data && response.data.code === 200) {
          this.$message.success(response.data.message || '课程更新成功')
          this.editFormDialogVisible = false
          
          // 更新本地课程列表
          const index = this.myPublishedCourses.findIndex(
            course => course.id === this.editingCourse.id
          )
          if (index !== -1) {
            this.myPublishedCourses[index] = {
              ...this.myPublishedCourses[index],
              ...this.editingCourse
            }
          }
          
          // 重置编辑状态
          this.selectedEditCourse = null
          this.editingCourse = {
            id: null,
            title: '',
            description: '',
            creatorId: 1
          }
        } else {
          this.$message.error(response.data.message || '更新失败')
        }
      } catch (error) {
        this.$message.error('更新课程失败：' + error.message)
      }
    },
    async handleSearch() {
      try {
        let url = 'http://localhost:8081/courses'
        let params = {}
        
        // 如果搜索关键词不为空，则使用搜索API
        if (this.searchQuery && this.searchQuery.trim()) {
          url = `${url}/search`
          params = { title: this.searchQuery.trim() }
        }
        
        const response = await axios.get(url, { params })
        
        if (response.data && response.data.code === 200) {
          const coursesData = response.data.data || []
          // 确保返回的数据是数组
          this.courses = Array.isArray(coursesData) ? coursesData : [coursesData]
          
          // 格式化数据以适应表格显示
          this.courses = this.courses.map(course => ({
            id: course.courseId,
            name: course.title,
            instructor: course.creatorId,
            description: course.description,
            duration: '未设置'
          }))
          
          if (this.courses.length === 0) {
            this.$message.info('未找到匹配的课程')
          }
        } else {
          this.$message.error('搜索失败：' + (response.data.message || '未知错误'))
        }
      } catch (error) {
        this.$message.error('搜索课程失败：' + error.message)
      }
    },
    async loadAllCourses() {
      try {
        const response = await axios.get('http://localhost:8081/courses')
        if (response.data) {
          // 合并默认课程和后端返回的课程
          const defaultCourses = [
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
          
          const backendCourses = Array.isArray(response.data) ? response.data : [response.data]
          const formattedBackendCourses = backendCourses.map(course => ({
            id: course.id,
            name: course.title,
            instructor: course.instructor || '未知',
            duration: course.duration || '未设置'
          }))
          
          this.courses = [...defaultCourses, ...formattedBackendCourses]
        }
      } catch (error) {
        this.$message.error('加载后端课程失败：' + error.message)
        // 保持默认课程显示
        this.courses = [
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
    async handleViewJoinedCourses() {
      try {
        const response = await axios.get(`http://localhost:8081/users/${this.userId}/courses`)
        if (response.data && response.data.code === 200) {
          // 确保返回的数据是数组
          const coursesData = response.data.data || []
          
          // 格式化课程数据
          this.joinedCourses = coursesData.map(course => ({
            id: course.courseId,
            title: course.title,
            description: course.description,
            instructor: course.creatorId
          }))
          
          this.joinedCoursesDialogVisible = true
          
          if (this.joinedCourses.length === 0) {
            this.$message.info('您还没有加入任何课程')
          }
        } else {
          this.$message.error(response.data.message || '获取课程列表失败')
        }
      } catch (error) {
        this.$message.error('获取课程列表失败：' + error.message)
      }
    },
    handleAddCourseFile() {
      this.addFileDialogVisible = true
      this.tempFileUrl = ''
      this.selectedCourseForFile = null
    },
    handleFileSelectionChange(selection) {
      this.selectedCourseForFile = selection[0]
    },
    handleFileUploadSuccess(response) {
      this.tempFileUrl = response.url
      this.$message.success('文件上传成功')
    },
    handleFileUploadError() {
      this.$message.error('文件上传失败')
    },
    async submitCourseLesson() {
      if (!this.selectedCourseForFile || !this.tempFileUrl) {
        this.$message.warning('请先选择课程并上传文件')
        return
      }
      
      try {
        const lessonData = {
          courseId: this.selectedCourseForFile.id,
          lessonId: 101, // 这里可以根据需要生成或获取
          title: `${this.selectedCourseForFile.title} 入门`,
          videoUrl: this.tempFileUrl.url || this.tempFileUrl, // 兼容新旧格式
          description: `学习如何使用 ${this.selectedCourseForFile.title} 构建应用`
        }
        
        const response = await axios.post('http://localhost:8081/course-lessons', lessonData)
        
        if (response.data) {
          this.$message.success('课程文件添加成功')
          this.addFileDialogVisible = false
          this.tempFileUrl = ''
          this.selectedCourseForFile = null
        }
      } catch (error) {
        this.$message.error('添加课程文件失败：' + error.message)
      }
    },
    cancelAddFile() {
      this.addFileDialogVisible = false
      this.tempFileUrl = ''
      this.selectedCourseForFile = null
    }
  },
  created() {
    this.loadAllCourses()
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

.top-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.upload-section {
  display: flex;
  align-items: center;
  gap: 15px;
}

.file-url {
  color: #666;
  font-size: 14px;
}

.el-input {
  width: 300px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.delete-course-content {
  padding: 20px 0;
}

.delete-tip {
  margin: 0 0 20px 0;
  color: #666;
}

.edit-course-content {
  padding: 20px 0;
}

.edit-tip {
  margin: 0 0 20px 0;
  color: #666;
}

.search-area {
  display: flex;
  gap: 10px;
  align-items: center;
}

.search-area .el-input {
  width: 300px;
}

.course-detail-content {
  padding: 20px;
}

.detail-item {
  margin-bottom: 20px;
}

.detail-item label {
  font-weight: bold;
  color: #606266;
  margin-right: 10px;
}

.detail-item p {
  margin: 10px 0 0 0;
  color: #333;
  line-height: 1.6;
}

.joined-courses-content {
  padding: 20px 0;
}

.add-file-content {
  padding: 20px 0;
}

.course-selection {
  margin-bottom: 30px;
}

.course-selection h3,
.file-upload h3 {
  margin-bottom: 15px;
  color: #333;
}

.file-upload {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.file-info {
  margin-top: 15px;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}

.file-info p {
  margin-bottom: 10px;
  color: #666;
  word-break: break-all;
}
</style> 
