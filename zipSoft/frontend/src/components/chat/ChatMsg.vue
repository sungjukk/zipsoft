<template>
    <div :class="isUser ? 'outgoing_msg' : 'incoming_msg'">
        <div v-if="!isUser" class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
        <div v-if="isUser" class="sent_msg">
            <p>{{message.message}}</p>
            <span class="time_date"> {{convert12H(message.sendDate)}}</span> 
        </div>
        <div v-else class="received_msg">
        <div class="received_withd_msg">
            <span>{{userName}}</span>
            <p>{{message.message}}</p>
            <span class="time_date"> {{convert12H(message.sendDate)}}</span></div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import {ref, PropType, computed} from 'vue';
import {useStore} from 'vuex';
import {CHAT_MESSAGE, CHAT_ROOM_MEMBER} from '@/views/chat/ChatRoomSerction.vue';
import {convert12H} from '@/utils/CommonUtil';
const {message, joinList} = defineProps({
    message : {
        type: Object as PropType<CHAT_MESSAGE>,
        required : true
    },
    joinList : {
        type : Array as PropType<Array<CHAT_ROOM_MEMBER>>,
        required : true
    }
});

const store = useStore();
const isUser = ref(message.userId == store.state.UserStore.id);

const userName = computed(() => {
    const member = joinList.find((m : any) => m.userId === message?.userId);
    if (member) return member?.userName;
    else "알수없음";
});

</script>