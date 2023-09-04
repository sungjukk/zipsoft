<template>
    <BoardDetail />
</template>
<script lang="ts">
export interface BoardDetail {
    id : String;
    subject: String;
    content : String;
    userName : String;
    updateDt : String;
    viewCnt : Number;
    fileList? : Array<BoardFile>
}

export interface BoardFile {
    id : String;
    orgFileName : String;
    fileName : String;
    fileSize : Number;
    ext : String;
}

import BoardDetail from '@/components/board/BoardDetail.vue'
import { defineComponent } from 'vue'

export default defineComponent({
    name : 'BoardDetailSection',
    components: {
        BoardDetail
    },
    setup() {
        
    },
})
</script>
