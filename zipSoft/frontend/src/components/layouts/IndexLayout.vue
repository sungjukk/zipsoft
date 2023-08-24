<template>
  <section>
	  <component :is="layout">
        <slot />
	  </component>
  </section>
</template>

<script lang="ts">
import DefaultLayout from './DefaultLayout.vue';
import NoLayout from './NoLayout.vue';
import {defineComponent, computed} from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'IndexLayout',
  components: {
    DefaultLayout, NoLayout
  },
  setup(props) {
    const {currentRoute} = useRouter();
    console.log(currentRoute.value.meta);
    const layout = computed(() => currentRoute.value.meta.layout || 'DefaultLayout');

    return { layout }
  }
})

</script>