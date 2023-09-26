<template>
    <ChatRoom />
</template>
<script lang="ts">
import {defineComponent} from 'vue';
import ChatRoom from '@/components/chat/ChatRoom.vue';

interface CHAT_ROOM_DETAIL {
    room : CHAT_ROOM,
    list : Array<CHAT_MESSAGE>
}

interface CHAT_ROOM {
    id : String;
    title : String;
    lastMessage? : String;
    noReadCnt : number;
    memberList : Array<CHAT_ROOM_MEMBER>
}

interface CHAT_ROOM_MEMBER {
    id : String;
    chatId : String;
    userId : number;
    userName? : string;
    noReadCnt : number;
    isActive : string;
}

interface CHAT_MESSAGE {
    type : String;
    id : String;
    chatId : String;
    message : String;
    userId : number;
    sendDate : String;
    noReadCnt: number;
}

export {CHAT_ROOM_DETAIL, CHAT_ROOM, CHAT_ROOM_MEMBER, CHAT_MESSAGE}

export default defineComponent({
    name : 'ChatRoomSection',
    components: {
        ChatRoom
    }
})
</script>