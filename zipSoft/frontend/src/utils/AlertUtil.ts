import {createApp} from 'vue';
import AlertComponent from '@/components/modal/AlertComponent.vue';
import LoadingBar from '@/components/modal/LoadingBar.vue';

const Alert = {
    install : (app:any) => {
        const instance:any = createApp({extends: AlertComponent}).mount(document.createElement('div'));
        const loading:any = createApp({extends: LoadingBar}).mount(document.createElement('div'));
        app.config.globalProperties.$alert = (msg:string, callback? : Function, ele?:HTMLElement) => {
          document.body.appendChild(instance.$el);
          instance.type = 'alert'
          instance.msg = msg
          instance.isShow = true
          instance.ele = ele
          instance.callback = callback
          instance.instance = instance
        };

        app.config.globalProperties.$confirm = (msg : string, success? : Function, cancel? : Function) => {
          document.body.appendChild(instance.$el);
          instance.type = 'confirm';
          instance.msg = msg;
          instance.isShow = true;
          instance.instance = instance;
          if (typeof success !== 'undefined') {
            instance.callback = success;
          }
          if (typeof cancel !== 'undefined') {
            instance.cancel = cancel;
          }
        }

        app.config.globalProperties.$loadingBar = (isShow : boolean, percent : number) => {
          document.body.appendChild(loading.$el);
          loading.isShow = isShow;
          loading.percent = percent;
          loading.instance = loading
        };
    }
}

export default Alert;