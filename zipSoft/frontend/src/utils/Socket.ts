import Vue from 'vue';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

enum SOCKET_STEP {
    INIT = 'INIT',
    LOADING = 'LOADING',
    SUCCESS = 'SUCCESS',
    FAIL = 'FAIL'
}

const RECONNECT_CNT = 5;

const Socket = {
    install : (app : any) => {
        let stompClient : any = null;
        let step = SOCKET_STEP.INIT;
        app.config.globalProperties.$socket = {
            connect : async (cnt : number) => {
                return new Promise((resolve, reject) => {
                    step = SOCKET_STEP.LOADING;
                    const sock = new SockJS('http://localhost:8080/ws/chat123');
                    stompClient = Stomp.over(sock);
                    console.log('socket 연결시도...');
                    const accessToken = sessionStorage.getItem('authorization');
                    const header = {
                        Authorization : `Bearer ${accessToken}`
                    };
                    stompClient.connect(header, (frame : any) => {
                        console.log('연결 성공');
                        step = SOCKET_STEP.SUCCESS;
                        resolve(true);   
                    }, (error : any) => {
                        console.log('연결실패');
                        step = SOCKET_STEP.FAIL;

                        if (cnt >= RECONNECT_CNT) {
                            reject(false);
                        } else {
                            console.log(cnt);
                            if (!cnt) cnt = 0;

                            app.config.globalProperties.$socket.connect(++cnt).then(() => resolve(true)).catch(() => reject(false));
                        }
                    });
                });
            },
            isConnected : () => {
                if (stompClient) return stompClient.connected
                else return false
            },
            subscribe: (url : String, fn : Function) => {
                
                if (step === SOCKET_STEP.LOADING) {
                    setTimeout(() => {
                        app.config.globalProperties.$socket.subscribe();
                    }, 500);
                } else if (step === SOCKET_STEP.SUCCESS) {
                    stompClient.subscribe(url, fn, {id : url});
                } else if (step === SOCKET_STEP.FAIL) {
                    alert('연결에 실패');
                }
                    
            },
            send: (url : string, msg : string) => {
                const accessToken = sessionStorage.getItem('authorization');
                const header = {
                    Authorization : `Bearer ${accessToken}`
                };
                if (stompClient!=null) stompClient.send(url, msg, header);
            },
            unsubscribe: () => {
                if (stompClient!=null) {
                    stompClient.unsubscribe('/topic/chat/asd');
                }
            }
        }
    }
}

export default Socket;
