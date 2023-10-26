import Vue from 'vue';
import {initializeApp, FirebaseApp} from 'firebase/app';
import { getMessaging, getToken, onMessage, Messaging } from 'firebase/messaging';

const FIRE_BASE_CONFIG = {
    
};

const FIRE_BASE_SERVER_KEY = '서버키';

const Push = {
    install : (app : any) => {
        let firebase : FirebaseApp;
        let messaging : Messaging;
        app.config.globalProperties.$push = {
            init : () => {
                firebase = initializeApp(FIRE_BASE_CONFIG);
                messaging = getMessaging(firebase);
            },
            getToken: (callback : Function) => {
                if (!messaging) return false;
                getToken(messaging, {vapidKey : FIRE_BASE_SERVER_KEY}).then((token : string) => {
                    console.log('token : ' + token);
                    if (callback) callback(token);
                });
            },
            onMessage: (callback :Function) => {
                if (!messaging) return false;
                onMessage(messaging, (payload) => {
                    console.log('Message received123213. ', payload);
                    if (callback) callback(payload);
                });
            }
        }
    }
}

export default Push;