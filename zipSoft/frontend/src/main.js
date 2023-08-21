import './assets/common.css'

import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'



const app = createApp({
	extends: App,
	async beforeCreate() {
		//await this.$store.dispatch('UserStore/republicToken');
		//console.log('asdasd');
	}
});
app.use(store).use(router).mount('#app')
