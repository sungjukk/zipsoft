import { createRouter, createWebHistory } from 'vue-router';
import PageHome from '@/views/PageHome.vue'
import BoardListSection from '@/views/board/BoardListSection.vue'
import BoardDetailSection from '@/views/board/BoardDetailSection.vue';
import BoardWriteSection from '@/views/board/BoardWriteSection.vue'
import Login from '@/views/login/Login.vue'
import store from '@/store/index';

export interface MenuDef {
  path: string;
  name: string;
  component: any;
  meta: any;
}

export enum RouteUrl  {
  MAIN = '/',
  ABOUT = '/about',
  BOARD = '/board',
  LOGIN = '/login',
  BOARD_DETAIL = '/board/:id',
  BOARD_WRITE = '/board/new'
}

export const routes = [
  {
    path: RouteUrl.MAIN,
    name: 'PageHome',
    component: PageHome,
    meta : {
      unauthorized: true,
      isShow: true
    }
  },
  {
    path: RouteUrl.LOGIN,
    name: 'Login',
    component: Login,
    meta : {
      unauthorized: true,
      isShow: false,
      layout : 'NoLayout'
    }
  },
  {
    path: RouteUrl.ABOUT,
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/PageAbout.vue'),
    meta : {
      unauthorized: true,
      isShow: true
    }
  },
  {
    path: RouteUrl.BOARD,
    name: 'BoardList',
    component: BoardListSection,
    meta : {
      unauthorized: true,
      isShow: true
    }
  },
  {
    path: RouteUrl.BOARD_WRITE,
    name: 'BoardWriteSection',
    component: BoardWriteSection,
    meta : {
      unauthorized: true,
      isShow: false
    }
  },
  {
    path: RouteUrl.BOARD_DETAIL,
    name: 'BoardDetailSection',
    component: BoardDetailSection,
    meta : {
      unauthorized: true,
      isShow: false
    }
  }
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