<template>
    <nav v-if="draw > 0" aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <li class="page-item" :class="pageParam.number == 0 ? 'disabled' : ''">
            <a class="page-link" href="javascript:;" aria-label="Previous" @click="movePage(1)">
                <span aria-hidden="true">&laquo;</span>
            </a>
            </li>
            <li class="page-item" :class="pageParam.number + 1 == start + i ? 'active' : ''" v-for="i in draw" :key="i">
                <a class="page-link" href="javascript:;" @click="movePage(start + i)">{{ start + i }}</a>
            </li>
            <li class="page-item" :class="pageParam.totalPages == pageParam.number + 1 ? 'disabled' : ''">
            <a class="page-link" href="javascript:;" aria-label="Next" @click="movePage(pageParam.totalPages)">
                <span aria-hidden="true">&raquo;</span>
            </a>
            </li>
        </ul>
    </nav>
</template>
<script lang="ts" setup>
import { PropType, defineEmits, toRefs, computed, ref } from 'vue';
import { PageParam } from '../board/BoardList.vue';

const props = defineProps<{pageParam : PageParam}>();
const {pageParam} = toRefs(props);
const emit = defineEmits([
  'callback'
]);

const draw = computed(() => {
    if (!pageParam.value) {
        return 0;
    }    
    return pageParam.value.totalPages >= 10 ? 10 : pageParam.value.totalPages;
});

const start = computed(() => {
    if (!pageParam.value) return 0;
    console.log(pageParam.value);

    let value = 0;

    if (pageParam.value.totalPages <= 10 || pageParam.value.number < 6) {
        value = 0;
    } else if (pageParam.value.number + 10 >= pageParam.value.totalPages) {
        value = pageParam.value.totalPages - 10
    } else if (pageParam.value.number >= 6) {
        value = pageParam.value.number - 5;
    }

    return value;

});

const movePage = (idx : number) => emit('callback', idx);

</script>