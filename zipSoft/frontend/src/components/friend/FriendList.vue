<template>
    <div>
      <div class="friend-cnt" @click="handleShowBtnOnclick">
        <p>친구 {{friendList.length}}</p>
        <i class="bi" :class="isShow ? 'bi-chevron-up' : 'bi-chevron-down'"></i>
      </div>
        <div class="friend-list-div" v-if="friendList.length &gt; 0">
          <Transition name="friend">
            <ul v-if="isShow" class="friend-list">
              <FriendListRow v-for="friend, i in friendList" :key="i" :friend="friend" @friendOnClick="(userId) => $emit('friendOnClick', userId)" />
            </ul>
          </Transition>
        </div>
        <div class="friend-list-not-found" v-else>
          <p>등록된 친구가 없습니다.</p>
        </div>
  </div>
</template>
<script lang="ts">
import {defineComponent, ref, PropType} from 'vue';
import FriendListRow from './FriendListRow.vue';
import {FRIEND} from '@/views/friend/FriendListSection.vue';

export default defineComponent({
    name: 'FriendList',
    components: {FriendListRow},
    props: {
      friendList : {type:Object as PropType<Array<FRIEND>>}
    },
    setup(props) {
        const isShow = ref(true);

        const handleShowBtnOnclick = () => {
          isShow.value = !isShow.value;
        }


        return {isShow, handleShowBtnOnclick};
    }
})
</script>