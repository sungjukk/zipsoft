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
                    <td><input type="file" class="form-control" ref="file" multiple /></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><Editor ref="editor" style="min-height : 250px"/></td>
                </tr>
            </tbody>
        </table>
        <div class="board-write-btn-group">
            <button class="btn btn-secondary" @click="moveBoardList">목록</button>
            <button class="btn btn-primary" style="margin-left : 10px" @click="submit">등록</button>
        </div>
    </div>
</template>
<script lang="ts">
import { defineComponent, getCurrentInstance, ref} from 'vue'
import { BoardWriteDef } from '@/views/board/BoardWriteSection.vue';
import {validationCheck} from '@/utils/CommonUtil';
import {callFileApi, HTTP_STATUS} from '@/utils/ApiClient';
import { useRouter } from 'vue-router';
import {RouteUrl} from '@/router/index';
import Editor from '../editor/Editor.vue';

export default defineComponent({
    name: 'BoardWrite',
    components: {
        Editor
    },
    setup(props,context) {
        const {proxy} = getCurrentInstance() as any;
        const route = useRouter();
        const editor = ref();
        const write = ref<BoardWriteDef>({
            subject : '',
            content : ''
        });

        const file = ref<HTMLInputElement>();

        const submit = async () => {
            //proxy.$alert('test');
            console.log(editor.value);
            if (!validationCheck('write-form')) return false;
            if (!editor.value.editorValidationCheck('내용')) return false;
            const formData = new FormData();
            const sub = write.value.subject;
            formData.append('subject', sub.toString());
            formData.append('content', editor.value.state.content);
            if (file.value?.files) {
                for (const f of file.value.files) {
                    formData.append("files",f);
                }
            }
            

            
            const res = await callFileApi('/board',formData);

            if (res.result == HTTP_STATUS.OK) {
                proxy.$alert("등록하였습니다.", ()=> {
                    route.push(RouteUrl.BOARD);
                });
            } else {
                proxy.$alert("등록에 실패하였습니다.");
            }

            //console.log(res);

        }

        const moveBoardList = () => {
            route.push(RouteUrl.BOARD);
        }

        return {write, submit, moveBoardList, file, editor};
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

</style>
