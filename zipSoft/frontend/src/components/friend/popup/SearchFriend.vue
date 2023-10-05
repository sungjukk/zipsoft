<template>
    <div class="search-friend-container">
        <div class="popup-title">
            <span>친구 검색</span>
        </div>
        <div class="d-flex justify-content-center">
            <div class="search">
                <div class="search-flex">
                    <input class="search_input" type="text" name="" placeholder="Search here...">
                    <a href="#" class="search_icon"><i class="bi bi-search"></i></a>
                </div>
            </div>
        </div>
        <div class="search-friend-list">
            <div class="search-friend-total">
                <span>총 0건</span>
            </div>
            <ul class="friend-list">
                <li @click="friendOnClick">
                    <div class="my-profile-row">
                    <div class="friend-row">
                        <div class="friend-img">
                            <img src="@/assets/img/empty-user.png" />
                        </div>
                        <div class="friend-txt">
                            <span>테스트</span>
                            <p>집에 가고 싶다...</p>
                        </div>
                    </div>
                    <div>
                        <button class="btn btn-secondary">추가</button>
                    </div>
                    </div>
                </li>
            </ul>
            <!--<p class="empty-search">검색 결과가 없습니다.</p>-->
        </div>
    </div>
</template>
<script lang="ts" setup>
import { defineEmits } from 'vue'
const emit = defineEmits([
  'friendBtnOnclick'
]);

const friendOnClick = () => {
    emit('friendBtnOnclick');
}
</script>