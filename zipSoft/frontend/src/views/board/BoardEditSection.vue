<template>
    <BoardEdit />
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import BoardEdit from '@/components/board/BoardEdit.vue';

export interface BoardEditDef {
    subject : String;
    content : String;
    file? : HTMLInputElement;
}

export default defineComponent({
    name : 'BoardWriteSection',
    components : { BoardEdit }
})
</script>
