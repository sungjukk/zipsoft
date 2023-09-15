<template>
    <div class="chat_list">
        <div class="chat_people">
            <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
            <div class="chat_ib">
                <h5>{{room.title}} <span class="chat_date">{{convert12H(room.updateDt)}}</span></h5>
                <p>{{room.message}}</p>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import {ref, PropType} from 'vue';
import {ChatRoom} from '@/views/chat/ChatListSection.vue';
import {convert12H} from '@/utils/CommonUtil';

const {room} = defineProps({
    room : Object as PropType<ChatRoom>
});

</script>