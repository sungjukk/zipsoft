<template>
    <Transition name="slide">
        <div v-if="isShow" class="popup-content" ref="modalEle">
            <div class="popup-close-btn" @click="modalCloseBtnOnClick">
                <i class="bi bi-x-lg"></i>
            </div>
            <div class="popup-body">
                <slot />
            </div>
        </div>
    </Transition>
    <div class="popup-background" v-if="isShow"></div>
</template>
<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
    name : 'Popup',
    props : {
        isShow : {type:Boolean, required : true}
    },
    emits: ['update:isShow'],
    setup(props, ctx) {

        const modalCloseBtnOnClick = () => {
            ctx.emit('update:isShow', false);
        }

        return {modalCloseBtnOnClick}
    } 
})
</script>