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
    path: '/course-recommendation',
    name: 'CourseRecommendation',
    component: () => import('../views/CourseRecommendation.vue')
  },
  {
    path: '/learning-forum',
    name: 'LearningForum',
    component: () => import('../views/LearningForum.vue')
  },
  {
    path: '/forum-post/:postId',
    name: 'ForumPost',
    component: () => import('../views/ForumPost.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 