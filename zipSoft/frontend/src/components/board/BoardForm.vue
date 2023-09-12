<template>
    <div id="write-form">
        <table class="table board-write">
            <colgroup>
                <col width="100px"  />
                <col  />
            </colgroup>
            <tbody>
                <tr>
                    <th>제목</th>
                    <td><input type="email" class="form-control" id="inputEmail4" title="제목" data-require  v-model="write.subject"></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td>
                        <div class="board-form-files" v-if="write.fileList.length > 0">
                            <ul>
                                <li v-for="(f) in write.fileList" :key="f.id"><span @click="downloadFile(f.id)">{{f.orgFileName}}</span><img src="@/assets/svg/x-square.svg" style="margin-left:5px; cursor:pointer" @click="deleteFile(f.id)" /></li>
                            </ul>
                        </div>
                        <input type="file" class="form-control" ref="file" multiple />
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><Editor ref="editor" style="min-height : 250px"/></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script lang="ts">
import { defineComponent, getCurrentInstance, ref, watch} from 'vue'
import { BoardWriteDef } from '@/views/board/BoardWriteSection.vue';
import {validationCheck} from '@/utils/CommonUtil';
import {callFileDownApi, callDeleteApi, HTTP_STATUS} from '@/utils/ApiClient';
import { useRouter } from 'vue-router';
import Editor from '../editor/Editor.vue';

export default defineComponent({
    name: 'BoardWrite',
    components: {
        Editor
    },
    setup() {
        const {proxy} = getCurrentInstance() as any;
        const route = useRouter();
        const editor = ref();
        const write = ref<any>({
            subject : '',
            content : '',
            fileList : []
        });

        const file = ref();

        const validation = async () => {
            if (!validationCheck('write-form')) return false;
            if (!editor.value.editorValidationCheck('내용')) return false;
        }

        const getFormData = () => {
            const formData = new FormData();
            const sub = write.value.subject;
            formData.append('subject', sub.toString());
            formData.append('content', editor.value.state.content);
            if (file.value?.files) {
                for (const f of file.value.files) {
                    formData.append("files",f);
                }
            }

            return formData;
        }

        const downloadFile = async (id : Number) => {
            await callFileDownApi(`/board/file/${id}`);
        };

        const deleteFile = async (id : Number) => {

            proxy.$confirm('삭제된 파일은 복구되지 않습니다.<br/>정말 삭제하시겠습니까?', async () => {
                const res = await callDeleteApi(`/board/file/${id}`);

                if (res.result === HTTP_STATUS.OK) {
                    proxy.$alert('삭제를 완료하였습니다.',() => {
                        write.value.fileList = write.value.fileList.filter((f : any) => f.id !== id);
                    });
                } else {
                    proxy.$alert('파일 삭제에 실패하였습니다.');
                }

            });
        }

        watch(() => write.value.content, () => {
            editor.value.state.content = write.value.content;
            
            setTimeout(() => {
                editor.value.focusEnd();
            },100);

        },{deep: true});

        return {write, validation, editor, downloadFile, deleteFile, file, getFormData};
    },
})
</script>
