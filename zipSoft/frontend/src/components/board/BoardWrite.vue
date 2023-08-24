<template>
    <div id="write-form">
        <table class="table board-write">
            <tbody>
                <tr>
                    <th>제목</th>
                    <td><input type="email" class="form-control" id="inputEmail4" title="제목" data-require  v-model="write.subject"></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td><input type="file" class="form-control"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea class="form-control" v-model="write.content" data-require title="내용" /></td>
                </tr>
            </tbody>
        </table>
        <div class="board-write-btn-group">
            <button class="btn btn-secondary">뒤로가기</button>
            <button class="btn btn-primary" style="margin-left : 10px" @click="submit">등록</button>
        </div>
        <section class="custom-alert modal-section type-alert">
            <div class="enroll_box">
                <p class="menu_msg">asdsafsadfasfasagf</p>
            </div>
            <div class="enroll_btn">
                <button class="btn btn-primary">확인</button>
            </div>
        </section>
    </div>
    <div class="dimLayer"></div>
</template>
<script lang="ts">
import { defineComponent, ref } from 'vue'
import { BoardWriteDef } from '@/views/board/BoardWriteSection.vue';
import {validationCheck} from '@/utils/CommonUtil';
import {callPostApi} from '@/utils/ApiClient';

export default defineComponent({
    name: 'BoardWrite',
    setup() {
        const write = ref<BoardWriteDef>({
            subject : '',
            content : ''
        });

        const submit = async () => {

            if (!validationCheck('write-form')) return false;

            const res = await callPostApi('/board',write.value);

            console.log(res);
        }

        return {write, submit};
    },
})
</script>
<style>
.board-write th {
    text-align: center;
    vertical-align: middle;
}

.board-write textarea {
    height: 400px;
}

.board-write-btn-group {
    text-align: right;
}

.custom-alert {
        width: 320px;
    height: 260px;
    border-radius: 25px;
    padding: 32px;
    box-sizing: border-box;
    text-align: center;
}

.modal-section{background: #ffffff;box-sizing: border-box;position: absolute;top: 50%;left: 50%;-webkit-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-moz-transform: translate(-50%, -50%);-o-transform: translate(-50%, -50%);transform: translate(-50%, -50%);z-index: 9999;}
.enroll_box {
    margin: 45px 0 42px 0;
    display: inline-block;
    text-align: center;
}
.modal .btn {
    cursor: pointer;
    border: 1px solid #999999;
    text-align: center;
    border-radius: 5px;
    outline: none;
    font-weight: 500;
}
.enroll_btn {
    margin-top: 10px;
}
.enroll_btn > button {
    min-width: 120px;
    height: 56px;
}
.pink_btn {
    width: 90px;
    background: #ed197a;
    color: #fff;
    height: 36px;
    line-height: 36px;
    transition: 0.5s;
    font-size: 17px;
    border: none;
}
.dimLayer {
    display: block;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    position: fixed;
    left: 0;
    top: 0px;
    margin: 0;
    padding: 0;
    z-index: 9998;
}
</style>
