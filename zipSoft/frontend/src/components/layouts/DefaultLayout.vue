<template>
  <div>
	  <PageHeader />
	  <div class="container">
		  <slot />
	  </div>
	  <PageFooter />
  </div>
</template>

<script lang="ts">
import PageHeader from './PageHeader.vue';
import PageFooter from './PageFooter.vue';
import {defineComponent, ref, onMounted} from 'vue';

export default defineComponent({
  name: 'DefaultLayout',
  components: {
    PageHeader, PageFooter
  }
})

</script>