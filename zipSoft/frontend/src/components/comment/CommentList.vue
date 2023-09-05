<template>
    <section class="mb-5">
        <div>
            <div class="card-body">
                <!-- Comment form-->
                <form class="mb-4">
                    <textarea class="form-control" rows="3" placeholder="Join the discussion and leave a comment!" v-model="content"></textarea>
                    <div class="ta-right mt-10">
                        <button type="button" class="btn btn-primary" style="font-size : 13px; padding : 10px 30px" @click="callApiInsertComment">댓글 쓰기</button>
                    </div>
                </form>
                <!-- Comment with nested comments-->
                <div class="d-flex mb-4">
                    <!-- Parent comment-->
                    <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..."></div>
                    <div class="ms-3">
                        <div class="comment-title">
                            <div class="fw-bold">Commenter Name</div>
                            <div class="comment-time">13:00</div>
                        </div>
                        <div>If you're going to lead a space frontier, it has to be government; it'll never be private enterprise. Because the space frontier is dangerous, and it's expensive, and it has unquantified risks.</div>
                        <div>
                            <a href="javascript:;" class="comment-time">댓글 쓰기</a>
                        </div>
                        <!-- Child comment 1-->
                        <div class="d-flex mt-4">
                            <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..."></div>
                            <div class="ms-3">
                                <div class="fw-bold">Commenter Name</div>
                                And under those conditions, you cannot establish a capital-market evaluation of that enterprise. You can't get investors.
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Single comment-->
                <div class="d-flex">
                    <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..."></div>
                    <div class="ms-3">
                        <div class="fw-bold">Commenter Name</div>
                        When I look at the universe and all the ways the universe wants to kill us, I find it hard to reconcile that with statements of beneficence.
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>
<script lang="ts">
interface BoardComment {
    id : String;
    boardId : String;
    parentId : String;
    comment : String;
    userName : String;
    userId : string;
    updateDt : String;
}

import {defineComponent, ref} from 'vue';
import {callPostApi, HTTP_STATUS} from '@/utils/ApiClient';

export default defineComponent({
    name: 'CommentList',
    props: {
        boardId : {type:String}
    },
    setup(props) {
        const data = ref<BoardComment>();
        const content = ref('');
        const callApiInsertComment = async () => {
            const res = await callPostApi(`/board/${props.boardId}/comment`,{parentId : 0, comment : content.value});

            if (res.result === HTTP_STATUS.OK) {
                console.log("등록 완료");
            }
        }

        return {data, content, callApiInsertComment}

    }
})

</script>