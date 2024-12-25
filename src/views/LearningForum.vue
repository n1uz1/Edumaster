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
                <div class="post-actions">
                  <el-button 
                    type="text" 
                    @click="loadComments(post.id)"
                  >
                    评论 ({{ post.commentCount || 0 }})
                  </el-button>
                </div>
                <el-button 
                  type="primary" 
                  size="small" 
                  @click="viewPost(post.id)"
                >
                  查看详情
                </el-button>
              </div>
              <div v-if="post.showComments" class="comments-section">
                <div class="comment-input">
                  <el-button 
                    type="primary" 
                    size="small" 
                    @click="showCommentDialog(post.id)"
                  >
                    发表评论
                  </el-button>
                </div>
                <div v-if="post.comments && post.comments.length > 0" class="comments-list">
                  <div v-for="comment in post.comments" :key="comment.id" class="comment-item">
                    <div class="comment-header">
                      <span class="comment-author">{{ comment.author }}</span>
                      <span class="comment-time">{{ comment.createTime }}</span>
                    </div>
                    <div class="comment-content">{{ comment.content }}</div>
                    <div class="comment-footer">
                      <span class="likes">
                        <i class="el-icon-star-off"></i>
                        {{ comment.likes }} 赞
                      </span>
                      <span class="replies-count" v-if="comment.replies.length > 0">
                        {{ comment.replies.length }} 条回复
                      </span>
                    </div>
                  </div>
                </div>
                <div v-else class="no-comments">暂无评论</div>
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

    <!-- 添加发布评论的对话框 -->
    <el-dialog 
      v-model="commentDialogVisible" 
      title="发表评论" 
      width="40%"
    >
      <el-form :model="newComment" ref="commentForm">
        <el-form-item 
          label="评论内容" 
          prop="content"
          :rules="[
            { required: true, message: '请输入评论内容', trigger: 'blur' }
          ]"
        >
          <el-input
            v-model="newComment.content"
            type="textarea"
            :rows="4"
            placeholder="请输入您的评论"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="commentDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitComment">发布</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

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
      ],
      currentPostId: null,
      commentDialogVisible: false,
      newComment: {
        content: '',
        postId: null
      }
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
    },
    async loadComments(postId) {
      // 切换评论显示状态
      const post = this.posts.find(p => p.id === postId)
      if (post) {
        post.showComments = !post.showComments
        
        // 如果是显示评论且还没有加载过评论
        if (post.showComments && (!post.comments || post.comments.length === 0)) {
          try {
            const response = await axios.get('http://localhost:8081/comments')
            if (Array.isArray(response.data)) {
              // 更新帖子的评论
              post.comments = response.data.map(comment => ({
                id: comment.id,
                author: comment.username,
                content: comment.content,
                createTime: new Date(comment.time).toLocaleString(),
                likes: comment.likes,
                replies: comment.replies || []
              }))
            }
          } catch (error) {
            this.$message.error('加载评论失败：' + error.message)
            post.comments = []
          }
        }
      }
    },
    showCommentDialog(postId) {
      this.newComment.postId = postId
      this.commentDialogVisible = true
    },
    async submitComment() {
      try {
        if (!this.newComment.content.trim()) {
          this.$message.warning('请输入评论内容')
          return
        }
        
        const response = await axios.post('http://localhost:8081/comments', {
          username: '当前用户', // 这里可以替换为实际的用户名
          content: this.newComment.content
        })
        
        if (response.data && response.data.code === 201) {
          this.$message.success('评论发布成功')
          this.commentDialogVisible = false
          
          // 重新加载评论列表
          await this.loadComments(this.newComment.postId)
          
          // 重置表单
          this.newComment.content = ''
          this.newComment.postId = null
        } else {
          this.$message.error(response.data.message || '评论发布失败')
        }
      } catch (error) {
        this.$message.error('评论发布失败：' + error.message)
      }
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

.comments-section {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #eee;
}

.comment-item {
  padding: 10px;
  margin-bottom: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.comment-author {
  font-weight: bold;
  color: #409EFF;
}

.comment-time {
  color: #909399;
  font-size: 12px;
}

.comment-content {
  color: #333;
  margin: 8px 0;
}

.no-comments {
  text-align: center;
  color: #909399;
  padding: 10px;
}

.post-actions {
  display: flex;
  gap: 15px;
}

.comment-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
  font-size: 12px;
  color: #909399;
}

.likes {
  display: flex;
  align-items: center;
  gap: 4px;
}

.replies-count {
  color: #409EFF;
  cursor: pointer;
}

.comment-input {
  margin-bottom: 15px;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
  display: flex;
  justify-content: flex-end;
}
</style> 