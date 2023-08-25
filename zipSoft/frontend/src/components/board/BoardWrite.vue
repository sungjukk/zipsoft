<template>
    <div id="write-form">
        <table class="table board-write">
            <tbody>
                <tr>
                    <th>제목</th>
                    <td><input type="email" class="form-control" id="inputEmail4" title="제목" data-require  v-model="write.subject"></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td><input type="file" class="form-control"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea class="form-control" v-model="write.content" data-require title="내용" /></td>
                </tr>
            </tbody>
        </table>
        <div class="board-write-btn-group">
            <button class="btn btn-secondary" @click="moveBoardList">목록</button>
            <button class="btn btn-primary" style="margin-left : 10px" @click="submit">등록</button>
        </div>
    </div>
</template>
<script lang="ts">
import { defineComponent, getCurrentInstance, ref} from 'vue'
import { BoardWriteDef } from '@/views/board/BoardWriteSection.vue';
import {validationCheck} from '@/utils/CommonUtil';
import {callPostApi} from '@/utils/ApiClient';
import { useRouter } from 'vue-router';
import {RouteUrl} from '@/router/index';

export default defineComponent({
    name: 'BoardWrite',
    setup(props,context) {
        const {proxy} = getCurrentInstance() as any;
        const route = useRouter();
        const write = ref<BoardWriteDef>({
            subject : '',
            content : ''
        });

        const submit = async () => {
            //proxy.$alert('test');
            if (!validationCheck('write-form')) return false;

            const res = await callPostApi('/board',write.value);

            if (res.result == 200) {
                proxy.$alert("등록하였습니다.", ()=> {
                    route.push(RouteUrl.BOARD);
                });
            } else {
                proxy.$alert("등록에 실패하였습니다.");
            }

            console.log(res);

        }

        const moveBoardList = () => {
            route.push(RouteUrl.BOARD);
        }

        return {write, submit, moveBoardList};
    },
})
</script>
<style>
.board-write th {
    text-align: center;
    vertical-align: middle;
}

.board-write textarea {
    height: 400px;
}

.board-write-btn-group {
    text-align: right;
}

</style>
