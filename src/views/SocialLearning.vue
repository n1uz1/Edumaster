<template>
  <div class="social-learning">
    <header class="header">
      <h1>社交学习</h1>
      <el-button @click="$router.push('/')">返回首页</el-button>
      <div class="user-info">xxxxxxx XXX</div>
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
        // ��小组数据...
      ],
      learningPartners: [
        {
          id: 1,
          name: '张三',
          avatar: '/avatar1.jpg',
          interests: '对Web开发和人工智能感兴趣',
          connected: false
        }
        // 更多伙伴数据...
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