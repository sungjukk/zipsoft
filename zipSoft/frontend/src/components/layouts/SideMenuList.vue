<template>
    <li class="nav-item">
        <a class="nav-link asd" :class="menu.path == currentPage ? 'active' : ''" :to="`${menu?.path}`" href="javascript:;" @click="onClick(menu.path)" >{{ menu?.name }}</a>
    </li>
</template>
<script lang="ts" setup>
import { PropType, defineEmits } from 'vue'
import { MenuDef } from '@/router/index';
import { useRouter } from 'vue-router';
const route = useRouter();
const {menu, currentPage} = defineProps({
    menu : Object as PropType<MenuDef>,
    currentPage: String
});
const emit = defineEmits([
  'closeSidebar'
]);

const onClick = (path : string) => {
    route.push({path});
    emit('closeSidebar');
}

</script>