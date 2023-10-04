<template>
    <div class="popup-profile">
        <div style="height : 65%">

        </div>
        <div class="popup-profile-info">
            <img src="@/assets/img/empty-user.png" />
            <p>테스터</p>
        </div>
        <div class="popup-profile-footer">
            <ul>
                <li>
                    <div class="popup-profile-menu">
                        <i class="bi bi-chat-fill"></i>
                        <a href="javascript:;">채팅하기</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
    name : 'Profile',
    setup() {

    }
})
</script>