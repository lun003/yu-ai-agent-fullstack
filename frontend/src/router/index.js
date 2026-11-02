import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/love_app',
      name: 'love_app',
      component: () => import('../views/LoveAppView.vue')
    },
    {
      path: '/manus',
      name: 'manus',
      component: () => import('../views/ManusView.vue')
    }
  ]
})

export default router
