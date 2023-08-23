import { createRouter, createWebHistory } from 'vue-router';
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import Login from '@/views/login/Login.vue'
import store from '@/store/index';

export interface MenuDef {
  path: string;
  name: string;
  component: any;
}

export enum RouteUrl  {
  MAIN = '/',
  ABOUT = '/about',
  BOARD = '/board',
  LOGIN = '/login',
}

export const routes = [
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
    component: () => import(/* webpackChunkName: "about" */ '../views/PageAbout.vue')
  },
  {
    path: RouteUrl.BOARD,
    name: 'BoardList',
    component: BoardList
  },
] as Array<MenuDef>;

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