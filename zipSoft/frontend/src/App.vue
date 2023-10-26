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
  },
  created() {
    this.$push.init();
    this.$push.getToken((token : string) => {
      this.$store.state.UserStore.deviceToken = token;
    });
  }
})

</script>


<style>
</style>
