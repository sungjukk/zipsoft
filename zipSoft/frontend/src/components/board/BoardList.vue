<template>
    <div>
        <table class="table table-hover">
            <thead class="table-light">
                <tr>
                <th scope="col">No</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">날짜</th>
                <th scope="col">조회수</th>
                <th scope="col">댓글수</th>
                </tr>
            </thead>
            <tbody class="table-group-divider">
                <tr v-for="(board, idx) in boardList" :key="board.id">
                    <th scope="row">{{board.no}}</th>
                    <td>{{board.subject}}</td>
                    <td>{{board.userName}}</td>
                    <td>{{dateFormat(board.updateDate)}}</td>
                    <td>{{board.viewCnt}}</td>
                    <td>{{board.commentCnt}}</td>
                </tr>
            </tbody>
        </table>
        <div style="text-align: right">
            <button type="button" class="btn btn-primary" @click="onButtonClick">등록</button>
        </div>
    </div>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted, getCurrentInstance} from 'vue';
import { Board } from '@/views/board/BoardListSection.vue';
import { useRouter } from 'vue-router';
import {callGetApi} from '@/utils/ApiClient';
import {RouteUrl} from '@/router/index';
import { dateFormat } from '@/utils/CommonUtil';

export default defineComponent({
    name : 'BoardList',
    setup() {
        const route = useRouter();
        const {proxy} = getCurrentInstance() as any;
        const boardList = ref<Board[]>();


        onMounted(async () => {

            const res = await callGetApi('/board');

            if (res.result == 200) {
                boardList.value = res.data;
            } else {
                proxy.$alert("서버와의 통신 중 오류가 발생하였습니다.");
            }

        });

        const onButtonClick = () => {
            route.push(RouteUrl.BOARD_WRITE);
        }

        return {boardList, onButtonClick}
    }
})
</script>