<template>
  <div class="forum-post">
    <header class="forum-header">
      <h1 class="forum-title">学习论坛</h1>
      <div class="header-center">
        <div class="nav-buttons">
          <el-button 
            class="menu-button" 
            @mouseenter="showLearningMenu = true"
            @mouseleave="showLearningMenu = false"
          >
            课程学习
            <div class="sub-menu" v-show="showLearningMenu">
              <router-link to="/course-management">课程管理</router-link>
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
              <router-link to="/learning-forum">学习论坛</router-link>
            </div>
          </el-button>
        </div>
      </div>
      <div class="header-right">
        <el-button @click="$router.push('/')">返回首页</el-button>
        <div class="user-info">xxxxxxx XXX</div>
      </div>
    </header>

    <div class="post-container">
      <div class="post-header">
        <el-button @click="$router.push('/learning-forum')" class="back-button">
          返回论坛
        </el-button>
      </div>

      <div v-if="post" class="post-content">
        <h2>{{ post.title }}</h2>
        <div class="post-meta">
          <span class="author">{{ post.author }}</span>
          <span class="time">{{ post.time }}</span>
          <span class="views">浏览: {{ post.views }}</span>
        </div>
        <div class="post-body">
          {{ post.content }}
        </div>
        <div class="post-tags">
          <el-tag v-for="tag in post.tags" :key="tag" size="small">
            {{ tag }}
          </el-tag>
        </div>
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
            </div>
          </el-card>
        </div>
        <div v-else class="no-comments">
          暂无评论
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ForumPost',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      post: null,
      comments: []
    }
  },
  async created() {
    const postId = this.$route.params.postId
    // 这里应该从后端获取帖子详情
    this.post = this.getMockPost(postId)
    await this.loadComments()
  },
  methods: {
    getMockPost(postId) {
      // 模拟帖子数据，实际应该从后端获取
      return {
        id: postId,
        title: '分享一下我的 Spring Boot 学习经验',
        content: '最近在学习 Spring Boot，感觉这个框架非常强大...',
        author: '张三',
        time: '2024-01-15 14:30',
        views: 156,
        tags: ['学习经验', '技术分享']
      }
    },
    async loadComments() {
      try {
        const response = await axios.get('http://localhost:8081/comments/forum')
        if (response.data && response.data.length > 0) {
          this.comments = response.data
        }
      } catch (error) {
        this.$message.error('加载评论失败：' + error.message)
      }
    }
  }
}
</script>

<style scoped>
/* 复用 LearningForum.vue 的头部样式 */
.forum-header {
  background-color: #e3f2fd;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

/* 其他样式... */

.post-container {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.post-header {
  margin-bottom: 20px;
}

.post-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.post-meta {
  margin: 10px 0 20px;
  color: #909399;
  font-size: 14px;
}

.post-meta > span {
  margin-right: 20px;
}

.post-body {
  line-height: 1.6;
  margin: 20px 0;
}

.comments-section {
  margin-top: 30px;
}

/* 复用 LearningForum.vue 的其他相关样式 */
</style> 