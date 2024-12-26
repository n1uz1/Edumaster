<template>
  <div class="course-detail">
    <header class="header">
      <h1>课程详情</h1>
      <el-button @click="$router.push('/course-management')">返回</el-button>
      <div class="user-info">张老师</div>
    </header>

    <div class="course-content">
      <h2 class="course-title">舞蹈基础课</h2>
      <div class="course-image">
      </div>
      <div class="course-intro">一个舞蹈的基本课程</div>

      <div class="video-section">
        <h2>课程视频</h2>
        <div class="video-list">
          <!-- 第一个视频 -->
          <div class="video-item" @click="playVideo('https://edumaster.oss-cn-beijing.aliyuncs.com/WeChat_20241226021016.mp4')">
            <div class="video-thumbnail">
              <div class="thumbnail-bg"></div>
              <div class="play-icon">
                <i class="el-icon-video-play"></i>
              </div>
            </div>
            <div class="video-info">
              <h3>第一讲：课程介绍</h3>
              <div class="video-actions">
                <el-button 
                  type="text" 
                  class="view-comments-btn"
                  @click.stop="viewComments({
                    title: '第一讲：课程介绍',
                    type: 'video'
                  })"
                >
                  查看评论
                </el-button>
              </div>
            </div>
          </div>

          <!-- 第二个视频 -->
          <div class="video-item" @click="playVideo('https://edumaster.oss-cn-beijing.aliyuncs.com/WeChat_20241226021016.mp4')">
            <div class="video-thumbnail">
              <div class="thumbnail-bg"></div>
              <div class="play-icon">
                <i class="el-icon-video-play"></i>
              </div>
            </div>
            <div class="video-info">
              <h3>第二讲：基础动作</h3>
              <div class="video-actions">
                <el-button 
                  type="text" 
                  class="view-comments-btn"
                  @click.stop="viewComments({
                    title: '第二讲：基础动作',
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
      currentVideoUrl: ''
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
</style> 