<template>
  <div>
	  <PageHeader />
	  <div class="container">
		  <router-view/>
	  </div>
	  <PageFooter />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import PageHeader from '@/components/layouts/PageHeader.vue';
import PageFooter from '@/components/layouts/PageFooter.vue';

@Options({
  components: {
    PageHeader,
    PageFooter
  },
})
export default class App extends Vue {}
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
