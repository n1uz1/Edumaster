<template>
  <div class="social-learning">
    <header class="header">
      <div class="header-left">
        <h1>社交学习</h1>
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

    <div class="content">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="学习小组" name="groups">
          <div class="group-list">
            <el-card v-for="group in studyGroups" :key="group.id" class="group-card">
              <template #header>
                <div class="card-header">
                  <span>{{ group.name }}</span>
                  <el-tag>{{ group.memberCount }}人</el-tag>
                </div>
              </template>
              <div class="card-content">
                <p>{{ group.description }}</p>
                <div class="group-tags">
                  <el-tag v-for="tag in group.tags" :key="tag" size="small" type="info">
                    {{ tag }}
                  </el-tag>
                </div>
                <el-button type="primary" @click="joinGroup(group)">
                  {{ group.joined ? '进入小组' : '加入小组' }}
                </el-button>
              </div>
            </el-card>
          </div>
        </el-tab-pane>

        <el-tab-pane label="学习伙伴" name="partners">
          <div class="partner-list">
            <el-card v-for="partner in learningPartners" :key="partner.id" class="partner-card">
              <div class="partner-info">
                <el-avatar :src="partner.avatar"></el-avatar>
                <div class="partner-details">
                  <h3>{{ partner.name }}</h3>
                  <p>{{ partner.interests }}</p>
                </div>
                <el-button type="primary" @click="connectPartner(partner)">
                  {{ partner.connected ? '发送消息' : '添加伙伴' }}
                </el-button>
              </div>
            </el-card>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SocialLearning',
  data() {
    return {
      showLearningMenu: false,
      showAdviceMenu: false,
      activeTab: 'groups',
      studyGroups: [
        {
          id: 1,
          name: 'Web开发学习小组',
          memberCount: 25,
          description: '一起学习Web开发技术，分享学习经验',
          tags: ['HTML', 'CSS', 'JavaScript'],
          joined: false
        }
      ],
      learningPartners: [
        {
          id: 1,
          name: '张三',
          avatar: '/avatar1.jpg',
          interests: '对Web开发和人工智能感兴趣',
          connected: false
        }
      ]
    }
  },
  methods: {
    joinGroup(group) {
      console.log('Joining group:', group.name)
    },
    connectPartner(partner) {
      console.log('Connecting with partner:', partner.name)
    }
  }
}
</script>

<style scoped>
.social-learning {
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

.content {
  max-width: 1200px;
  margin: 0 auto;
}

.group-card, .partner-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.group-tags {
  margin: 10px 0;
  display: flex;
  gap: 5px;
}

.partner-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.partner-details {
  flex-grow: 1;
}

.partner-details h3 {
  margin: 0 0 5px 0;
}

.partner-details p {
  margin: 0;
  color: #666;
}
</style> 