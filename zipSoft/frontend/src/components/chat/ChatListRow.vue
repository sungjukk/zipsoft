<template>
    <div class="chat_list">
        <div class="chat_people">
            <div class="chat_img">
                <div class="profile-div chat-profile">
                    <img :src="room.id" alt="sunil" @error="errorImg">
                </div>
            </div>
            <div class="chat_ib" @click="onDetailClick">
                <h5>{{room.title}} <span class="chat_date">{{convert12H(room.sendDate)}}</span></h5>
                <p>{{room.lastMessage}}</p>
                <div class="no_read_cnt" v-if="room.noReadCnt > 0">{{room.noReadCnt}}</div>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import {ref, PropType} from 'vue';
import {ChatRoom} from '@/views/chat/ChatListSection.vue';
import {convert12H} from '@/utils/CommonUtil';
import { useRouter } from 'vue-router';
import {RouteUrl} from '@/router/index';

const {room} = defineProps({
    room : Object as PropType<ChatRoom>
});

const route = useRouter();

const onDetailClick = () => {
    if (room) {
        route.push(RouteUrl.CHAT_ROOM.replace(':id',room.id));
    }
}

const errorImg = (e : any) => {
    e.target.src = require('@/assets/img/user.png');
    e.target.style.width = '15px';
}

</script>