<template>
	<header>
		<nav class="navbar navbar-dark fixed-top page-nav" style="background-color : #249d6a">
			<div class="container-fluid page-header">
				<div>
					<!--<button class="navbar-toggler" type="button" @click="openSidebar()">
						<span class="navbar-toggler-icon"></span>
					</button>-->
					<a class="navbar-brand nav-font" href="#" >{{ title }}</a>
					<div class="pc-menu-list">
						<div class="menu-box">
							<div class="menu-btn-div">
								<a class="nav-font menu-hover">Friend</a>
							</div>
							<div class="menu-btn-div">
								<a class="nav-font menu-hover">Chat</a>
							</div>
							<div class="menu-btn-div">
								<a class="nav-font menu-hover">Board</a>
							</div>
						</div>
					</div>
				</div>

				<div>

					<div class="nav-font" v-if="$store.getters['UserStore/isLogin']">
						<span >{{$store.state.UserStore.name}}님 </span>
						<a href="javascript:;" class="nav-font"  @click="logout">로그아웃</a>
					</div>
					<router-link class="nav-font" :to="`${RouteUrl.LOGIN}`" v-else>로그인</router-link>
				</div>
			</div>
			<SideMenu ref="sideBar" :title="title"/>
		</nav>
	</header>
	<div class="menuBar">
		<div class="menu-list">
			<div class="menu-box">
				<div class="menu-btn-div menu-hover">
					<i class="bi bi-people-fill menu-icon"></i>
					<p class="menu-description">Friend</p>
				</div>
			</div>
			<div class="menu-box">
				<div class="menu-btn-div menu-hover">
					<i class="bi bi-chat-fill menu-icon"></i>
					<p class="menu-description">Chat</p>
				</div>
			</div>
			<div class="menu-box">
				<div class="menu-btn-div menu-hover">
					<i class="bi bi-card-list menu-icon"></i>
					<p class="menu-description">Board</p>
				</div>
			</div>
		</div>
	</div>
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