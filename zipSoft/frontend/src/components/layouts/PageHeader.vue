<template>
	<header>
		<nav class="navbar navbar-dark bg-dark fixed-top">
			<div class="container-fluid">
				<div>
					<button class="navbar-toggler" type="button" @click="openSidebar()">
						<span class="navbar-toggler-icon"></span>
					</button>
					<a class="navbar-brand" href="#" style="margin-left: 20px;">{{ title }}</a>
				</div>
				<div>
					<div class="navbar-font" v-if="$store.getters['UserStore/isLogin']">
						<span >{{$store.state.UserStore.name}}님 </span>
						<a href="javascript:;"  @click="logout">로그아웃</a>
					</div>
					<router-link class="navbar-font" :to="`${RouteUrl.LOGIN}`" v-else>로그인</router-link>
				</div>
			</div>
			<SideMenu ref="sideBar" :title="title"/>
		</nav>
	</header>
	<!--<header>
		<h1><a href="#" class="logo"><img alt="Vue logo" src="../../assets/logo.png" width="80"></a></h1>
		<div class="menuWrap">
			<ul class="menu">
				<li><router-link :to="`${RouteUrl.MAIN}`">Home</router-link></li>
				<li>
					<a href="javascript:;" @click="logout" v-if="$store.getters['UserStore/isLogin']">로그아웃</a>
					<router-link :to="`${RouteUrl.LOGIN}`" v-else>로그인</router-link>
				</li>
				<li><router-link :to="`${RouteUrl.ABOUT}`">About</router-link></li>
				<li><router-link :to="`${RouteUrl.BOARD}`">게시판</router-link></li>
			</ul>
		</div>
	</header>-->
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import { useStore } from 'vuex';

import {RouteUrl} from '@/router/index';
import SideMenu from './SideMenu.vue';

export default defineComponent({
	name : 'PageHeader',
	components: {
		SideMenu
	},
	setup() {
		const sideBar = ref<any>();
		const title = ref(process.env.VUE_APP_TITLE);

		const store = useStore();

		const openSidebar = () => {
			sideBar.value.openSidebar();
		}

		const logout = () => {
			//console.log('test');
			store.dispatch('UserStore/logout');
		}

		return {
			sideBar, RouteUrl, title, openSidebar, logout
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