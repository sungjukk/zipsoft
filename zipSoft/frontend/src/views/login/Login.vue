<template>
	<div class="">
		<div class="login-wrapper">
			<h2>Login</h2>
			<form method="post" action="서버의url" id="login-form">
				<input type="text" name="userName" placeholder="Email" v-model="userId" data-require title="아이디">
				<input type="password" name="userPassword" placeholder="Password" v-model="password" data-require title="패스워드">
				<label for="remember-check">
					<input type="checkbox" id="remember-check">아이디 저장하기
				</label>
				<button type="button" @click="submit()" >로그인</button>
			</form>
		</div>
	</div>
</template>

<script lang="ts" setup>
import { ref, getCurrentInstance, onMounted } from 'vue';
import { useStore } from 'vuex';

import {validationCheck} from '@/utils/CommonUtil';

const userId = ref('');
const password = ref('');
const store = useStore();
const {proxy} = getCurrentInstance() as any;

onMounted(() => {
	console.log('onMounted');
});

const submit = async () => {

	if (!validationCheck('login-form')) return false;

	const data = {
		userId : userId.value,
		password : password.value
	};

	await store.dispatch('UserStore/login',data);
	//proxy.$alert('test');
	//console.log(store);
}
</script>
