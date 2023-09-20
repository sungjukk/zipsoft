<template>
    <div class="menu-box">
        <div class="menu-btn-div menu-hover" :class="menu.path == currentPage ? 'active' : ''" @click="handleOnclick">
            <i v-if="viewType != 'pc'" class="bi menu-icon" :class="menu.meta.mobileIcon"></i>
            <p class="menu-description">{{menu.name}}</p>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { PropType } from 'vue'
import { MenuDef } from '@/router/index';
import { useRouter } from 'vue-router';
const route = useRouter();
const {menu, currentPage} = defineProps({
    menu : Object as PropType<MenuDef>,
    currentPage: String,
    viewType: String
});

const handleOnclick = () => {
    route.push(menu?.path);
}

</script>