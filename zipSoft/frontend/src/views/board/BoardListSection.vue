<template>
  <BoardList />
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import BoardList from '@/components/board/BoardList.vue';

export interface Board {
  no : Number;
  id : String;
  subject: String;
  content : String;
  userName : String;
  updateDt : String;
  fileId? : Number;
  viewCnt : Number;
  commentCnt : Number;
}

export default defineComponent({
  name : 'BoardListSection',
  components: {
    BoardList
  }
});

</script>