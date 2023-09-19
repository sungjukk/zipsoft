import "bootstrap" // [bootstrap]
import "bootstrap/dist/css/bootstrap.min.css" // [bootstrap]
import "bootstrap-icons/font/bootstrap-icons.css";
import './assets/common.css'

import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import Alert from './utils/AlertUtil';
import Socket from "./utils/Socket"
import { quillEditor } from 'vue3-quill';


const app = createApp({
	extends: App,
	async beforeCreate() {
		if ('serviceWorker' in navigator) {
			navigator.serviceWorker.register('/sw.js').then(() => {
				console.log('Service worker Registred!!');
			})
			.catch(err => {
				console.log(err);
			})
		}
	}
});

app.use(store).use(router).use(Alert).use(Socket).use(quillEditor).mount('#app');

const globals = app.config.globalProperties
export { globals };
