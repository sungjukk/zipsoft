<template>
  <footer>
    <div class="menuBar">
      <MenuList :viewType="'mobile'" />
    </div>
  </footer>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import MenuList from './MenuList.vue';
export default defineComponent({
  name : 'pageFooter',
  components: {MenuList}
})
</script>

<style scoped>

</style>
