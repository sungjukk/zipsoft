import {createApp} from 'vue';
import AlertComponent from '@/components/modal/AlertComponent.vue';

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $alert:any
  }
}

const Alert = {
    install : (app:any) => {
        const instance:any = createApp({extends: AlertComponent}).mount(document.createElement('div'));
        app.config.globalProperties.$alert = (msg:string) => {
            document.body.appendChild(instance.$el);
            instance.type = 'alert'
            instance.msg = msg
            instance.isShow = true
            instance.instance = instance
        };

        app.config.globalProperties.$confirm = (msg : string, success? : Function, cancel? : Function) => {
            document.body.appendChild(instance.$el);
		    instance.type = 'confirm';
		    instance.msg = msg;
		    instance.isShow = true;
		    instance.instance = instance;
		    if (typeof success !== 'undefined') {
		      instance.success = success;
		    }
		    if (typeof cancel !== 'undefined') {
		      instance.cancel = cancel;
		    }
        }

    }
}

export default Alert;