<template>
    <!-- Vertically centered modal -->
        <div class="modal-dialog modal-dialog-centered">
        ...
        </div>

        <!-- Vertically centered scrollable modal -->
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        ...
    </div>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';
@Component
export default class AlertComponent extends Vue {}
</script>