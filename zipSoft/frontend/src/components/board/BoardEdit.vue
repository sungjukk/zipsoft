<template>
    <div class="board-container">
        <BoardForm ref="frm"  />
        <div class="board-write-btn-group">
            <button class="btn btn-secondary" @click="moveBoardList">취소</button>
            <button class="btn btn-primary" style="margin-left : 10px" @click="submit">수정</button>
        </div>
    </div>
</template>
<script lang="ts">
import {defineComponent, ref, getCurrentInstance, onMounted} from 'vue';
import BoardForm from './BoardForm.vue';
import { useRouter, useRoute } from 'vue-router';
import {callGetApi, callFileApi, HTTP_STATUS} from '@/utils/ApiClient';
import {RouteUrl} from '@/router/index';

export default defineComponent({
    name: 'BoardEdit',
    components : {
        BoardForm
    },
    setup() {
        const frm = ref();
        const router = useRouter();
        const route = useRoute();
        const {proxy} = getCurrentInstance() as any;
        const callApiBoardDetail = async () => {

            const res = await callGetApi(`/board/${route.params.id}`, {});
            if (res.result == HTTP_STATUS.OK) {

                if (res.data) {
                    console.log(res.data);
                    frm.value.write.subject = res.data.subject;
                    frm.value.write.content = res.data.content;
                    frm.value.write.fileList = res.data.fileList;
                } else {
                    proxy.$alert("해당 글이 존재하지 않습니다.", () => {
                        router.push(RouteUrl.BOARD);
                    });
                    return false;
                }

            } else {
                proxy.$alert("서버와의 통신 중 오류가 발생하였습니다.");
                return false;
            }

        }

        const submit = async () => {
            if (!frm.value.validation()) return false;

            const {params : {id}} = route;
            const formData = frm.value.getFormData();
            formData.append('id', id);

            const res = await callFileApi(`/board/${id}`,formData);
            if (res.result == HTTP_STATUS.OK) {
                proxy.$alert("수정 되었습니다.", () => {
                    router.push(RouteUrl.BOARD_DETAIL.replace(":id",id + ''));
                });
            } else {
                proxy.$alert("서버와의 통신 중 오류가 발생하였습니다.");
                return false;
            }
        }

        onMounted(async () => {
            await callApiBoardDetail();
        })

        return {frm, submit}
    }
})

</script>