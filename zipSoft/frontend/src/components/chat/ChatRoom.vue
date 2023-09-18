<template>
    <div class="chat-container">
        <div class="mesgs">
          <div class="msg_history">
            <div class="incoming_msg">
              <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
              <div class="received_msg">
                <div class="received_withd_msg">
                  <p>Test which is a new approach to have all
                    solutions</p>
                  <span class="time_date"> 11:01 AM    |    June 9</span></div>
              </div>
            </div>
            <div class="outgoing_msg">
              <div class="sent_msg">
                <p>Test which is a new approach to have all
                  solutions</p>
                <span class="time_date"> 11:01 AM    |    June 9</span> </div>
            </div>
            <div class="incoming_msg">
              <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
              <div class="received_msg">
                <div class="received_withd_msg">
                  <p>Test, which is a new approach to have</p>
                  <span class="time_date"> 11:01 AM    |    Yesterday</span></div>
              </div>
            </div>
            <div class="outgoing_msg">
              <div class="sent_msg">
                <p>Apollo University, Delhi, India Test</p>
                <span class="time_date"> 11:01 AM    |    Today</span> </div>
            </div>
            <div class="incoming_msg">
              <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
              <div class="received_msg">
                <div class="received_withd_msg">
                  <p>We work directly with our designers and suppliers,
                    and sell direct to you, which means quality, exclusive
                    products, at a price anyone can afford.</p>
                  <span class="time_date"> 11:01 AM    |    Today</span></div>
              </div>
            </div>
          </div>
          <div class="type_msg">
            <div class="input_msg_write">
              <input type="text" class="write_msg" placeholder="Type a message" v-model="sendMsg" />
              <button class="msg_send_btn" type="button" @click="onSendBtnClick"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
            </div>
          </div>
        </div>
    </div>
</template>
<script lang="ts">
import {defineComponent, onMounted, getCurrentInstance, ref, onUnmounted} from 'vue';
import { useRouter, useRoute } from 'vue-router';

export default defineComponent({
    name : 'ChatRoom',
    setup() {
        const {proxy} = getCurrentInstance() as any;
        const route = useRoute();
        const sendMsg = ref('');

        onMounted(() => {
            proxy.$socket.subscribe(`/topic/chat/${route.params.id}`, (res : any) => {
                console.log('받아옴');
            });
        });

        onUnmounted(() => {
            proxy.$socket.unsubscribe();
        })


        const onSendBtnClick = () => {
            const data = {
                id : `${route.params.id}`,
                message : sendMsg.value,
                type: "TALK"
            }

            proxy.$socket.send('/app/chat/send', JSON.stringify(data));
        };

        return {sendMsg, onSendBtnClick}
    }
})
</script>