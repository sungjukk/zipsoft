<template>
    <BoardDetail />
</template>
<script lang="ts">
export interface BoardDetail {
    id : Number;
    subject: String;
    content : String;
    userName : String;
    updateDt : String;
    viewCnt : Number;
    regId: Number;
    fileList? : Array<BoardFile>
}

export interface BoardFile {
    id : Number;
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
