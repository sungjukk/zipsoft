<template>
    <div class="chat-container">
        <ChatListRow v-for="(room) in list" :key="room.id" :room="room" />
        <div v-if="list.length <= 0" style="text-align:center">
            <p>채팅방이 없습니다.</p>
        </div>
        <button @click="onClickHandler">test</button>
    </div>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted, getCurrentInstance, onUnmounted} from 'vue';
import { useStore } from 'vuex';
import {ChatRoom} from '@/views/chat/ChatListSection.vue';
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
            list.value = [
                {
                    id : 'asd',
                    title : '테스터2',
                    message : '안녕하냐',
                    sendDate : '20230915203600',
                    noReadCnt : 5
                },
                {
                    id : 'asd1',
                    title : '전영은',
                    message : '안녕하냐ㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴ',
                    sendDate : '20230914203600',
                    noReadCnt : 0
                },
                {
                    id : 'asd2',
                    title : '집현전',
                    message : 'ㅁㄴㅇㅁㄴㅁㄹㅇㄶㄴㅇㅎㄴㅇ',
                    sendDate : '20230115203600',
                    noReadCnt : 3
                },
                {
                    id : 'asd3',
                    title : '이성주',
                    message : '안녕하냐ㅠㅠㅠㅠㅠ',
                    sendDate : '20220915203600',
                    noReadCnt : 0
                }
            ]
        }

        onMounted(() => {
            callApiChatRoomList();
            proxy.$socket.subscribe(`/topic/${store.state.UserStore.id}`, (res : any) => {
                const message = JSON.parse(res.body);
                let room : ChatRoom | undefined = list.value.find((chat) => chat.id === message.id);
                if (room) {
                    room.message = message.message;
                    room.noReadCnt = room.noReadCnt + 1;
                    room.sendDate = message.sendDate
                } else {
                    room = {
                        id : message.id,
                        title : message.title ? message.title : message.userName,
                        message : message.message,
                        sendDate : '20230915203600',
                        noReadCnt : 1
                    }
                }
                console.log(room);
                list.value = list.value.filter((chat) => chat.id != message.id);
                list.value.unshift(room);
            });
        });

        onUnmounted(() => {
            proxy.$socket.unsubscribe(`/topic/${store.state.UserStore.id}`);
        });

        const onClickHandler = () => {

            const data = {
                id : 'asd',
                message : '테스트',
                type: "TALK"
            }

            proxy.$socket.send('/app/chat/send', JSON.stringify(data));
        }

        return {list, onClickHandler}

    }
})
</script>