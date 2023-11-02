<template>
    <li @click="friendOnClick1">
        <div class="friend-row">
            <Thumbnail :size="50" :id="friend.friendId" />
            <div class="friend-txt">
                <span>{{friend.friendName}}</span>
                <p v-if="friend.comment">{{friend.comment}}</p>
            </div>
        </div>
    </li>
</template>
<script lang="ts" setup>
import {PropType, defineEmits, ref} from 'vue';
import {FRIEND} from '@/views/friend/FriendListSection.vue';
import Thumbnail from '@/components/layouts/Thumbnail.vue';

const {friend} = defineProps({
    friend : { type : Object as PropType<FRIEND>, required : true}
});

const emit = defineEmits([
  'friendOnClick'
])

const friendOnClick1 = () => {
    emit('friendOnClick', friend.friendId);
}

</script>