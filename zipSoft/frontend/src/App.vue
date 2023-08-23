<template>
  <div>
	  <PageHeader />
	  <div class="container">
		  <router-view />
	  </div>
	  <PageFooter />
  </div>
</template>

<script lang="ts">
import PageHeader from '@/components/layouts/PageHeader.vue';
import PageFooter from '@/components/layouts/PageFooter.vue';
import {defineComponent, ref, onMounted} from 'vue';

export default defineComponent({
  name: 'App',
  components: {
    PageHeader, PageFooter
  }
})

</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
