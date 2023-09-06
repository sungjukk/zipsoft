<template>
    <div class="d-flex" :class="isRoot ? 'mb-4' : 'mt-4'">
        <!-- Parent comment-->
        <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..."></div>
        <div class="ms-3" style="width : 100%">
            <div class="comment-title">
                <div class="fw-bold">{{comment.userName}}</div>
                <div class="comment-time">{{timeForToday(comment.regDt)}}</div>
            </div>
            <div>{{comment.comment}}</div>
            <div>
                <a href="javascript:;">댓글 숨기기</a>
                <a href="javascript:;" class="comment-time" @click="onWriteBtnClick">{{!isWrite ? '댓글 쓰기' : '댓글 취소'}}</a>
            </div>

            <div v-if="isWrite">
                <CommentWrite :boardId="comment.boardId" :parentId="comment.id" @callback="$emit('writeCallback')" />
            </div>
            

            <slot />

        </div>
    </div>
</template>
<script lang="ts" setup>
import {defineProps, PropType, ref, defineEmits} from 'vue';
import {BoardComment} from './CommentList.vue';
import {timeForToday} from '@/utils/CommonUtil';
import CommentWrite from './CommentWrite.vue';

const {comment, isRoot} = defineProps({
    comment : Object as PropType<BoardComment>,
    isRoot : Boolean
});

const emit = defineEmits([
    'writeCallback'
])

const isWrite = ref(false);

const onWriteBtnClick = () => {
    isWrite.value = !isWrite.value;
}

</script>