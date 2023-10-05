<template>
    <li @click="friendOnClick1">
        <div class="friend-row">
        <div class="friend-img">
            <img src="@/assets/img/empty-user.png" />
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

</script>