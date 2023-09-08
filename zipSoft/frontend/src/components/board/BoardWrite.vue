<template>
    <div class="board-container">
        <BoardForm ref="frm"  />
        <div class="board-write-btn-group">
            <button class="btn btn-secondary" @click="moveBoardList">목록</button>
            <button class="btn btn-primary" style="margin-left : 10px" @click="submit">등록</button>
        </div>
    </div>
</template>
<script lang="ts">
import { defineComponent, getCurrentInstance, ref} from 'vue'
import {validationCheck} from '@/utils/CommonUtil';
import {callFileApi, HTTP_STATUS} from '@/utils/ApiClient';
import { useRouter } from 'vue-router';
import {RouteUrl} from '@/router/index';
import BoardForm from './BoardForm.vue';

export default defineComponent({
    name: 'BoardWrite',
    components: {
        BoardForm
    },
    setup() {
        const {proxy} = getCurrentInstance() as any;
        const route = useRouter();
        const frm = ref();

        const submit = async () => {
            if (!frm.value.validation()) return false;
            
            const res = await callFileApi('/board',frm.value.getFormData());

            if (res.result == HTTP_STATUS.OK) {
                proxy.$alert("등록하였습니다.", ()=> {
                    route.push(RouteUrl.BOARD);
                });
            } else {
                proxy.$alert("등록에 실패하였습니다.");
            }

        }

        const moveBoardList = () => {
            route.push(RouteUrl.BOARD);
        }

        return {frm, submit, moveBoardList};
    },
})
</script>