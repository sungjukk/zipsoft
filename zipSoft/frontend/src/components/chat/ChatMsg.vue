<template>
    <div v-if="message.userId == 0">
        <div class="msg_enter_div">
            <p class="msg_box msg_enter">{{message.message}}</p>
        </div>
    </div>
    <div v-else :class="isUser ? 'outgoing_msg' : 'incoming_msg'">
        <div v-if="!isUser" class="incoming_msg_img"> 
            <Thumbnail :id="sendUserInfo?.userId"  />
        </div>
        <div v-if="isUser" class="sent_msg">
            <div class="time_date">
                <span v-if="message.noReadCnt > 0">{{message.noReadCnt}}</span>
                <span>{{convert12H(message.sendDate)}}</span>
            </div> 
            <p class="msg_box">{{message.message}}</p>
        </div>
        <div v-else class="received_msg">
            <div class="received_withd_msg">
                <div>
                <span style="font-size : 11px">{{sendUserInfo ? sendUserInfo.userName : '알수없음'}}</span>
                <p class="msg_box">{{message.message}}</p>
                </div>
                <div class="time_date"> 
                    <span v-if="message.noReadCnt > 0">{{message.noReadCnt}}</span>
                    <span>{{convert12H(message.sendDate)}}</span>
                </div>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import {ref, PropType, computed} from 'vue';
import {useStore} from 'vuex';
import {CHAT_MESSAGE, CHAT_ROOM_MEMBER} from '@/views/chat/ChatRoomSerction.vue';
import {convert12H} from '@/utils/CommonUtil';
import Thumbnail from '@/components/layouts/Thumbnail.vue';

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

const sendUserInfo = computed(() => {
    return joinList.find((m : any) => m.userId === message?.userId);
});

</script>