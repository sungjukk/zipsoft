import { createRouter, createWebHistory } from 'vue-router';
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import Login from '@/views/login/Login.vue'
import store from '@/store/index';

export const RouteUrl = {
  MAIN : '/',
  ABOUT : '/about',
  BOARD : '/board',
  LOGIN : '/login',
};

const routes = [
  {
    path: RouteUrl.MAIN,
    name: 'PageHome',
    component: PageHome
  },
  {
    path: RouteUrl.LOGIN,
    name: 'Login',
    component: Login
  },
  {
    path: RouteUrl.ABOUT,
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/PageAbout.vue')
  },
  {
    path: RouteUrl.BOARD,
    name: 'BoardList',
    component: BoardList
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach(async (to, from, next) => {
  await store.dispatch('UserStore/loginCheck');
  console.log(store.getters['UserStore/isLogin']);
  //return next({ path: "/login" });
  next();
});


export default router;