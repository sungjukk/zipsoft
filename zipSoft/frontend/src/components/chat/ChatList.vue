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
import {defineComponent, ref, onMounted, getCurrentInstance} from 'vue';
import { useStore } from 'vuex';
import {ChatRoom} from '@/views/chat/ChatListSection.vue';
import ChatListRow from './ChatListRow.vue';

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
                    updateDt : '20230915203600',
                    noReadCnt : 5
                },
                {
                    id : 'asd1',
                    title : '전영은',
                    message : '안녕하냐ㅁㄴㅇㅁㅇㄴㅁㅇㅁㄴㅇㅁㄴ',
                    updateDt : '20230914203600',
                    noReadCnt : 0
                },
                {
                    id : 'asd2',
                    title : '집현전',
                    message : 'ㅁㄴㅇㅁㄴㅁㄹㅇㄶㄴㅇㅎㄴㅇ',
                    updateDt : '20230115203600',
                    noReadCnt : 3
                },
                {
                    id : 'asd',
                    title : '이성주',
                    message : '안녕하냐ㅠㅠㅠㅠㅠ',
                    updateDt : '20220915203600',
                    noReadCnt : 0
                }
            ]
        }

        onMounted(() => {
            callApiChatRoomList();
            proxy.$socket.subscribe(`/topic/${store.state.UserStore.id}`, (res : any) => {
                console.log('받아옴');
            });
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