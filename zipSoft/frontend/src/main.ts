import "bootstrap" // [bootstrap]
import "bootstrap/dist/css/bootstrap.min.css" // [bootstrap]
import './assets/common.css'

import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import Alert from './utils/AlertUtil';


const app = createApp({
	extends: App,
	async beforeCreate() {
		//await this.$store.dispatch('UserStore/republicToken');
		//console.log('asdasd');
	}
});

console.log(app);

app.use(store).use(router).use(Alert).mount('#app');

const globals = app.config.globalProperties
export { globals };
