<template>
	<header>
		<nav class="navbar navbar-dark fixed-top page-nav" style="background-color : #249d6a">
			<div class="container-fluid page-header">
				<div>
					<!--<button class="navbar-toggler" type="button" @click="openSidebar()">
						<span class="navbar-toggler-icon"></span>
					</button>-->
					<router-link class="navbar-brand nav-font menu-hover" :to="`${RouteUrl.MAIN}`" >{{ title }}</router-link>
					<MenuList :viewType="'pc'" :currentPage="currentPage" />
				</div>
				<div>

					<div class="nav-font" v-if="$store.getters['UserStore/isLogin']" style="position : relative">
						<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						<span >{{$store.state.UserStore.name}}님 </span>
						</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="#">Action</a></li>
							<li><a class="dropdown-item" href="#">Another action</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="#" @click="logout">로그아웃</a></li>
						</ul>
					</div>
					<router-link class="nav-font" :to="`${RouteUrl.LOGIN}`" v-else>로그인</router-link>
				</div>
			</div>
		</nav>
		<SubHeader :currentPage="currentPage" :title="$store.state.MenuStore.title"/>
	</header>
	<div class="menuBar" v-if="$store.state.MenuStore.isShow">
		<MenuList :viewType="'mobile'" :currentPage="currentPage"  />
	</div>
</template>

<script lang="ts">
import {defineComponent, ref, computed} from 'vue';
import {useRouter} from 'vue-router';
import { useStore } from 'vuex';

import {RouteUrl} from '@/router/index';
import MenuList from './MenuList.vue';
import SubHeader from './SubHeader.vue';

export default defineComponent({
	name : 'PageHeader',
	components: {
		MenuList, SubHeader
	},
	setup() {
		const sideBar = ref<any>();
		const title = ref(process.env.VUE_APP_TITLE);
		const store = useStore();
		const {currentRoute} = useRouter(); 
		const currentPage = computed(() => {console.log(currentRoute.value); return currentRoute.value});

		const openSidebar = () => {
			sideBar.value.openSidebar();
		}

		const logout = () => {
			//console.log('test');
			store.dispatch('UserStore/logout');
		}

		return {
			sideBar, RouteUrl, title, openSidebar, logout, currentPage
		}
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