<template>
  <div class="assignments">
    <header class="header">
      <h1>在线作业与考试</h1>
      <el-button @click="$router.push('/')">返回首页</el-button>
      <div class="user-info">xxxxxxx XXX</div>
    </header>

    <div class="content">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="作业" name="homework">
          <div class="homework-list">
            <el-card v-for="item in homeworkList" :key="item.id" class="assignment-card">
              <template #header>
                <div class="card-header">
                  <span>{{ item.title }}</span>
                  <el-tag :type="item.status === '已完成' ? 'success' : 'warning'">
                    {{ item.status }}
                  </el-tag>
                </div>
              </template>
              <div class="card-content">
                <p>截止日期: {{ item.deadline }}</p>
                <p>{{ item.description }}</p>
                <el-button type="primary" @click="startAssignment(item)">
                  {{ item.status === '已完成' ? '查看详情' : '开始作业' }}
                </el-button>
              </div>
            </el-card>
          </div>
        </el-tab-pane>

        <el-tab-pane label="考试" name="exam">
          <div class="exam-list">
            <el-card v-for="item in examList" :key="item.id" class="assignment-card">
              <template #header>
                <div class="card-header">
                  <span>{{ item.title }}</span>
                  <el-tag :type="getExamStatusType(item.status)">
                    {{ item.status }}
                  </el-tag>
                </div>
              </template>
              <div class="card-content">
                <p>考试时间: {{ item.examTime }}</p>
                <p>时长: {{ item.duration }}</p>
                <p>{{ item.description }}</p>
                <el-button type="primary" @click="startExam(item)"
                          :disabled="item.status === '已完成'">
                  {{ getExamButtonText(item.status) }}
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
  name: 'AssignmentsPage',
  data() {
    return {
      activeTab: 'homework',
      homeworkList: [
        {
          id: 1,
          title: '第一章作���',
          status: '未完成',
          deadline: '2024-03-20',
          description: '完成第一章的练习题'
        }
        // 更多作业数据...
      ],
      examList: [
        {
          id: 1,
          title: '期中考试',
          status: '未开始',
          examTime: '2024-03-25 14:00',
          duration: '120分钟',
          description: '覆盖第1-5章内容'
        }
        // 更多考试数据...
      ]
    }
  },
  methods: {
    startAssignment(item) {
      console.log('Starting assignment:', item.title)
    },
    startExam(item) {
      console.log('Starting exam:', item.title)
    },
    getExamStatusType(status) {
      const types = {
        '未开始': 'info',
        '进行中': 'warning',
        '已完成': 'success'
      }
      return types[status] || 'info'
    },
    getExamButtonText(status) {
      const texts = {
        '未开始': '进入考试',
        '进行中': '继续考试',
        '已完成': '查看成绩'
      }
      return texts[status] || '进入考试'
    }
  }
}
</script>

<style scoped>
.assignments {
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

.assignment-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  line-height: 1.6;
}

.card-content button {
  margin-top: 10px;
}
</style> 