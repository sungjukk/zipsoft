<template>
    <div>
        <ul class="my-profile">
            <li>
                <div class="my-profile-row">
                    <div class="friend-row">
                        <div class="my-profile-img">
                          <img :src="$store.state.UserStore.thumbnail" @error="errorImg" />
                        </div>
                        <div class="profile-txt">
                        <span>{{$store.state.UserStore.name}}</span>
                        <p v-if="$store.state.UserStore.comment">집에 가고 싶다...</p>
                        </div>
                    </div>
                    <div class="add-friend" @click="searchFriendOnClick">
                        <i class="bi bi-person-plus-fill"></i>
                    </div>
                </div>
            </li>
        </ul>
        <FriendList :friendList="friendList" @friendOnClick="friendOnClick" />
  </div>
  <Popup v-model:isShow="isModalShow">
    <Profile />
  </Popup>
  <Popup v-model:isShow="isSeachFriend">
    <SearchFriend @friendBtnOnclick="friendOnClick" />
  </Popup>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted, getCurrentInstance} from 'vue';
import { useStore } from 'vuex';

import Popup from '@/components/modal/Popup.vue';
import Profile from './popup/Profile.vue';
import SearchFriend from './popup/SearchFriend.vue';
import FriendList from './FriendList.vue';
import {callGetApi, HTTP_STATUS} from '@/utils/ApiClient';
import {FRIEND} from '@/views/friend/FriendListSection.vue';

export default defineComponent({
    name: 'Friend',
    components: {Popup, Profile, SearchFriend, FriendList},
    setup() {

        const store = useStore();
        const modalEle = ref<HTMLDivElement>();

        const isModalShow = ref(false);
        const isSeachFriend = ref(false);
        const {proxy} = getCurrentInstance() as any;
        const friendList = ref<Array<FRIEND>>([]);


        onMounted(async () => {
          await callApiFriendList();
        });


        const callApiFriendList =  async () => {
          const res = await callGetApi('/friend');

          if (res.result == HTTP_STATUS.OK) {
            const {data} = res;
            if (data) friendList.value = data;

            friendList.value = [{
              id : 1,
              userId : 1,
              userName : '',
              friendId : 2,
              friendName : '테스터2',
              comment : '리니지m',
              acceptYn : 'Y'
            }];

          } else {
            proxy.$alert('친구 목록을 가져오는 중 오류가 발생하였습니다.');
            return false;
          }

          console.log(res);
        }

        const errorImg = (e : any) => {
          e.target.src = require('@/assets/img/empty-user.png');
        }

        const friendOnClick = (userId : number) => {
          console.log(userId);
          isModalShow.value = true;
        }

        const searchFriendOnClick = () => {
          isSeachFriend.value = true;
        }


        return {modalEle, isModalShow, friendOnClick, searchFriendOnClick, isSeachFriend, friendList, errorImg};
    }
})
</script>