<template>
    <div v-if="isShow">
        <div class="percent">
            <svg>
            <circle cx="105" cy="105" r="50"></circle>
            <circle cx="105" cy="105" r="50" :style="{'--percent' : percent}"></circle>
            </svg>
            <div class="number">
            <h5>{{percent}}<span>%</span></h5>
            </div>
        </div>
        <div class="dimLayer"></div>
    </div>
</template>
<script lang="ts">
interface LoadingData {
	percent : number;
	isShow : boolean;
}

import { defineComponent } from "vue";

export default defineComponent({
    data () {
        return {
            percent : 0,
            isShow : false
        } as LoadingData
    },
    methods : {

    }
})

</script>