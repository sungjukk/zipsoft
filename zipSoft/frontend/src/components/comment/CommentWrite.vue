<template>
    <!-- Comment form-->
    <form class="mb-4">
        <textarea class="form-control" rows="3"  v-model="content"></textarea>
        <div class="ta-right mt-10">
            <button type="button" class="btn btn-primary" style="font-size : 13px; padding : 10px 30px" @click="callApiInsertComment">댓글 쓰기</button>
        </div>
    </form>
</template>
<script lang="ts" setup>
import { ref, defineProps, defineEmits } from 'vue';
import {callPostApi, HTTP_STATUS} from '@/utils/ApiClient';

const props = defineProps({
    boardId : Number,
    parentId : Number
});

const emit = defineEmits([
    'callback'
]);

const content = ref('');

const callApiInsertComment = async () => {
    const res = await callPostApi(`/board/${props.boardId}/comment`,{parentId : props.parentId, comment : content.value});

    if (res.result === HTTP_STATUS.OK) {
        console.log("등록 완료");
        content.value = '';
        emit('callback');
    }

}
</script>