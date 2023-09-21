import { createRouter, createWebHistory } from 'vue-router';
import PageHome from '@/views/PageHome.vue'
import BoardListSection from '@/views/board/BoardListSection.vue'
import BoardDetailSection from '@/views/board/BoardDetailSection.vue';
import BoardWriteSection from '@/views/board/BoardWriteSection.vue';
import BoardEditSection from '@/views/board/BoardEditSection.vue';

import ChatListSection from '@/views/chat/ChatListSection.vue';
import ChatRoomSection from '@/views/chat/ChatRoomSerction.vue';

import Login from '@/views/login/Login.vue'
import store from '@/store/index';

export interface MenuDef {
  id: number,
  parentId: number,
  order: number,
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
  BOARD_WRITE = '/board/new',
  BOARD_EDIT = '/board/:id/edit',
  CHAT_LIST = '/chat',
  CHAT_ROOM = '/chat/:id'
}

export const routes = [
  {
    path: RouteUrl.MAIN,
    name: '메인',
    component: PageHome,
    meta : {
      unauthorized: true,
      isShow: true,
      mobileIcon: 'bi-house-fill',
      id: -1,
      parentId: 0,
      order: 1
    }
  },
  {
    path: RouteUrl.MAIN,
    name: '친구',
    component: PageHome,
    meta : {
      unauthorized: true,
      isShow: true,
      mobileIcon: 'bi-people-fill',
      id: 1,
      parentId: 0,
      order: 2
    }
  },
  {
    path: RouteUrl.LOGIN,
    name: 'Login',
    component: Login,
    meta : {
      unauthorized: true,
      isShow: false,
      layout : 'NoLayout',
      id: 2,
      parentId: 0,
      order: 1
    }
  },
  {
    path: RouteUrl.ABOUT,
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/PageAbout.vue'),
    meta : {
      unauthorized: false,
      isShow: false,
      id: 3,
      parentId: 0,
      order: 1
    }
  },
  {
    path: RouteUrl.BOARD,
    name: '게시판',
    component: BoardListSection,
    meta : {
      unauthorized: false,
      isShow: true,
      mobileIcon: 'bi-card-list',
      id: 4,
      parentId: 0,
      order: 4
    }
  },
  {
    path: RouteUrl.BOARD_WRITE,
    name: '글쓰기',
    component: BoardWriteSection,
    meta : {
      unauthorized: false,
      isShow: false,
      id: 5,
      parentId: 4,
      order: 0
    }
  },
  {
    path: RouteUrl.BOARD_DETAIL,
    name: '글 상세',
    component: BoardDetailSection,
    meta : {
      unauthorized: false,
      isShow: false,
      id: 6,
      parentId: 4,
      order: 0
    }
  },
  {
    path: RouteUrl.BOARD_EDIT,
    name: '글 수정',
    component: BoardEditSection,
    meta : {
      unauthorized: false,
      isShow: false,
      id: 7,
      parentId: 4,
      order: 0
    }
  },
  {
    path: RouteUrl.CHAT_LIST,
    name: '채팅',
    component: ChatListSection,
    meta : {
      unauthorized: false,
      isShow: true,
      mobileIcon: 'bi-chat-fill',
      id: 8,
      parentId: 0,
      order: 3
    }
  },
  {
    path: RouteUrl.CHAT_ROOM,
    name: 'ChatRoomSection',
    component: ChatRoomSection,
    meta : {
      unauthorized: false,
      isShow: false,
      id: 9,
      parentId: 8,
      order: 0
    }
  }
] as Array<MenuDef>;

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach(async (to, from, next) => {
  const {meta : {unauthorized}} = to;
  if (!unauthorized) {
    const isSucc = await store.dispatch('UserStore/loginCheck');
    if (!isSucc) {
      return next({path : RouteUrl.LOGIN});
    }
  }
  next();

  //return next({ path: "/login" });
});


export default router;