<template>
    <div ref="backRef" class="profile-div" :class="props.className">
        <img ref="imgRef" style="display:none" @error="errorImg" />
    </div>
</template>
<script lang="ts" setup>
import {ref, onMounted, watch} from 'vue';
const props = defineProps({
    id : Number,
    size : Number,
    className : String,
    base64: String
});

const backRef = ref<HTMLDivElement>();
const imgRef = ref<HTMLImageElement>();
const API_URL = ref(process.env.VUE_APP_API_URL);
const backgroundW = ref(0);
const errImgW = ref(0);

onMounted(() => {
    if (backRef.value && imgRef.value) {
        backgroundW.value = props.size ? props.size : backRef.value.offsetWidth;
        errImgW.value = backgroundW.value / 3;
        backRef.value.style.backgroundImage = `url(${API_URL.value}/member/thumbnail/${props.id})`;
        imgRef.value.src = `${API_URL.value}/member/thumbnail/${props.id}`;
        if (props.size) {
            backRef.value.style.width = `${props.size}px`;
            backRef.value.style.height = `${props.size}px`;
        }
    }
});

watch(() => props.id, (newVal : Number) => {
    const src = `${API_URL.value}/member/thumbnail/${newVal}`;
    console.log(src);
    reset(src);
});

watch(() => props.base64, (newVal : String) => {
    reset(newVal);
});

const reset = (src : string) => {
    if (backRef.value && imgRef.value) {
        backRef.value.style.backgroundImage = `url(${src})`;
        imgRef.value.src = src;
        imgRef.value.style.width = `${backgroundW.value}px`; 
        imgRef.value.style.display = 'none';
    }
}

const errorImg = (e: any) => {
    e.target.src = require('@/assets/img/user.png');

    if (backRef.value) {
        e.target.style.width = `${errImgW.value}px`; 
        e.target.style.display = 'block';
    }

}

</script>