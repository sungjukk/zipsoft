<template>
    <div class="chat-container">
        <ChatListRow v-for="(room) in list" :key="room.id" :room="room" />
        <div v-if="list.length <= 0" style="text-align:center">
            <p>채팅방이 없습니다.</p>
        </div>
    </div>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted, getCurrentInstance, onUnmounted} from 'vue';
import { useStore } from 'vuex';
import {ChatRoom} from '@/views/chat/ChatListSection.vue';
import {callGetApi, HTTP_STATUS} from '@/utils/ApiClient';
import ChatListRow from './ChatListRow.vue';
import ChatRoomVue from './ChatRoom.vue';

export default defineComponent({
    name : 'ChatList',
    components : {
        ChatListRow
    },
    setup() {
        
        const {proxy} = getCurrentInstance() as any;
        const store = useStore();
        const list = ref<Array<ChatRoom>>([]);

        const callApiChatRoomList = async () => {

            const res = await callGetApi('/chat');
            console.log(res);
            if (res.result === HTTP_STATUS.OK) {
                list.value = res.data;
            } else {
                proxy.$alert("채팅방 호출 중 오류가 발생하였습니다.");
                return false;
            }

        }

        onMounted(async () => {
            await callApiChatRoomList();

            proxy.$push.onMessage((payload : any) => {
                const message = payload.data;
                let room : ChatRoom | undefined = list.value.find((chat) => chat.id === message.id);
                if (room) {
                    room.lastMessage = message.message;
                    room.noReadCnt = room.noReadCnt + 1;
                    room.sendDate = message.sendDate
                } else {
                    room = {
                        id : message.id,
                        title : message.title ? message.title : message.userName,
                        lastMessage : message.message,
                        sendDate : message.sendDate,
                        noReadCnt : 1
                    }
                }

                list.value = list.value.filter((chat) => chat.id != message.id);
                list.value.unshift(room);

            });
        });

        onUnmounted(() => {
            proxy.$push.onMessage(null);
        });

        return {list}

    }
})
</script>