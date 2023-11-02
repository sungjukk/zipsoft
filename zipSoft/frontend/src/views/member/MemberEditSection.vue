<template>
  <MemberEdit />
</template> 

<script lang="ts">

export interface MEMBER {
  id : number;
  userId : string;
  userName : string;
  email : string;
  thumbId : number;
}

import {defineComponent} from 'vue';
import MemberEdit from '@/components/member/MemberEdit.vue';

export default defineComponent({
  name : 'FriendListSection',
  components : {
    MemberEdit
  }
});

</script>