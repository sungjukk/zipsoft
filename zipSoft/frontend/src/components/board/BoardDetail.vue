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
            <div>
                <a href="javascript:;" @click="downloadFile(3)"><img style="vertical-align: text-top" src="@/assets/svg/download.svg" />&nbsp; 가나다라마바사.pdf</a>
            </div>
        </div>
        <div class="board-content">
            {{detail.content}}
        </div>
    </div>
    <div class="card">
  <div class="percent">
    <svg>
      <circle cx="105" cy="105" r="100"></circle>
      <circle cx="105" cy="105" r="100" style="--percent: 90"></circle>
    </svg>
    <div class="number">
      <h3>90<span>%</span></h3>
    </div>
  </div>
  <div class="title">
    <h2>JavaScript</h2>
  </div>
</div>   
</template>
<script lang="ts" setup>
import {BoardDetail, BoardFile} from '@/views/board/BoardDetailSection.vue';
import { useRouter, useRoute } from 'vue-router';
import {RouteUrl} from '@/router/index';
import {ref, onMounted, getCurrentInstance} from 'vue';
import {callGetApi, callFileDownApi} from '@/utils/ApiClient';

const {proxy} = getCurrentInstance() as any;
const router = useRouter();
const route = useRoute();
const tootip = ref<HTMLDivElement>();
const detail = ref<BoardDetail>({
    id : '0',
    subject: '',
    content : '',
    userName : '',
    updateDt : '',
    viewCnt : 0,
    fileList : []
});

onMounted(async () => {
    await callApiBoardDetail();
})

const callApiBoardDetail = async () => {

    const res = await callGetApi(`/board/${route.params.id}`, {});
    if (res.result == 200) {

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
        console.log('복사 완료');

        if (tootip && tootip.value) {
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

</script>