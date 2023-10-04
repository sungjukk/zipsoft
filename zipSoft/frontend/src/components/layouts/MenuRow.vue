<template>
    <div class="menu-box">
        <div class="menu-btn-div menu-hover" :class="isActive ? 'active' : ''" @click="handleOnclick">
            <i v-if="viewType != 'pc'" class="bi menu-icon" :class="menu.meta.mobileIcon"></i>
            <p class="menu-description">{{menu.name}}</p>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { PropType, watch, ref, onMounted } from 'vue'
import { MenuDef } from '@/router/index';
import { useRouter } from 'vue-router';
import {routes} from '@/router/index';
const route = useRouter();
const props = defineProps({
    menu : Object as PropType<MenuDef>,
    currentPage: Object,
    viewType: String
});

const isActive = ref(false);

onMounted(() => {
    init();
})

watch(props, (value : any) => {
    init();
})

const init = () => {
    const {currentPage, menu} = props;
    if (!currentPage) return false;
    if (!menu) return false;

    if (currentPage.path === menu.path) {
        isActive.value = true;
    } else if (currentPage.meta.parentId != 0) {
        isActive.value = isActiveCheck(currentPage);
    } else {
        isActive.value = false;
    }
}

const isActiveCheck = (current : any) => {
    const {menu} = props;
    if (!menu) return false;
    if (current.path === menu.path) return true;

    const parentM = routes.find((m) => m.meta.id == current.meta.parentId && m.meta.id !== current.meta.id);
    if (!parentM) return false;

    if (parentM.path === menu.path) return true;
    else return isActiveCheck(parentM);

}

const handleOnclick = () => {
    route.push(props.menu.path);
}

</script>