import {createApp} from 'vue';
import AlertComponent from '@/components/modal/AlertComponent.vue';

export const Alert = {
    install : (app:any) => {
        const instance:any = createApp(AlertComponent).mount(document.createElement('div'));

        app.config.globalProperties.$alert = (msg:string) => {
            document.body.appendChild(instance.$el);
            instance.type = 'alert'
            instance.msg = msg
            instance.isShow = true
            instance.instance = instance
        };

        app.config.globalProperties.$confirm = (msg : string, success? : Function, cancel? : Function) => {
            
        }

    }
}