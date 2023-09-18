<template>
    <ChatRoom />
</template>
<script lang="ts">
import {defineComponent} from 'vue';
import ChatRoom from '@/components/chat/ChatRoom.vue';

export default defineComponent({
    name : 'ChatRoomSection',
    components: {
        ChatRoom
    }
})
</script>