<template>
    <div :class="`${viewType}-menu-list`">
        <MenuRow v-for="menu in menuList" :key="menu.path" :menu="menu" :currentPage="currentPage" :viewType="viewType" />
    </div>
</template>
<script lang="ts">
import {defineComponent, ref, computed } from 'vue';
import {useRouter} from 'vue-router';
import {routes} from '@/router/index';
import MenuRow from './MenuRow.vue';

export default defineComponent({
    name : 'MenuList',
    components : {MenuRow},
    props: {
        viewType: {
            type: String,
            required: true,
        },
    },
    setup(props) {
        const menuList = ref(routes.filter((menu) => menu.meta.isShow));
        const {currentRoute} = useRouter(); 
        
        const currentPage = computed(() => { console.log(currentRoute.value); return currentRoute.value.path});
        return {menuList, currentPage};

    }
})
</script>