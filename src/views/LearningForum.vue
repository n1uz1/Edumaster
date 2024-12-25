<template>
  <div class="learning-forum">
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
      </div>
      <div class="header-right">
        <el-button @click="$router.push('/')">返回首页</el-button>
        <div class="user-info">xxxxxxx XXX</div>
      </div>
    </header>

    <div class="forum-actions">
      <el-button type="primary" @click="showNewPostDialog">发布新帖</el-button>
    </div>

    <div class="forum-content">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="全部帖子" name="all">
          <div class="posts-list">
            <el-card v-for="post in posts" :key="post.id" class="post-item">
              <div class="post-header">
                <div class="post-info">
                  <span class="author">{{ post.author }}</span>
                  <span class="time">{{ post.time }}</span>
                </div>
                <div class="post-stats">
                  <span class="views">浏览: {{ post.views }}</span>
                  <span class="replies">回复: {{ post.replies }}</span>
                </div>
              </div>
              <div class="post-title" @click="viewPost(post.id)">
                {{ post.title }}
              </div>
              <div class="post-preview">
                {{ post.content }}
              </div>
              <div class="post-footer">
                <div class="post-tags">
                  <el-tag v-for="tag in post.tags" :key="tag" size="small">
                    {{ tag }}
                  </el-tag>
                </div>
                <el-button 
                  type="primary" 
                  size="small" 
                  @click="viewPost(post.id)"
                >
                  查看详情
                </el-button>
              </div>
            </el-card>
          </div>
        </el-tab-pane>
        <el-tab-pane label="热门讨论" name="hot">
          <!-- 热门帖子内容 -->
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 发布新帖对话框 -->
    <el-dialog v-model="newPostDialogVisible" title="发布新帖" width="50%">
      <el-form :model="newPost" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="newPost.title" placeholder="请输入帖子标题"/>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="newPost.content"
            type="textarea"
            :rows="6"
            placeholder="请输入帖子内容"
          />
        </el-form-item>
        <el-form-item label="标签">
          <el-select
            v-model="newPost.tags"
            multiple
            placeholder="选择标签"
          >
            <el-option
              v-for="tag in availableTags"
              :key="tag"
              :label="tag"
              :value="tag"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="newPostDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitNewPost">发布</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'LearningForum',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      activeTab: 'all',
      newPostDialogVisible: false,
      newPost: {
        title: '',
        content: '',
        tags: []
      },
      availableTags: ['课程讨论', '技术分享', '学习经验', '求助', '资源分享'],
      posts: [
        {
          id: 1,
          title: '分享一下我的 Spring Boot 学习经验',
          content: '最近在学习 Spring Boot，感觉这个框架非常强大...',
          author: '张三',
          time: '2024-01-15 14:30',
          views: 156,
          replies: 23,
          tags: ['学习经验', '技术分享']
        },
        {
          id: 2,
          title: '求助：Vue.js 路由配置问题',
          content: '在配置嵌套路由时遇到了一些问题...',
          author: '李四',
          time: '2024-01-14 16:45',
          views: 89,
          replies: 12,
          tags: ['求助', '技术分享']
        }
      ]
    }
  },
  methods: {
    showNewPostDialog() {
      this.newPostDialogVisible = true
    },
    submitNewPost() {
      // 这里添加发布新帖的逻辑
      const post = {
        id: this.posts.length + 1,
        ...this.newPost,
        author: '当前用户',
        time: new Date().toLocaleString(),
        views: 0,
        replies: 0
      }
      this.posts.unshift(post)
      this.newPostDialogVisible = false
      this.newPost = { title: '', content: '', tags: [] }
      this.$message.success('发布成功')
    },
    viewPost(postId) {
      this.$router.push(`/forum-post/${postId}`)
    }
  }
}
</script>

<style scoped>
.learning-forum {
  padding: 20px;
}

.forum-header {
  background-color: #e3f2fd;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.forum-title {
  margin: 0;
  font-size: 24px;
}

.header-center {
  display: flex;
  flex: 1;
  justify-content: center;
  align-items: center;
  margin-left: 50px;
}

.nav-buttons {
  display: flex;
  gap: 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-left: auto;
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

.forum-actions {
  padding: 20px;
  display: flex;
  justify-content: flex-end;
}

.posts-list {
  margin-top: 20px;
}

.post-item {
  margin-bottom: 20px;
  cursor: pointer;
}

.post-item:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.post-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.post-info {
  display: flex;
  gap: 15px;
}

.author {
  font-weight: bold;
  color: #409EFF;
}

.time {
  color: #909399;
  font-size: 14px;
}

.post-stats {
  color: #909399;
  font-size: 14px;
}

.post-stats span {
  margin-left: 15px;
}

.post-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #303133;
}

.post-preview {
  color: #606266;
  margin-bottom: 15px;
}

.post-tags {
  display: flex;
  gap: 10px;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 15px;
}
</style> 