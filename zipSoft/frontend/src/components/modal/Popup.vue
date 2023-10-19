<template>
    <Transition name="slide">
        <div v-if="isShow" class="popup-content" :style="{zIndex}">
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
import {defineComponent, watch, ref} from 'vue';

export default defineComponent({
    name : 'Popup',
    props : {
        isShow : {type:Boolean, required : true}
    },
    emits: ['update:isShow'],
    setup(props, ctx) {
        const modalEle = ref<HTMLDivElement>();
        const zIndex = ref('9000');

        watch(props, (value : any) => {
            const {isShow} = value;

            if (isShow) {
                const elements = document.getElementsByClassName('popup-content');
                for (let i = 0; i < elements.length; i++) {
                    const ele = elements[i] as HTMLElement;
                    const eleIndex = Number(document.defaultView?.getComputedStyle(ele).getPropertyValue('z-index'));
                    if (eleIndex >= Number(zIndex.value)) zIndex.value = `${(eleIndex + 1)}`;
                }

            }

        })

        const modalCloseBtnOnClick = () => {
            ctx.emit('update:isShow', false);
        }

        return {modalCloseBtnOnClick, modalEle, zIndex}
    } 
})
</script>