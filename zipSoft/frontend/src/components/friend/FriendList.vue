<template>
    <div>
    <ul class="my-profile">
      <li>
        <div class="my-profile-row">
          <div class="friend-row">
            <div class="my-profile-img">
              <img src="@/assets/img/empty-user.png" />
            </div>
            <div class="profile-txt">
              <span>테스트</span>
              <p>집에 가고 싶다...</p>
            </div>
          </div>
          <div class="add-friend" @click="searchFriendOnClick">
            <i class="bi bi-person-plus-fill"></i>
          </div>
        </div>
      </li>
    </ul>
    <div class="friend-cnt" @click="handleShowBtnOnclick">
      <p>친구 30</p>
      <i class="bi" :class="isShow ? 'bi-chevron-up' : 'bi-chevron-down'"></i>
    </div>
    <div ref="listEle" class="friend-list-div">
      <ul class="friend-list">
        <li @click="friendOnClick">
          <div class="friend-row">
            <div class="friend-img">
              <img src="@/assets/img/empty-user.png" />
            </div>
            <div class="friend-txt">
              <span>테스트</span>
              <p>집에 가고 싶다...</p>
            </div>
          </div>
        </li>
        <li>
          <div class="friend-row">
            <div class="friend-img">
              <img src="@/assets/img/empty-user.png" />
            </div>
            <div class="friend-txt">
              <span>테스트</span>
            </div>
          </div>
        </li>
        <li>
          <div class="friend-row">
            <div class="friend-img">
              <img src="@/assets/img/empty-user.png" />
            </div>
            <div class="friend-txt">
              <span>테스트</span>
            </div>
          </div>
        </li>
        <li>
          <div class="friend-row">
            <div class="friend-img">
              <img src="@/assets/img/empty-user.png" />
            </div>
            <div class="friend-txt">
              <span>테스트</span>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <Popup v-model:isShow="isModalShow">
    <Profile />
  </Popup>
  <Popup v-model:isShow="isSeachFriend">
    <SearchFriend @friendBtnOnclick="friendOnClick" />
  </Popup>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted} from 'vue';
import Popup from '@/components/modal/Popup.vue';
import Profile from './popup/Profile.vue';
import SearchFriend from './popup/SearchFriend.vue';

export default defineComponent({
    name: 'FriendList',
    components: {Popup, Profile, SearchFriend},
    setup() {
        const isShow = ref(true);
        const listEle = ref<HTMLDivElement>();
        const listHeight = ref(0);

        const modalEle = ref<HTMLDivElement>();

        const isModalShow = ref(false);
        const isSeachFriend = ref(false);


        onMounted(() => {

        if (listEle.value) {
            listHeight.value = Number(listEle.value.clientHeight);
            listEle.value.style.height = `${listHeight.value}px`;    
        }

        })

        const handleShowBtnOnclick = () => {

          if (!listEle.value) return false;

          if (isShow.value) {
              listHeight.value = Number(listEle.value.clientHeight);
          }

          isShow.value = !isShow.value;
          listEle.value.style.height = isShow.value ? `${listHeight.value}px` : '0px';
        
        }

        const friendOnClick = () => {
          isModalShow.value = true;
        }

        const searchFriendOnClick = () => {
          isSeachFriend.value = true;
        }


        return {isShow, listEle, handleShowBtnOnclick,  modalEle, isModalShow, friendOnClick, searchFriendOnClick, isSeachFriend};
    }
})
</script>