<template>
  <div class="course-detail">
    <header class="header">
      <h1>课程详情</h1>
      <el-button @click="$router.push('/course-management')">返回</el-button>
      <el-button type="primary" @click="showAddLessonDialog">添加课程文件</el-button>
      <div class="user-info">张老师</div>
    </header>

    <div class="course-content">
      <h2 class="course-title">{{ $route.query.title }}</h2>
      <div class="course-image">
      </div>
      <div class="course-intro">{{ $route.query.description }}</div>

      <div class="video-section">
  <h2>课程视频</h2>
  <div class="video-list">
    <div v-for="lesson in courseLessons" 
         :key="lesson.courseLessonId" 
         class="video-item" 
         @click="playVideo(lesson.videoUrl)"
    >
      <div class="video-thumbnail">
        <div class="thumbnail-bg"></div>
        <div class="play-icon">
          <i class="el-icon-video-play"></i>
        </div>
      </div>
      <div class="video-info">
        <h3>{{ lesson.title }}</h3>
        <div class="video-actions">
          <el-button 
            type="text" 
            class="view-comments-btn"
            @click.stop="viewComments({
              title: lesson.title,
              type: 'video'
            })"
          >
            查看评论
          </el-button>
        </div>
      </div>
    </div>
  </div>
        

        <!-- 视频播放器 -->
        <div v-if="showVideoPlayer" class="video-player-modal">
          <div class="video-player-container">
            <div class="close-button-wrapper">
              <el-button
                class="close-button"
                type="danger"
                circle
                @click="closeVideo"
              >
                <i class="el-icon-close"></i>
              </el-button>
            </div>
            <video 
              ref="videoPlayer"
              :src="currentVideoUrl"
              controls
              autoplay
              class="video-player"
            ></video>
          </div>
        </div>
      </div>

      <div class="document-section">
        <h3>课程文档</h3>
        <div class="document-list">
          <div v-for="doc in course.documents" :key="doc.id" class="document-item">
            <div class="document-content" @click="openDocument(doc)">
              <span>{{ doc.name }}</span>
            </div>
            <div class="document-actions">
              <el-button type="text" @click="viewComments(doc)">
                查看评论 
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 文件查看弹窗 -->
    <el-dialog v-model="dialogVisible" :title="currentFile.title" width="70%">
      <div class="file-content">
        <div v-if="currentFile.type === 'video'" class="video-player">
          <video controls :src="currentFile.url"></video>
        </div>
        <div v-else class="document-viewer">
          <iframe :src="currentFile.url"></iframe>
        </div>
      </div>
      <div v-if="showComments" class="comments-section">
        <h4>评论区</h4>
        <div class="comments-input">
          <el-input
            v-model="newComment"
            type="textarea"
            :rows="2"
            placeholder="写下你的评论..."
          />
          <el-button type="primary" @click="submitComment">发表评论</el-button>
        </div>
        <div class="comments-list">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <div class="comment-user">{{ comment.username }}</div>
            <p>{{ comment.content }}</p>
            <span>{{ comment.date }}</span>
          </div>
        </div>
      </div>
    </el-dialog>

    <!-- 添加课程文件弹窗 -->
    <el-dialog v-model="addLessonDialogVisible" title="添加课程文件" width="40%">
      <el-form :model="newLesson" label-width="100px">
        <el-form-item label="课号">
          <el-input v-model="$route.query.courseId" :disabled="true" />
        </el-form-item>
        <el-form-item label="小课号">
          <el-input v-model="newLesson.lessonId" placeholder="请输入小课号" />
        </el-form-item>
        <el-form-item label="小课标题">
          <el-input v-model="newLesson.lesson_title" placeholder="请输入小课标题" />
        </el-form-item>
        <el-form-item label="小课简介">
          <el-input
            v-model="newLesson.lesson_description"
            type="textarea"
            :rows="4"
            placeholder="请输入小课简介"
          />
        </el-form-item>
        <el-form-item label="课程文件">
          <el-upload
            class="upload-demo"
            action="http://localhost:8081/uploadFile"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :show-file-list="false"
          >
            <el-button type="primary">上传文件</el-button>
          </el-upload>
          <div v-if="uploadedFile" class="file-info">
            <p>已上传文件：{{ uploadedFile.name }}</p>
            <p>文件URL：{{ uploadedFile.url }}</p>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addLessonDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitLesson">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CourseDetail',
  data() {
    return {
      course: {
        name: '示例课程',
        image: '/course-image.jpg',
        introduction: '这是一个示例课程介绍...',
        videos: [
          {
            id: 1,
            title: '第一课：课程介绍',
            thumbnail: '/video1-thumb.jpg',
            duration: '15:00',
            commentCount: 5,
            url: '/video1.mp4'
          },
          {
            id: 2,
            title: '第二课：基础概念',
            thumbnail: '/video2-thumb.jpg',
            duration: '20:00',
            commentCount: 3,
            url: '/video2.mp4'
          }
        ],
        documents: [
          {
            id: 1,
            name: '课程大纲.pdf',
            commentCount: 2,
            url: '/outline.pdf'
          },
          {
            id: 2,
            name: '练习题.pdf',
            commentCount: 4,
            url: '/exercises.pdf'
          }
        ]
      },
      dialogVisible: false,
      currentFile: {
        title: '',
        type: '',
        url: ''
      },
      showComments: false,
      newComment: '',
      comments: [
        {
          id: 1,
          username: 'A',
          content: '这节课讲得很清楚！',
          date: '2024-03-15 14:57'
        },
        {
          id: 2,
          username: 'B',
          content: '有些概念需要多加练习。',
          date: '2024-03-15 15:06'
        }
      ],
      showVideoPlayer: false,
      currentVideoUrl: '',
      addLessonDialogVisible: false,
      newLesson: {
        lessonId: '',
        lesson_title: '',
        lesson_description: '',
        fileUrl: ''
      },
      uploadedFile: null,
      courseTitle: this.$route.query.title,
      courseLessons: []
    }
  },
  
  created() {
    this.loadCourseLessons()
  },
  
  methods: {
    openVideo(video) {
      this.currentFile = {
        title: video.title,
        type: 'video',
        url: video.url
      }
      this.dialogVisible = true
      this.showComments = false
    },
    openDocument(doc) {
      this.currentFile = {
        title: doc.name,
        type: 'document',
        url: doc.url
      }
      this.dialogVisible = true
      this.showComments = false
    },
    viewComments(file) {
      this.currentFile = {
        title: file.title || file.name,
        type: file.type,
        url: file.url
      }
      this.dialogVisible = true
      this.showComments = true
    },
    submitComment() {
      if (this.newComment.trim()) {
        this.comments.unshift({
          id: Date.now(),
          username: '张老师',
          content: this.newComment,
          date: new Date().toLocaleString()
        })
        this.newComment = ''
        this.$message.success('评论发布成功')
      }
    },
    playVideo(url) {
      this.currentVideoUrl = url;
      this.showVideoPlayer = true;
    },
    closeVideo() {
      this.showVideoPlayer = false;
      this.currentVideoUrl = '';
      if (this.$refs.videoPlayer) {
        this.$refs.videoPlayer.pause();
      }
    },
    showAddLessonDialog() {
      this.addLessonDialogVisible = true
      this.newLesson = {
        lessonId: '',
        lesson_title: '',
        lesson_description: '',
        fileUrl: ''
      }
      this.uploadedFile = null
    },
    
    handleUploadSuccess(response) {
      if (response.message === "File uploaded successfully.") {
        this.uploadedFile = {
          name: response.url.split('/').pop(), // 从URL中提取文件名
          url: response.url
        }
        this.newLesson.fileUrl = response.url
      }
    },
    
    handleUploadError() {
      // 静默处理错误
    },
    
    async submitLesson() {
      try {
        // 检查必填字段
        if (!this.newLesson.lessonId || !this.newLesson.lesson_title || !this.newLesson.lesson_description||!this.newLesson.fileUrl) {
          this.$message.error('请填写完整信息')
          return
        }

        // 构造请求数据
        const lessonData = {
          courseId: parseInt(this.$route.query.courseId), // 确保是数字类型
          lessonId: parseInt(this.newLesson.lessonId),
          title: this.newLesson.lesson_title,
          videoUrl: this.newLesson.fileUrl, // 如果没有上传文件，使用默认URL
          description: this.newLesson.lesson_description
        }

        // 发送请求
        const response = await axios.post('http://localhost:8081/course-lessons', lessonData)

        if (response.data && response.data.code === 201) {
          // 关闭弹窗
          this.addLessonDialogVisible = false
          
          // 重置表单
          this.newLesson = {
            lessonId: '',
            lesson_title: '',
            lesson_description: '',
            fileUrl: ''
          }
          this.uploadedFile = null
          
          // 重新加载课程列表（如果需要）
          // this.loadLessons()
        }
      } catch {
        // 静默处理错误
      }
    },
    async loadCourseLessons() {
      try {
        const courseId = this.$route.query.courseId
        const response = await axios.get(`http://localhost:8081/course-lessons/course/${courseId}`)
        
        if (response.data && response.data.code === 200) {
          // 处理返回的课程列表数据
          this.courseLessons = response.data.data.map(lesson => ({
            courseLessonId: lesson.courseLessonId,
            courseId: lesson.courseId,
            lessonId: lesson.lessonId,
            title: lesson.title,
            videoUrl: lesson.videoUrl,
            description: lesson.description
          }))
        }
      } catch {
        // 静默处理错误
      }
    }
  }
}
</script>

