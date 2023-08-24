<template>
    <BoardWrite />
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import BoardWrite from '@/components/board/BoardWrite.vue';

export interface BoardWriteDef {
    subject : String;
    content : String;
    file? : File;
}

export default defineComponent({
    name : 'BoardWriteSection',
    components : { BoardWrite }
})
</script>
