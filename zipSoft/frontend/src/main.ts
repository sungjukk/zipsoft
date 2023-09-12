import "bootstrap" // [bootstrap]
import "bootstrap/dist/css/bootstrap.min.css" // [bootstrap]
import './assets/common.css'

import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import Alert from './utils/AlertUtil';
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

console.log(app);

app.use(store).use(router).use(Alert).use(quillEditor).mount('#app');

const globals = app.config.globalProperties
export { globals };
