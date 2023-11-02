importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js');

firebase.initializeApp({
});

const messaging = firebase.messaging();


messaging.onBackgroundMessage((payload) => {
    console.log(
        '[firebase-messaging-sw.js] Received background message ',
        payload
    );

    const {data} = payload;
    const notificationTitle = data.userName;
    const notificationOptions = {
        body: data.message,
        icon: '/icon/144.png'
    };

    self.registration.showNotification(notificationTitle, notificationOptions);
});