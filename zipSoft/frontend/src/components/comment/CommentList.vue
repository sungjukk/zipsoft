<template>
    <section class="mb-5">
        <div>
            <div class="card-body">
                <!-- Comment form-->
                <CommentWrite :boardId="boardId" :parentId="0" @callback="callApiCommentList" />
                <CommentListRow v-for="(comment) in commentList" :key="comment.id" :comment="comment" :isRoot="true" @writeCallback="callApiCommentList">
                    <CommentListRow v-for="(child) in comment.childList" :key="child.id" :comment="child" :isRoot="false" @writeCallback="callApiCommentList" />
                </CommentListRow>
            </div>
        </div>
    </section>
</template>
<script lang="ts">
export interface BoardComment {
    id : Number;
    boardId : Number;
    parentId : Number;
    comment : String;
    userName : String;
    regId : Number;
    regDt : String;
    rootId : Number;
    childList? : Array<BoardComment>
}

import {defineComponent, ref, onMounted, watch} from 'vue';
import {callGetApi, HTTP_STATUS} from '@/utils/ApiClient';

import CommentWrite from './CommentWrite.vue';
import CommentListRow from './CommentListRow.vue';

export default defineComponent({
    name: 'CommentList',
    components : {
        CommentListRow, CommentWrite
    },
    props: {
        boardId : {type:Number}
    },
    setup(props) {
        const commentList = ref<Array<BoardComment>>([]);
        
        const callApiCommentList = async () => {
            const res = await callGetApi(`/board/${props.boardId}/comment`);

            if (res.result == HTTP_STATUS.OK) {
                const data = res.data as Array<BoardComment> | null;

                if (data && data.length > 0) {
                    const list = data.filter((d) => d.parentId == 0);

                    list.forEach((d) => {
                        d.childList = data.filter((c) => c.rootId === d.id && c.parentId != 0);
                    });

                    commentList.value = list;
                }

            }
        }

        onMounted(() => {
            if (props.boardId != '0') {
                callApiCommentList();
            }
        })

        watch(props, () => {
            if (props.boardId != '0') {
                callApiCommentList();
            }
        })

        return {commentList, callApiCommentList}

    }
})

</script>