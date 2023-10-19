<template>
    <div class="popup-profile">
        <div style="height : 65%">

        </div>
        <div class="popup-profile-info">
            <img src="@/assets/img/empty-user.png" />
            <p>{{user.userName}}</p>
        </div>
        <div class="popup-profile-footer">
            <ul>
                <li>
                    <div class="popup-profile-menu" @click="chatBtnOnClick">
                        <i class="bi bi-chat-fill"></i>
                        <a href="javascript:;">채팅하기</a>
                    </div>
                </li>
                <li v-if="!user.friendReq">
                    <div class="popup-profile-menu" @click="friendAddBtnOnClick">
                        <i class="bi bi-person-fill-add"></i>
                        <a href="javascript:;">친구추가</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
<script lang="ts">
export interface PROFILE {
    id: number,
    userId : string,
    userName : string,
    email: string,
    comment : string,
    friendReq : boolean
}

import {defineComponent, onMounted, ref, getCurrentInstance} from 'vue';
import {callGetApi, callPostApi, HTTP_STATUS} from '@/utils/ApiClient';

export default defineComponent({
    name : 'Profile',
    props: {
      profileId : {type: Number}
    },
    setup(props) {
        const {proxy} = getCurrentInstance() as any;
        const user = ref<PROFILE>({
            id: 0,
            userId : '',
            userName : '',
            email: '',
            comment : '',
            friendReq : false
        });
        const callApiUserProfile = async () => {
            const res = await callGetApi(`/friend/${props.profileId}`);
            if (res.result === HTTP_STATUS.OK) {
                const {data} = res;

                if (!data) proxy.$alert('해당 사용자가 존재하지 않습니다.', () => {
                    const ele: any = document.getElementsByClassName('popup-close-btn')[0];
                    ele.click();
                });

                user.value = {
                    ...user.value,
                    ...data
                }
            }
        }

        const chatBtnOnClick = async () => {
            const data = {
                memberList : [{
                    userId : user.value.id
                }]
            };
            const res = await callPostApi(`/chat/add`, data);
            console.log(res);
        }

        const friendAddBtnOnClick = async () => {
            const res = await callPostApi(`/friend/add/${user.value.id}`);

            if (res.result === HTTP_STATUS.OK) {
                user.value = {
                    ...user.value,
                    friendReq : true
                };
                proxy.$alert('친구 신청하였습니다.');
                return false;
            } else {
                proxy.$alert('친구 신청 중 오류가 발생하였습니다.');
                return false;
            }

        }

        onMounted(async () => {
            await callApiUserProfile();
        })

        return {user, friendAddBtnOnClick, chatBtnOnClick}
    }
})
</script>