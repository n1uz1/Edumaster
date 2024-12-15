/* eslint-disable */
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/course-management',
    name: 'CourseManagement',
    component: () => import('../views/CourseManagement.vue')
  },
  {
    path: '/course-detail/:id',
    name: 'CourseDetail',
    component: () => import('../views/CourseDetail.vue')
  },
  {
    path: '/assignments',
    name: 'Assignments',
    component: () => import('../views/Assignments.vue')
  },
  {
    path: '/social-learning',
    name: 'SocialLearning',
    component: () => import('../views/SocialLearning.vue')
  },
  {
    path: '/learning-report',
    name: 'LearningReport',
    component: () => import('../views/LearningReport.vue')
  },
  {
    path: '/discussion',
    name: 'Discussion',
    component: () => import('../views/Discussion.vue')
  },
  {
    path: '/course-recommendation',
    name: 'CourseRecommendation',
    component: () => import('../views/CourseRecommendation.vue')
  },
  {
    path: '/learning-path',
    name: 'LearningPath',
    component: () => import('../views/LearningPath.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 