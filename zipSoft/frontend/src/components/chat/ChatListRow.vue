<template>
    <div class="chat_list">
        <div class="chat_people">
            <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
            <div class="chat_ib" @click="onDetailClick">
                <h5>{{room.title}} <span class="chat_date">{{convert12H(room.updateDt)}}</span></h5>
                <p>{{room.message}}</p>
                <div class="no_read_cnt">{{room.noReadCnt}}</div>
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

</script>