<template>
	<nav class="navbar navbar-light pc-sub-nav">
		<div class="container-fluid page-sub-header">
			<ul>
				<li>
					<router-link :to="'/'" style="vertical-align: baseline;" class="menu-hover"><i class="bi bi-house-fill" style="font-size : 16px"></i></router-link>
				</li>
				<li v-for="m in menuList" :key="m.meta.id">
					<span class="sub-header-arrow">&gt;</span>
					<router-link class="menu-hover" :to="`${m.path}`">{{m.name}}</router-link>
				</li>
			</ul>
		</div>
	</nav>
	<nav class="navbar navbar-light mobile-sub-nav" v-if="currentPage.meta.parentId != 0" >
		<div class="container-fluid page-sub-header">
        <span class="navbar-brand back-icon menu-hover" @click="historyback"><i class="bi bi-chevron-left"></i></span>
		<div>
        	<a class="nav-font">{{title != '' ? title : currentPage.name}}</a>
		</div>
		</div>
	</nav>
</template>

<script lang="ts">
import {defineComponent, computed, ref, watch} from 'vue';
import {routes} from '@/router/index';
import {useRouter} from 'vue-router';

export default defineComponent({
	name : 'subHeader',
	props: ['currentPage', 'title'],
	setup(props) {
		const route = useRouter();
		const menuList = ref<Array<any>>([]);


		watch(props, (value : any) => {
			const {currentPage} = value;
			menuList.value = [];
			menuList.value.unshift(currentPage);
			if (currentPage.meta.parentId != 0) {
				getMenuList(currentPage);
			}
		})

		const getMenuList = (menu : any) => {
			const parentM = routes.find((m) => m.meta.id == menu.meta.parentId && m.meta.id !== menu.meta.id);
			if (parentM) {
				menuList.value.unshift(parentM);
				getMenuList(parentM);
			}
		}

		const historyback = () => {
			route.go(-1);
		}

		return {menuList, historyback}

	}
})

</script>

<style scoped>
	.navbar-font {
		font-size: 16px;
		font-weight: bold;
		color: #fff !important;
		text-decoration: none !important;
	}
</style>