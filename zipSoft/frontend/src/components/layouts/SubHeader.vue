<template>
	<nav class="navbar navbar-light" style="background-color: rgb(36 157 106 / 76%); position : fixed; width : 100%; top : 80px; color:#ededed">
		<div class="container-fluid page-header">
		</div>
	</nav>
</template>

<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
	name : 'subHeader'
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