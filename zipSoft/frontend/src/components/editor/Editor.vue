<template>
    <quill-editor
        v-model:value="state.content"
        :options="state.editorOption"
        :disabled="state.disabled"
        @blur="onEditorBlur($event)"
        @focus="onEditorFocus($event)"
        @ready="onEditorReady($event)"
        @change="onEditorChange($event)"
    />
</template>
<script>
import { reactive, ref, getCurrentInstance } from 'vue';
import {callFileApi} from '@/utils/ApiClient';

export default {
  name: 'App',
  setup() {
    const quill = ref();
    const {proxy} = getCurrentInstance();
    const imageHandler = () => {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept','image/*');
        input.click();

        input.addEventListener('change', async () => {
            console.log(input.files);
            const formData = new FormData();
            if (input.files) {
                for (const f of input.files) {
                    formData.append("files",f);
                }

                const res = await callFileApi('/common/upload/editor/image', formData);
                const IMG_URL =  `${process.env.VUE_APP_API_URL}/common/editor/imageView?fileName=${res.data}`;

                const range = quill.value.getSelection(true);

                quill.value.insertEmbed(range.index, 'image', IMG_URL);
                quill.value.setSelection(range.index + 2);
            }
        });

    }
    const state = reactive({
      content: '',
      _content: '',
      editorOption: {
        placeholder: 'core',
        modules: {
          // toolbars: [
            // custom toolbars options
            // will override the default configuration
          // ],
          // other moudle options here
          // otherMoudle: {}
          toolbar: {
            container: [
                        ['bold', 'italic', 'underline', 'strike'],
                        ['blockquote', 'code-block'],
                        [{ header: 1 }, { header: 2 }],
                        [{ list: 'ordered' }, { list: 'bullet' }],
                        [{ script: 'sub' }, { script: 'super' }],
                        [{ indent: '-1' }, { indent: '+1' }],
                        [{ direction: 'rtl' }],
                        [{ header: [1, 2, 3, 4, 5, 6, false] }],
                        [{ color: [] }, { background: [] }],
                        [{ font: [] }],
                        [{ align: [] }],
                        ['clean'],
                        ['link', 'image', 'video']
                    ],
            handlers: {
                image : imageHandler
            }
          }
        },
        // more options
      },
      disabled: false
    })

    const onEditorBlur = (quill) => {
    }
    const onEditorFocus = (quill) => {
    }
    const onEditorReady = (q) => {
      quill.value = q;
    }
    const onEditorChange = ({ quill, html, text }) => {
      state._content = text;
    }

    const editorValidationCheck = (title) => {
        if (state.content != '' && state.content != '\n') {
            return true;
        } else {
            proxy.$alert(`${title}을 입력해주세요.`, function () {
                quill.value.focus();
            });

            return false;
        }
    }

    const focusEnd = () => {
      const contentEditableElement = document.getElementsByClassName('ql-editor')[0];
      contentEditableElement.focus();
      //Firefox, Chrome, Opera, Safari, IE 9+
      const range = document.createRange(); //Create a range (a range is a like the selection but invisible)
      range.deleteContents();
      range.selectNodeContents(contentEditableElement); //Select the entire contents of the element with the range
      
      const selection = window.getSelection(); //get the selection object (allows you to change selection)
      selection?.removeAllRanges(); //remove any selections already made
      selection?.addRange(range); //make the range you have just created the visible selection
      selection?.collapseToEnd();
      contentEditableElement.focus();
      range.detach();
                
    };

   

    return { state, onEditorBlur, onEditorFocus, onEditorReady, onEditorChange, imageHandler, editorValidationCheck, quill, focusEnd }
  }
}
</script>