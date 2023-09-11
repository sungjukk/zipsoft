<template>
    <div class="board-container">
        <div class="board-title">
            <span>{{detail.subject}}</span>
        </div>
        <div class="board-info">
            <div class="board-info-list">
                <div>{{detail.userName}}</div>
                <div>{{detail.updateDt}}</div>
                <div><img style="vertical-align: text-top" src="@/assets/svg/eye.svg" /> {{detail.viewCnt}}</div>
            </div>
            <div class="board-clipboard">
                <div @click="clipboard"><img style="vertical-align: text-top" src="@/assets/svg/link-45deg.svg" /> URL 복사</div>
                <div ref="tootip" class="prtooltip">복사가 완료되었습니다.</div>
            </div>
        </div>
        <div class="board-file" v-if="detail.fileList && detail.fileList.length > 0">
            <div>
                <span>첨부파일</span>
            </div>
            <div class="board-file-list" >
                <div v-for="(file) in detail.fileList" :key="file.id">
                    <a href="javascript:;" @click="downloadFile(file.id)">
                        {{file.orgFileName}}
                    </a>
                </div>
            </div>
        </div>
        <div class="board-content" v-html="detail.content"></div>
    </div>
    <div v-if="$store.state.UserStore.id == detail.regId" class="board-btn-group mt-3">
        <button type="button" class="btn btn-secondary" style="font-size : 13px; padding : 10px 30px">삭제</button>
        <button type="button" class="btn btn-primary" style="font-size : 13px; padding : 10px 30px" @click="onEditBtnClick" >수정</button>
    </div>
    <div class="comment-container">
        <CommentList :boardId="detail.id" />
    </div>
</template>
<script lang="ts" setup>
import {BoardDetail} from '@/views/board/BoardDetailSection.vue';
import { useRouter, useRoute } from 'vue-router';
import {RouteUrl} from '@/router/index';
import {ref, onMounted, getCurrentInstance} from 'vue';
import {callGetApi, callFileDownApi , HTTP_STATUS} from '@/utils/ApiClient';

import CommentList from '@/components/comment/CommentList.vue';

const {proxy} = getCurrentInstance() as any;
const router = useRouter();
const route = useRoute();
const tootip = ref<HTMLDivElement>();
const detail = ref<BoardDetail>({
    id : 0,
    subject: '',
    content : '',
    userName : '',
    updateDt : '',
    viewCnt : 0,
    regId : 0,
    fileList : []
});

onMounted(async () => {
    console.log('onMounted');
    await callApiBoardDetail();
})

const callApiBoardDetail = async () => {

    const res = await callGetApi(`/board/${route.params.id}`, {});
    if (res.result == HTTP_STATUS.OK) {

        if (res.data) {
            detail.value = res.data;
            console.log(detail.value);
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


const clipboard = () => {
    window.navigator.clipboard.writeText(window.location.href).then(() => {

        if (tootip.value) {
            tootip.value.style.display = 'block';
            setTimeout((value: HTMLDivElement) => {
                value.style.opacity = '1';
            },100, tootip.value);


            setTimeout((value: HTMLDivElement) => {
                value.style.opacity = '0';
                setTimeout(() => value.style.display = 'none', 300);
            },1000, tootip.value)
        }
    }) 
}

const downloadFile = async (id : number) => {
    await callFileDownApi(`/board/file/${id}`);
}

const onEditBtnClick = () => {
    router.push(RouteUrl.BOARD_EDIT.replace(':id',detail.value.id + ''));
}

</script>