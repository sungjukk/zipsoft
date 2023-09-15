import Vue from 'vue';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

const Socket = {
    install : (app : any) => {
        let stompClient : any = null;
        app.config.globalProperties.$socket = {
            connect : async () => {
                return new Promise((resolve, reject) => {
                    const sock = new SockJS('http://localhost:8080/ws/chat');
                    stompClient = Stomp.over(sock);
                    console.log('socket 연결시도...');
                    const accessToken = sessionStorage.getItem('authorization');
                    const header = {
                        Authorization : `Bearer ${accessToken}`
                    };
                    stompClient.connect(header, (frame : any) => {
                        console.log('연결 성공');
                        resolve(true);   
                    }, (error : any) => {
                        console.log('연결실패');
                        reject(false);
                    });
                });
            },
            isConnected : () => {
                if (stompClient) return stompClient.connected
                else return false
            },
            subscribe: (url : String, fn : Function) => {
                console.log(stompClient);
                if (stompClient!=null) {
                    if (stompClient.connected) {
                        stompClient.subscribe(url, fn);
                    } else {
                        app.config.globalProperties.$socket.connect().then(() => stompClient.subscribe(url, fn));
                    }
                }
                    
            },
            send: (url : string, msg : string) => {
                if (stompClient!=null) stompClient.send(url, msg);
            }
        }
    }
}

export default Socket;