<style scoped>
.course-detail {
  background-color: #e8f5e9;
  min-height: 100vh;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 30px;
  background-color: #e6f0fa;
  height: 60px;
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  border-bottom: 1px solid #d9e6f2;
}

h1 {
  margin: 0;
  font-size: 20px;
  color: #333;
}

.user-info {
  font-size: 16px;
  color: #333;
}

.course-content {
  max-width: 1200px;
  margin: 0 auto;
}

.course-image img {
  max-width: 100%;
  height: auto;
}

.video-list, .document-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  margin: 20px 0;
}

.video-item, .document-item {
  cursor: pointer;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.video-item img, .document-item img {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.video-content, .document-content {
  flex: 1;
  padding: 10px;
}

.video-actions, .document-actions {
  padding: 8px;
  border-top: 1px solid #eee;
  text-align: right;
  background: #f9f9f9;
}

.comments-section {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ddd;
}

.comments-input {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.comments-input .el-textarea {
  flex: 1;
}

.comment-item {
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.comment-user {
  font-weight: bold;
  margin-bottom: 5px;
  color: #409EFF;
}

.comment-item span {
  font-size: 12px;
  color: #999;
  display: block;
  margin-top: 5px;
}

.video-section {
  padding: 20px;
}

.video-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.video-item {
  cursor: pointer;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  transition: transform 0.3s;
}

.video-item:hover {
  transform: translateY(-5px);
}

.video-thumbnail {
  position: relative;
  padding-top: 56.25%; /* 16:9 比例 */
  background: #f5f5f5;
}

.thumbnail-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #e0e0e0;
}

.play-icon {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 48px;
  opacity: 0.8;
}

.video-info {
  padding: 15px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.video-info h3 {
  margin: 0;
  font-size: 16px;
}

.video-player-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.9);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.video-player-container {
  position: relative;
  width: 80%;
  max-width: 1200px;
  padding-top: 40px;  /* 为关闭按钮留出空间 */
}

.close-button-wrapper {
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1001;
}

.video-player {
  width: 100%;
  border-radius: 8px;
}

.close-button {
}

.video-actions {
  display: flex;
  justify-content: flex-end;
  padding-top: 5px;
}

.view-comments-btn {
  color: #409EFF;
  padding: 0;
}

.view-comments-btn:hover {
  color: #66b1ff;
}

.file-info {
  margin-top: 10px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.file-info p {
  margin: 5px 0;
  word-break: break-all;
  color: #666;
}
</style> 