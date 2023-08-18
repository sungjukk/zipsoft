<template>
	<div id="app">
		<PageHeader></PageHeader>
		<router-view/>
		<PageFooter></PageFooter>
	</div>
</template>

<script>
import PageHeader from '@/components/layouts/PageHeader'
import PageFooter from '@/components/layouts/PageFooter'

export default {
  name: 'App',
  components: {
    PageHeader,
    PageFooter
  }
}
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
