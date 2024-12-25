<template>
  <div class="course-lesson">
    <header class="header">
      <div class="header-left">
        <el-button @click="$router.back()">返回</el-button>
        <h1>{{ courseTitle }}</h1>
      </div>
      <div class="header-center">
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
      <div class="user-info">xxxxxxx XXX</div>
    </header>

    <div class="lesson-content">
      <div class="video-section" v-if="currentLesson">
        <video 
          v-if="currentLesson.videoUrl" 
          :src="currentLesson.videoUrl" 
          controls
          class="lesson-video"
        ></video>
        <div class="lesson-info">
          <h2>{{ currentLesson.title }}</h2>
          <p>{{ currentLesson.description }}</p>
        </div>

        <div class="comments-section">
          <h3>评论区</h3>
          <div v-if="comments.length > 0" class="comments-list">
            <el-card v-for="comment in comments" :key="comment.id" class="comment-item">
              <div class="comment-header">
                <span class="username">{{ comment.username }}</span>
                <span class="time">{{ comment.time }}</span>
              </div>
              <div class="comment-content">
                {{ comment.content }}
              </div>
              <div class="comment-footer">
                <span class="likes">
                  <i class="el-icon-star-off"></i>
                  {{ comment.likes }}
                </span>
                <div class="replies" v-if="comment.replies && comment.replies.length > 0">
                  <div v-for="reply in comment.replies" :key="reply.id" class="reply-item">
                    <div class="reply-header">
                      <span class="username">{{ reply.username }}</span>
                      <span class="time">{{ reply.time }}</span>
                    </div>
                    <div class="reply-content">
                      {{ reply.content }}
                    </div>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
          <div v-else class="no-comments">
            暂无评论
          </div>
        </div>
      </div>

      <div class="course-files">
        <div class="files-header">
          <h3>课程资料</h3>
          <el-button 
            type="danger" 
            size="small" 
            @click="showDeleteFileDialog"
          >
            删除文件
          </el-button>
        </div>
        <div v-if="courseFiles.length > 0" class="files-list">
          <el-card v-for="file in courseFiles" :key="file.fileId" class="file-item">
            <div class="file-info">
              <div class="file-name">
                <i class="el-icon-document"></i>
                <span>{{ file.fileName }}</span>
              </div>
              <div class="file-actions">
                <el-button 
                  type="primary" 
                  size="small"
                  @click="downloadFile(file.fileUrl)"
                >
                  下载
                </el-button>
              </div>
            </div>
          </el-card>
        </div>
        <div v-else class="no-files">
          暂无课程��料
        </div>
      </div>

      <div class="lessons-list">
        <h3>课程目录</h3>
        <el-menu
          :default-active="currentLessonId"
          class="lesson-menu"
        >
          <el-menu-item
            v-for="lesson in lessonsList"
            :key="lesson.lessonId"
            :index="String(lesson.lessonId)"
            @click="loadLesson(courseId, lesson.lessonId)"
          >
            <span>{{ lesson.title }}</span>
          </el-menu-item>
        </el-menu>
      </div>
    </div>

    <el-dialog v-model="deleteFileDialogVisible" title="删除课程文件" width="40%">
      <div class="delete-file-content">
        <p class="delete-tip">请选择要删除的课程文件：</p>
        <el-table
          :data="courseFiles"
          @selection-change="handleDeleteSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="fileName" label="文件名称" />
          <el-table-column prop="lessonId" label="课时ID" />
        </el-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="deleteFileDialogVisible = false">取消</el-button>
          <el-button 
            type="danger" 
            @click="confirmDeleteFile"
            :disabled="!selectedFile"
          >
            删除
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CourseLesson',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      courseTitle: '',
      currentLesson: null,
      comments: [],
      courseFiles: [],
      deleteFileDialogVisible: false,
      selectedFile: null
    }
  },
  async created() {
    await this.loadLessonData()
  },
  methods: {
    async loadLessonData() {
      const { courseId, lessonId } = this.$route.params
      try {
        const response = await axios.get(
          `http://localhost:8081/course-lessons/${courseId}/${lessonId}`
        )
        
        if (response.data && response.data.code === 200) {
          const lessonData = response.data.data
          this.currentLesson = {
            title: lessonData.title,
            description: lessonData.description,
            videoUrl: lessonData.videoUrl
          }
          this.courseTitle = lessonData.title
        } else {
          this.$message.error(response.data.message || '加载课程内容失败')
        }
      } catch (error) {
        this.$message.error('加载课程内容失败：' + error.message)
      }
    },
    async loadCourseFiles(courseId) {
      try {
        const response = await axios.get(`http://localhost:8081/course-files/course/${courseId}`)
        if (response.data) {
          this.courseFiles = response.data.map(file => ({
            fileId: file.fileId,
            courseId: file.courseId,
            fileUrl: file.fileUrl,
            fileName: file.fileName,
            filetype: file.filetype
          }))
          
          if (this.courseFiles.length === 0) {
            this.$message.info('该课程暂无资料')
          }
        }
      } catch (error) {
        this.$message.error('加载课程资料失败：' + error.message)
      }
    },
    downloadFile(fileUrl) {
      const link = document.createElement('a')
      link.href = fileUrl
      link.target = '_blank'
      link.download = fileUrl.split('/').pop()
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    },
    async loadComments(courseId, lessonId) {
      try {
        const response = await axios.get(
          `http://localhost:8081/comments/lesson/${courseId}/${lessonId}`
        )
        if (response.data && response.data.length > 0) {
          this.comments = response.data
        } else {
          this.comments = []
        }
      } catch (error) {
        this.$message.error('加载评论失败：' + error.message)
        this.comments = []
      }
    },
    showDeleteFileDialog() {
      this.deleteFileDialogVisible = true
      this.selectedFile = null
    },
    handleDeleteSelectionChange(selection) {
      this.selectedFile = selection[0]
    },
    async confirmDeleteFile() {
      if (!this.selectedFile) {
        this.$message.warning('请选择要删除的文件')
        return
      }
      
      try {
        const response = await axios.delete(
          `http://localhost:8081/course-lessons/${this.courseId}/${this.selectedFile.lessonId}`
        )
        
        if (response.data && response.data.code === 200) {
          this.$message.success(response.data.message || '课程节课删除成功')
          this.deleteFileDialogVisible = false
          
          this.courseFiles = this.courseFiles.filter(
            file => file.lessonId !== this.selectedFile.lessonId
          )
          this.selectedFile = null
        } else {
          this.$message.error(response.data.message || '删除失败')
        }
      } catch (error) {
        this.$message.error('删除文件失败：' + error.message)
      }
    }
  },
  watch: {
    async currentLessonId() {
      if (this.courseId && this.currentLessonId) {
        await this.loadComments(this.courseId, this.currentLessonId)
      }
    }
  }
}
</script>

