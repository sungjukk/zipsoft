<template>
    <form class="form-signin" id="form-signin">
        <div class="mb-5">
            <div class="member-pic-div">
                <i class="bi bi-x-lg" v-if="isShow" @click="removeImg"></i>
                <div @click="thumbnailOnClick">
                    <Thumbnail :id="member.thumbId <= 0 ? 0 : member.id" :size="120" :base64="fileImage" />
                </div>
            </div>
            <input type="file" style="display: none" ref="fileRef" accept="image/*" @change="previewThumbnail">
        </div>
        <div class="mb-3">
            <h6 class="mber-title">회원 정보</h6>
        </div>
        <div class="mb-3">
            <label>아이디</label>
            <input class="form-control" v-model="member.userId" disabled />
        </div>
        <div class="mb-3">
            <label>닉네임</label>
            <input class="form-control" name="userName" v-model="member.userName" data-require title="닉네임" />
        </div>
        <div class="mb-3">
            <label>이메일</label>
            <input type="email" class="form-control" name="email" v-model="member.email" data-require title="이메일" />
        </div>
        <div class="edit-btn-group">
            <button type="button" class="btn edit-btn" @click="editBtnOnClick">수정</button>
        </div>
  </form>
</template>
<script lang="ts" setup>
import {ref, getCurrentInstance, onMounted} from 'vue';
import {callGetApi, callFileApi, HTTP_STATUS} from '@/utils/ApiClient';
import {MEMBER} from '@/views/member/MemberEditSection.vue';
import {validationCheck} from '@/utils/CommonUtil';
import { useStore } from 'vuex';
import Thumbnail from '@/components/layouts/Thumbnail.vue';

const member = ref<MEMBER>({
    id : 0,
    userId : '',
    userName : '',
    email : '',
    thumbId : 0
});
const fileRef = ref<HTMLInputElement>();
const isShow = ref(false);
const store = useStore();
const {proxy} = getCurrentInstance() as any;
const fileImage = ref('');

const callApiMemberDetail = async () => {

    const res = await callGetApi('/member/edit');

    if (res.result === HTTP_STATUS.OK) {
        const {data} = res;

        member.value = data;
        if (member.value.thumbId > 0) isShow.value = true;

    } else {
        proxy.$alert("서버와 통신 중 오류가 발생하였습니다.");
        return false;
    }

};

onMounted(async () => {
    await callApiMemberDetail();
});

const editBtnOnClick = async () => {
    if (!validationCheck('form-signin')) return false;

    const res = await callApiMemberEdit();
    console.log(res);
    if (res.result === HTTP_STATUS.OK) {
        await store.dispatch('UserStore/updateTokenInfo');
        proxy.$alert('수정이 완료되었습니다.');
        return false;
    }
}

const callApiMemberEdit = async () => {

    const params = getFormData();

    return await callFileApi('/member/edit', params);

}

const thumbnailOnClick = () => {
    fileRef.value?.click();
}

const previewThumbnail = (e : any) => {
    const input = e.target;

    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = (event : any) => {
            fileImage.value = event.target.result;
            isShow.value = true;
        }

        reader.readAsDataURL(input.files[0]);
    } else {
        resetImage();
    }

} 

const resetImage = () => {
    isShow.value = false;
    member.value.thumbId = 0;
    fileImage.value = '';
    if (fileRef.value) fileRef.value.value = '';
}

const removeImg = () => {

    proxy.$confirm('정말 삭제하시겠습니까?', () => {
        resetImage();
    });
    return false;
}

const getFormData = () => {
    const formData = new FormData();
    formData.append('userName',member.value.userName);
    formData.append('email', member.value.email);
    formData.append('thumbId', member.value.thumbId);

    if (fileRef.value?.files && fileRef.value.files.length > 0) formData.append("file",fileRef.value.files[0]);

    return formData;
}

</script>
