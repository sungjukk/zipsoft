<template>
    <div class="search-friend-container">
        <div class="popup-title">
            <span>친구 검색</span>
        </div>
        <div class="d-flex justify-content-center">
            <div class="search">
                <div class="search-flex">
                    <input class="search_input" type="text" name="" v-model="userName" placeholder="사용자 아이디" @keyup.enter="serachBtnOnClick">
                    <a href="#" class="search_icon" @click="serachBtnOnClick"><i class="bi bi-search"></i></a>
                </div>
            </div>
        </div>
        <div class="search-friend-list">
            <div class="search-friend-total">
                <span>총 {{userList.length}}건</span>
            </div>
            <ul class="friend-list" v-if="userList.length > 0">
                <FriendListRow v-for="friend in userList" :key="friend.id" :friend="friend" @friendOnClick="friendOnClick" />
            </ul>
            <p v-else class="empty-search">검색 결과가 없습니다.</p>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { defineEmits, ref } from 'vue';
import {callGetApi, HTTP_STATUS} from '@/utils/ApiClient';
import FriendListRow from '@/components/friend/FriendListRow.vue';
import {FRIEND} from '@/views/friend/FriendListSection.vue';
const emit = defineEmits([
  'friendBtnOnclick'
]);

const userName = ref('');
const userList = ref<Array<FRIEND>>([]);

const serachBtnOnClick = async () => {
    console.log(userName.value);

    const res = await callGetApi(`/friend/search/${userName.value}`);
    const list = [];
    if (res.result === HTTP_STATUS.OK) {
        const {data} = res;
        for (var i in data) {
            const user = data[i];
            const friend = {
                id : user.id,
                userId : 0,
                userName : '',
                friendId : user.id,
                friendName : user.userName,
                comment : '',
                acceptYn : ''
            } as FRIEND;

            list.push(friend);
        }
    }

    userList.value = list;

}

const friendOnClick = (userId: number) => {
    console.log(userId);
    emit('friendBtnOnclick', userId);
}
</script>