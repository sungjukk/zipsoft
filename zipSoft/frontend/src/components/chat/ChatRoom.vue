<template>
    <nav class="navbar navbar-light" style="background-color: #e3f2fd; position : fixed; width : 100%; top : 0px">
      <div style="width: 100%; text-align : center">
        <span class="navbar-brand mb-0 h1 chat-back-icon"><i class="bi bi-chevron-left"></i></span>
        <span class="navbar-brand mb-0 h1">테스터2</span>
      </div>
    </nav>
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
        </div>
    </div>
    <div class="type_msg">
      <div class="input_msg_write">
        <textarea ref="chatInput" class="write_msg" v-model="sendMsg" rows="1" @input="handleChange" />
        <button class="msg_send_btn" type="button" @click="onSendBtnClick"><i class="bi bi-send"></i></button>
      </div>
    </div>
</template>
<script lang="ts">
import {defineComponent, onMounted, getCurrentInstance, ref, onUnmounted} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';

export default defineComponent({
    name : 'ChatRoom',
    setup() {
        const {proxy} = getCurrentInstance() as any;
        const route = useRoute();
        const sendMsg = ref('');
        const chatInput = ref();
        const store = useStore();
        const chatId = ref(route.params.id);

        onMounted(() => {
            proxy.$socket.subscribe(`/topic/chat/${route.params.id}`, (res : any) => {
                console.log('받아옴');
            });
        });

        onUnmounted(() => {
            proxy.$socket.unsubscribe(`/topic/chat/${chatId.value}`);
        })


        const onSendBtnClick = () => {
            const data = {
                id : `${route.params.id}`,
                message : sendMsg.value,
                type: "TALK",
                userName: store.state.UserStore.name
            }

            sendMsg.value = '';
            chatInput.value.style.height = '40px';

            proxy.$socket.send('/app/chat/send', JSON.stringify(data));
        };

        const handleChange = () => {
          chatInput.value.style.height = '0px';
          const scrollHeight = chatInput.value.scrollHeight >= 108 ? 108 : chatInput.value.scrollHeight;
          chatInput.value.style.height = `${scrollHeight}px`;
        }

        return {sendMsg, onSendBtnClick, chatInput, handleChange}
    }
})
</script>