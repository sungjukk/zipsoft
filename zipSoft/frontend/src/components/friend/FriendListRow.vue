<template>
    <li @click="friendOnClick1">
        <div class="friend-row">
        <div class="friend-img profile-div">
            <img :src="$store.state.UserStore.thumbnail" @error="errorImg" />
        </div>
        <div class="friend-txt">
            <span>{{friend.friendName}}</span>
            <p v-if="friend.comment">{{friend.comment}}</p>
        </div>
        </div>
    </li>
</template>
<script lang="ts" setup>
import {PropType, defineEmits} from 'vue';
import {FRIEND} from '@/views/friend/FriendListSection.vue';
const {friend} = defineProps({
    friend : { type : Object as PropType<FRIEND>, required : true}
});

const emit = defineEmits([
  'friendOnClick'
])

const friendOnClick1 = () => {
    console.log('asdasd');
    emit('friendOnClick', friend.friendId);
}

const errorImg = (e : any) => {
    e.target.src = require('@/assets/img/user.png');
    e.target.style.width = '18px';
}

</script>