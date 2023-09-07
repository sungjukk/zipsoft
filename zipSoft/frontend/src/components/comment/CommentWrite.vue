<template>
    <!-- Comment form-->
    <form class="mb-4">
        <Editor ref="content" style="height : 100px"/>
        <div class="ta-right mt-10">
            <button type="button" class="btn btn-primary" style="font-size : 13px; padding : 10px 30px" @click="callApiInsertComment">댓글 쓰기</button>
        </div>
    </form>
</template>
<script lang="ts" setup>
import { ref, defineProps, defineEmits } from 'vue';
import {callPostApi, HTTP_STATUS} from '@/utils/ApiClient';

import Editor from '../editor/Editor.vue';

const props = defineProps({
    boardId : Number,
    parentId : Number
});

const emit = defineEmits([
    'callback'
]);

const content = ref();

const callApiInsertComment = async () => {
    if (!content.value.editorValidationCheck('댓글')) return false;

    const res = await callPostApi(`/board/${props.boardId}/comment`,{parentId : props.parentId, comment : content.value.state.content});

    if (res.result === HTTP_STATUS.OK) {
        console.log("등록 완료");
        content.value.quill.setText('');
        emit('callback');
    }

}
</script>