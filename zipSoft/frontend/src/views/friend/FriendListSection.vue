<template>
  <Friend />
</template> 

<script lang="ts">
import {defineComponent} from 'vue';
import Friend from '@/components/friend/Friend.vue';

export interface FRIEND {
  id : number;
  userId : number;
  userName : string;
  friendId : number;
  friendName : string;
  comment : string;
  acceptYn : string;
}

export default defineComponent({
  name : 'FriendListSection',
  components: {
    Friend
  }
});

</script>