import "bootstrap" // [bootstrap]
import "bootstrap/dist/css/bootstrap.min.css" // [bootstrap]
import "bootstrap-icons/font/bootstrap-icons.css";
import './assets/common.css'

import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import Alert from './utils/AlertUtil';
import Socket from "./utils/Socket";
import Push from "./utils/Push";
import { quillEditor } from 'vue3-quill';

declare module '@vue/runtime-core' {
	interface ComponentCustomProperties {
		$alert:any,
		$confirm:any,
		$loadingBar:any,
		$push:any
	}
}

// 알림 수신을 위한 사용자 권한 요청
Notification.requestPermission()
  .then((permission) => {
    console.log('permission ', permission)
    if (permission !== 'granted') {
      alert('알림을 허용해주세요')
    }
  });


const app = createApp({
	extends: App,
	async beforeCreate() {
		if ('serviceWorker' in navigator) {
			navigator.serviceWorker.register('/sw.js').then(() => {
				console.log('Service worker Registred!!');
			})
			.catch(err => {
				console.log(err);
			});

			
		}
	}
});

app.use(store).use(router).use(Alert).use(Socket).use(Push).use(quillEditor).mount('#app');

const globals = app.config.globalProperties
export { globals };
