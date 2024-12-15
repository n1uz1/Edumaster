<template>
  <div class="course-detail">
    <header class="header">
      <h1>课程详情</h1>
      <el-button @click="$router.push('/course-management')">返回</el-button>
      <div class="user-info">xxxxxxx XXX</div>
    </header>

    <div class="course-content">
      <h2 class="course-title">{{ course.name }}</h2>
      <div class="course-image">
        <img :src="course.image" :alt="course.name">
      </div>
      <div class="course-intro">{{ course.introduction }}</div>

      <div class="video-section">
        <h3>课程视频</h3>
        <div class="video-list">
          <div v-for="video in course.videos" :key="video.id" class="video-item">
            <div class="video-content" @click="openVideo(video)">
              <img :src="video.thumbnail" :alt="video.title">
              <div class="video-info">
                <h4>{{ video.title }}</h4>
                <p>{{ video.duration }}</p>
              </div>
            </div>
            <div class="video-actions">
              <el-button type="text" @click="viewComments(video)">
                查看评论 ({{ video.commentCount || 0 }})
              </el-button>
            </div>
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
                查看评论 ({{ doc.commentCount || 0 }})
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
  </div>
</template>

<script>
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
          username: '学习者A',
          content: '这节课讲得很清楚！',
          date: '2024-03-15 14:30'
        },
        {
          id: 2,
          username: '学习者B',
          content: '有些概念需要多加练习。',
          date: '2024-03-15 15:00'
        }
      ]
    }
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
          username: '当前用户',
          content: this.newComment,
          date: new Date().toLocaleString()
        })
        this.newComment = ''
        this.$message.success('评论发布成功')
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
  background-color: #e3f2fd;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding: 15px 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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
</style> 