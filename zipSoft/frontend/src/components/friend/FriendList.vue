<template>
    <div>
    <ul class="my-profile">
      <li>
        <div class="friend-row">
          <div class="my-profile-img">
            <img src="@/assets/img/empty-user.png" />
          </div>
          <div class="profile-txt">
            <span>테스트</span>
          </div>
        </div>
      </li>
    </ul>
    <div class="friend-cnt">
      <p>친구 30</p>
      <i class="bi" :class="isShow ? 'bi-chevron-up' : 'bi-chevron-down'" @click="handleShowBtnOnclick"></i>
    </div>
    <div ref="listEle" class="friend-list-div">
      <ul class="friend-list">
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
  <div class="popup-content">
    <div class="popup-close-btn">
      <i class="bi bi-x-lg"></i>
    </div>
    <div>
    </div>
  </div>
  <div class="popup-background"></div>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted} from 'vue';

export default defineComponent({
    name: 'FriendList',
    setup() {
        const isShow = ref(true);
        const listEle = ref<HTMLDivElement>();
        const listHeight = ref(0);


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

        return {isShow, listEle, handleShowBtnOnclick};
    }
})
</script>