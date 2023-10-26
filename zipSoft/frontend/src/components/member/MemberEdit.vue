<template>
    <form class="form-signin">
        <div class="mb-3">
            <div class="member-pic-div">
                <i class="bi bi-x-lg" v-if="isShow" @click="removeImg"></i>
                <div class="member-pic profile-div" @click="thumbnailOnClick">
                    <img src="" ref="thumb" @error="errorImg" />
                </div>
            </div>
            <input type="file" style="display: none" ref="fileRef" accept="image/*" @change="previewThumbnail">
        </div>
        <div class="mb-3">
            <label>아이디</label>
            <input class="form-control" />
        </div>
        <div class="mb-3">
            <label>닉네임</label>
            <input class="form-control" />
        </div>
        <div class="mb-3">
            <label>이메일</label>
            <input type="email" class="form-control" />
        </div>
  </form>
</template>
<script lang="ts" setup>
import {ref, getCurrentInstance} from 'vue';

const fileRef = ref<HTMLInputElement>();
const thumb = ref<HTMLImageElement>();
const isShow = ref(false);
const {proxy} = getCurrentInstance() as any;

const thumbnailOnClick = () => {
    fileRef.value?.click();
}

const previewThumbnail = (e : any) => {
    const input = e.target;

    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = (event : any) => {
            if (thumb.value) {
                thumb.value.style.width = '100%';
                thumb.value.src = event.target.result;
                isShow.value = true;
            }
        }

        reader.readAsDataURL(input.files[0]);
    } else {
        errorImg();
    }

} 

const errorImg = () => {
    if (thumb.value) {
        thumb.value.src = require('@/assets/img/user.png');
        thumb.value.style.width = '40px';
    }
    isShow.value = false;
}

const removeImg = () => {

    proxy.$confirm('정말 삭제하시겠습니까?', () => {
        errorImg();
        if (fileRef.value) fileRef.value.value = '';
    });
    return false;
}
</script>
