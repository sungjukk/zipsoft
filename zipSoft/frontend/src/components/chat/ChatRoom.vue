<template>
    <nav class="navbar navbar-light" style="background-color: #249d6a; position : fixed; width : 100%; top : 0px;; color:#ededed">
      <div style="width: 100%; text-align : center">
        <span class="navbar-brand mb-0 h1 chat-back-icon"><i class="bi bi-chevron-left"></i></span>
        <span class="navbar-brand mb-0 h1">테스터2</span>
      </div>
    </nav>
    <div class="chat-container">
        <div class="mesgs">
          <div class="msg_history">
            <ChatMsg v-for="message in msgList.slice().reverse()" :key="message.id" :joinList="joinList" :message="message" />
          </div>
        </div>
    </div>
    <div class="type_msg">
      <div class="input_msg_write">
        <textarea ref="chatInput" class="write_msg" v-model="sendMsg" rows="1" @input="handleChange" />
        <button class="msg_send_btn" type="button" @click="onSendBtnClick"><i class="bi bi-send"></i></button>
      </div>
    </div>
</template>
<script lang="ts">
import {defineComponent, onMounted, getCurrentInstance, ref, onUnmounted} from 'vue';
import {CHAT_ROOM_DETAIL, CHAT_MESSAGE, CHAT_ROOM, CHAT_ROOM_MEMBER} from '@/views/chat/ChatRoomSerction.vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import {callGetApi, HTTP_STATUS} from '@/utils/ApiClient';
import {RouteUrl} from '@/router/index';

import ChatMsg from './ChatMsg.vue';

export default defineComponent({
    name : 'ChatRoom',
    components : {
      ChatMsg
    },
    setup() {
        const {proxy} = getCurrentInstance() as any;
        const route = useRoute();
        const router = useRouter();
        const sendMsg = ref('');
        const chatInput = ref();
        const store = useStore();
        const chatId = ref(route.params.id);
        const joinList = ref<Array<CHAT_ROOM_MEMBER>>([]);
        const msgList = ref<Array<CHAT_MESSAGE>>([]);

        const callApiChatRoomDetail = async () => {
          const res = await callGetApi(`/chat/${route.params.id}`);
          if (res.result == HTTP_STATUS.OK) {
            const data : CHAT_ROOM_DETAIL = res.data;
            const {room, list} = data;
            const {memberList, title} = room;
            store.commit('MenuStore/updatePayload', {
              isShow : false,
              title
            });

            msgList.value = list;
            joinList.value = memberList;
            updateScroll();
          } else {
            proxy.$alert("서버와 연결이 실패하였습니다. 잠시후 다시 이용바랍니다.", () => {
              router.push(RouteUrl.CHAT_LIST);
            });
            return false;
          }
        }

        onMounted(async () => {
          await callApiChatRoomDetail();
            proxy.$socket.subscribe(`/topic/chat/${route.params.id}`, (res : any) => {
                const body : CHAT_MESSAGE = JSON.parse(res.body);
                msgList.value.unshift(body);
                updateScroll();
            });
            const data = {
                id : `${route.params.id}`,
                message : '',
                type: "ENTER",
                userName: store.state.UserStore.name
            }
            proxy.$socket.send('/app/chat/send', JSON.stringify(data));
        });

        onUnmounted(() => {
            store.commit('MenuStore/init');
            proxy.$socket.unsubscribe(`/topic/chat/${chatId.value}`);
        })


        const onSendBtnClick = () => {
            const data = {
                id : `${route.params.id}`,
                message : sendMsg.value,
                type: "TALK",
                userName: store.state.UserStore.name
            }

            sendMsg.value = '';
            chatInput.value.style.height = '40px';

            proxy.$socket.send('/app/chat/send', JSON.stringify(data));
        };

        const handleChange = () => {
          chatInput.value.style.height = '0px';
          const scrollHeight = chatInput.value.scrollHeight >= 108 ? 108 : chatInput.value.scrollHeight;
          chatInput.value.style.height = `${scrollHeight}px`;
        };

        const updateScroll = () => {
          setTimeout(() => window.scrollTo(0, document.body.scrollHeight), 10);
        }

        return {sendMsg, onSendBtnClick, chatInput, handleChange, joinList, msgList}
    }
})
</script>