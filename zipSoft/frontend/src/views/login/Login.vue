<template>
	<div class="login-wrapper">
		<h2>Login</h2>
		<form method="post" action="서버의url" id="login-form">
			<input type="text" name="userName" placeholder="Email" v-model="userId">
			<input type="password" name="userPassword" placeholder="Password" v-model="password">
			<label for="remember-check">
				<input type="checkbox" id="remember-check">아이디 저장하기
			</label>
			<button type="button" @click="submit()" >로그인</button>
		</form>
	</div>
</template>

<script>
export default {
	data() {
		return {
			userId: '',
			password: '',
		}
	},
	methods: {
		submit() {
			console.log(this.$store.state.UserStore.id);
			
			const data = {
				userId: this.userId,
				password: this.password
			};
			
			this.$store.dispatch('UserStore/login',data);
			
		}
	}
}
</script>