<style scoped>
.course-lesson {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.lesson-content {
  display: flex;
  gap: 30px;
}

.video-section {
  flex: 1;
}

.lesson-video {
  width: 100%;
  max-width: 800px;
  margin-bottom: 20px;
}

.lesson-info {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 4px;
}

.lesson-info h2 {
  margin: 0 0 10px 0;
}

.lesson-info p {
  margin: 0;
  color: #666;
}

.lessons-list {
  width: 300px;
  border-left: 1px solid #eee;
  padding-left: 20px;
}

.lesson-menu {
  border-right: none;
}

.course-files {
  margin-top: 30px;
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.files-list {
  margin-top: 15px;
}

.file-item {
  margin-bottom: 10px;
}

.file-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.file-name {
  display: flex;
  align-items: center;
  gap: 10px;
}

.file-name i {
  font-size: 20px;
  color: #409EFF;
}

.no-files {
  color: #909399;
  text-align: center;
  padding: 20px;
}

.comments-section {
  margin-top: 30px;
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.comments-list {
  margin-top: 20px;
}

.comment-item {
  margin-bottom: 15px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.username {
  font-weight: bold;
  color: #409EFF;
}

.time {
  color: #909399;
  font-size: 12px;
}

.comment-content {
  margin: 10px 0;
  color: #333;
}

.comment-footer {
  margin-top: 10px;
}

.likes {
  color: #909399;
  font-size: 14px;
}

.replies {
  margin-top: 15px;
  padding-left: 20px;
  border-left: 2px solid #eee;
}

.reply-item {
  margin: 10px 0;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}

.reply-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.reply-content {
  color: #666;
}

.no-comments {
  text-align: center;
  color: #909399;
  padding: 20px;
}

.files-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.delete-file-content {
  padding: 20px 0;
}

.delete-tip {
  margin: 0 0 20px 0;
  color: #666;
}

.header-center {
  display: flex;
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
</style> 