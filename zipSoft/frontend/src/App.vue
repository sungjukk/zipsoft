<template>
  <IndexLayout>
		<router-view />
  </IndexLayout>
</template>

<script lang="ts">
import IndexLayout from '@/components/layouts/IndexLayout.vue';
import {defineComponent} from 'vue';

export default defineComponent({
  name: 'App',
  components: {
    IndexLayout
  }
})

</script>


<style>
</style>
