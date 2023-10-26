<template>
  <MemberEdit />
</template> 

<script lang="ts">
import {defineComponent} from 'vue';
import MemberEdit from '@/components/member/MemberEdit.vue';

export default defineComponent({
  name : 'FriendListSection',
  components : {
    MemberEdit
  }
});

</script>