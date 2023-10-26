<template>
  <ChatList />
</template>

<script lang="ts">
import ChatList from '@/components/chat/ChatList.vue';
import {defineComponent} from 'vue';

export interface ChatRoom {
  id : String;
  title : String;
  lastMessage : String;
  sendDate : String;
  noReadCnt : number;
}

export default defineComponent({
  name : 'ChatListSection',
  components: {
    ChatList
  }
});

</script>