<template>
  <FriendList />
</template> 

<script lang="ts">
import {defineComponent} from 'vue';
import FriendList from '@/components/friend/FriendList.vue';


export default defineComponent({
  name : 'FriendListSection',
  components: {
    FriendList
  }
});

</script>