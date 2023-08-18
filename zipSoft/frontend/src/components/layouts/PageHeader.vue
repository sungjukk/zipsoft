<template>
	<header>
		<h1><a href="#" class="logo"><img alt="Vue logo" src="../../assets/logo.png" width="80"></a></h1>
		<div class="menuWrap">
			<ul class="menu">
				<li><router-link :to="`${RouteUrl.MAIN}`">Home</router-link></li>
				<li><router-link :to="`${RouteUrl.LOGIN}`">로그인</router-link></li>
				<li><router-link :to="`${RouteUrl.ABOUT}`">About</router-link></li>
				<li><router-link :to="`${RouteUrl.BOARD}`">게시판</router-link></li>
			</ul>
		</div>
	</header>
</template>

<script>
import {RouteUrl} from '@/router/index';
	export default {
		data () {
			return {
				RouteUrl
			}
		}
	}
</script>

<style scoped>
</style>