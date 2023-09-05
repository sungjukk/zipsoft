<template>
    <div class="board-container">
        <div class="container-header">
            <h5>총 : {{pageParam.totalElements}}건</h5>
        </div>
        <div>
            <table class="table table-hover">
                <colgroup>
                    <col width="50px"  />
                    <col width="300px" />
                    <col width="100px" />
                    <col width="200px" />
                    <col width="70px"  />
                    <col width="70px"  />
                </colgroup>
                <thead class="table-light">
                    <tr>
                        <th scope="col">No</th>
                        <th scope="col" class="ta-left">제목</th>
                        <th scope="col">작성자</th>
                        <th scope="col">날짜</th>
                        <th scope="col">조회수</th>
                        <th scope="col">댓글수</th>
                    </tr>
                </thead>
                <tbody class="table-group-divider">
                    <tr v-for="(board, idx) in boardList" :key="board.id">
                        <td scope="row">{{no - idx}}</td>
                        <td class="ta-left"><a href="javascript:;" @click="onDetailClick(board.id)">{{board.subject}} <img v-if="board.fileCnt > 0" src="@/assets/svg/files.svg" /></a></td>
                        <td>{{board.userName}}</td>
                        <td>{{board.updateDt}}</td>
                        <td>{{board.viewCnt}}</td>
                        <td>0</td>
                    </tr>
                </tbody>
            </table>
            <div style="text-align: right">
                <button type="button" class="btn btn-primary" @click="onButtonClick">등록</button>
            </div>
        </div>
    </div>
    <div>
        <Pagination :pageParam="pageParam" @callback="callApiBoardList" />
    </div>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted, getCurrentInstance} from 'vue';
import { Board } from '@/views/board/BoardListSection.vue';
import { useRouter } from 'vue-router';
import {callGetApi, HTTP_STATUS} from '@/utils/ApiClient';
import {RouteUrl} from '@/router/index';
import Pagination from '@/components/modal/Pagination.vue';


export interface PageParam {
    number: number,
    totalElements: number,
    totalPages: number
}

export default defineComponent({
    name : 'BoardList',
    components: {
        Pagination
    },
    setup() {
        const route = useRouter();
        const {proxy} = getCurrentInstance() as any;
        const boardList = ref<Board[]>();
        const no = ref(0);
        const pageParam = ref<PageParam>({
            number : 0,
            totalElements : 0,
            totalPages : 0
        });


        const callApiBoardList = async (page : number) => {
            const res = await callGetApi('/board',{page});

            if (res && res.result == HTTP_STATUS.OK) {
                boardList.value = res.data.content;
                const {number, totalElements, totalPages, size} = res.data;
                pageParam.value = {
                    number,
                    totalElements,
                    totalPages
                };
                no.value = totalElements - (number * size);
            } else {
                proxy.$alert("서버와의 통신 중 오류가 발생하였습니다.");
            }
        }


        onMounted(async () => {
            await callApiBoardList(1);
        });

        const onButtonClick = () => {
            route.push(RouteUrl.BOARD_WRITE);
        };

        const onDetailClick = (id : string) => {
            route.push(RouteUrl.BOARD_DETAIL.replace(":id",id));
        }

        return {boardList, pageParam, onButtonClick, callApiBoardList, no, onDetailClick}
    }
})
</script>