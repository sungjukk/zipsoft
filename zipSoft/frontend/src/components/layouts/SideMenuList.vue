<template>
    <li class="nav-item">
        <router-link class="nav-link active" :to="`${menu?.path}`" >{{ menu?.name }}</router-link>
    </li>
</template>
<script lang="ts" setup>
import type { PropType } from 'vue'
import { MenuDef } from '@/router/index';
const {menu} = defineProps({
    menu : Object as PropType<MenuDef>
});
</script>