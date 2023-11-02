<template>
    <div class="popup-profile">
        <div style="height : 65%">

        </div>
        <div class="popup-profile-info">
            <div class="member-pic-div">
                <i class="bi bi-x-lg" ></i>
                <div @click="thumbnailOnClick">
                    <Thumbnail :id="user.id" :className="'popup-img'" :base64="fileImage" />
                </div>
                <input type="file" style="display: none" ref="fileRef" accept="image/*" @change="previewThumbnail">
            </div>
            <div v-if="editProfile">
                <div class="profile-form-input">
                    <input type="text" :value="user.userName" />
                    <i class="bi bi-pencil-fill"></i>
                </div>
                <div class="profile-form-input mt-3">
                    <input type="text" :value="user.comment" placeholder="상태메세지를 입력해주세요." style="padding-left : 0px; text-align : left" />
                    <i class="bi bi-pencil-fill"></i>
                </div>
            </div>
            <p v-else>{{user.userName}}</p>
            
        </div>
        <div class="popup-profile-footer">
            <ul v-if="editProfile">
            </ul> 
            <ul v-else>
                <li>
                    <div class="popup-profile-menu" @click="chatBtnOnClick">
                        <i class="bi bi-chat-fill"></i>
                        <a href="javascript:;">채팅하기</a>
                    </div>
                </li>
                <li v-if="!user.friendReq && !isLoginUser">
                    <div class="popup-profile-menu" @click="friendAddBtnOnClick">
                        <i class="bi bi-person-fill-add"></i>
                        <a href="javascript:;">친구추가</a>
                    </div>
                </li>
                <li v-if="isLoginUser">
                    <div class="popup-profile-menu" @click="profileEditBtnOnClick">
                        <i class="bi bi-pencil-fill"></i>
                        <a href="javascript:;">프로필 편집</a>
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
import { useRouter } from 'vue-router';
import {useStore} from 'vuex';

import Thumbnail from '@/components/layouts/Thumbnail.vue';

export default defineComponent({
    name : 'Profile',
    components : {Thumbnail},
    props: {
      profileId : {type: Number}
    },
    setup(props) {
        const {proxy} = getCurrentInstance() as any;
        const router = useRouter();
        const store = useStore();
        const isLoginUser = ref(false);
        const editProfile = ref(false);
        const user = ref<PROFILE>({
            id: 0,
            userId : '',
            userName : '',
            email: '',
            comment : '',
            friendReq : false
        });

        const fileRef = ref<HTMLInputElement>();
        const fileImage = ref('');

        const callApiUserProfile = async () => {
            const res = await callGetApi(`/friend/${props.profileId}`);
            if (res.result === HTTP_STATUS.OK) {
                const {data} = res;

                if (!data) proxy.$alert('해당 사용자가 존재하지 않습니다.', () => {
                    const ele: any = document.getElementsByClassName('popup-close-btn')[0];
                    ele.click();
                });

                isLoginUser.value = store.state.UserStore.id == data.id;

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
            if (res.result === HTTP_STATUS.OK) {
                router.push(`/chat/${res.data}`);
            } else {
                proxy.$alert("채팅방 이동 중 오류가 발생하였습니다.");
                return false;
            }
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

        const profileEditBtnOnClick = () => {
            editProfile.value = true;
        }

        const thumbnailOnClick = () => {
            if (editProfile.value) fileRef.value?.click();
        }

        const previewThumbnail = (e : any) => {
            const input = e.target;

            if (input.files && input.files[0]) {
                const reader = new FileReader();
                reader.onload = (event : any) => {
                    fileImage.value = event.target.result;
                }

                reader.readAsDataURL(input.files[0]);
            } 

        } 

        onMounted(async () => {
            await callApiUserProfile();
        })

        return {user, friendAddBtnOnClick, chatBtnOnClick, isLoginUser, profileEditBtnOnClick, editProfile, thumbnailOnClick, previewThumbnail, fileRef, fileImage}
    }
})
</script>