<template>
  <div class="discussion">
    <header class="header">
      <div class="header-left">
        <h1>互动与讨论</h1>
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
        <el-button @click="$router.push('/')">返回首页</el-button>
        <div class="user-info">xxxxxxx XXX</div>
      </div>
    </header>

    <div class="discussion-content">
      <div class="discussion-tools">
        <el-button type="primary" @click="createNewTopic">发起新讨论</el-button>
        <el-input
          v-model="searchQuery"
          placeholder="搜索讨论主题"
          prefix-icon="el-icon-search"
          class="search-input"
        />
      </div>

      <div class="topics-list">
        <el-card v-for="topic in filteredTopics" :key="topic.id" class="topic-card">
          <div class="topic-header">
            <div class="topic-info">
              <h3>{{ topic.title }}</h3>
              <div class="topic-meta">
                <span>发起人: {{ topic.author }}</span>
                <span>发布时间: {{ topic.createTime }}</span>
                <span>回复: {{ topic.replyCount }}</span>
              </div>
            </div>
            <el-tag :type="topic.category.type">{{ topic.category.name }}</el-tag>
          </div>
          <div class="topic-content">{{ topic.content }}</div>
          <div class="topic-actions">
            <el-button type="text" @click="viewTopic(topic)">查看详情</el-button>
            <el-button type="text" @click="replyTopic(topic)">回复</el-button>
          </div>
        </el-card>
      </div>

      <el-pagination
        :current-page="currentPage"
        :page-size="10"
        :total="totalTopics"
        layout="prev, pager, next"
        @current-change="handlePageChange"
      />
    </div>

    <!-- 新建讨论弹窗 -->
    <el-dialog v-model="dialogVisible" title="发起新讨论" width="50%">
      <el-form :model="newTopic" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="newTopic.title" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="newTopic.category">
            <el-option
              v-for="item in categories"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="newTopic.content"
            type="textarea"
            :rows="4"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitTopic">发布</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'DiscussionBoard',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      searchQuery: '',
      currentPage: 1,
      totalTopics: 100,
      dialogVisible: false,
      newTopic: {
        title: '',
        category: '',
        content: ''
      },
      categories: [
        { value: 'question', label: '问题求助' },
        { value: 'share', label: '经验分享' },
        { value: 'discussion', label: '讨论交流' }
      ],
      topics: [
        {
          id: 1,
          title: '如何更好地理解Vue3的组合式API？',
          author: '张三',
          createTime: '2024-03-15 14:30',
          replyCount: 5,
          category: { name: '问题求助', type: 'warning' },
          content: '最近在学习Vue3的组合式API，有些概念理解起来比较困难...'
        }
        // 更多讨论主题数据...
      ]
    }
  },
  computed: {
    filteredTopics() {
      return this.topics.filter(topic =>
        topic.title.toLowerCase().includes(this.searchQuery.toLowerCase())
      )
    }
  },
  methods: {
    createNewTopic() {
      this.dialogVisible = true
    },
    submitTopic() {
      // 处理提交新讨论的逻辑
      this.$message.success('发布成功')
      this.dialogVisible = false
    },
    viewTopic(topic) {
      console.log('Viewing topic:', topic.title)
    },
    replyTopic(topic) {
      console.log('Replying to topic:', topic.title)
    },
    handlePageChange(page) {
      this.currentPage = page
      // 这里将来调用后端API获取对应页的数据
    }
  }
}
</script>

<style scoped>
.discussion {
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

.discussion-content {
  max-width: 1200px;
  margin: 0 auto;
}

.discussion-tools {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.search-input {
  width: 300px;
}

.topic-card {
  margin-bottom: 20px;
}

.topic-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.topic-info h3 {
  margin: 0 0 5px 0;
}

.topic-meta {
  font-size: 14px;
  color: #666;
  display: flex;
  gap: 15px;
}

.topic-content {
  margin: 10px 0;
  color: #333;
}

.topic-actions {
  display: flex;
  gap: 15px;
}
</style> 