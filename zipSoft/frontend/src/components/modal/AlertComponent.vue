<template>
	<div v-if="isShow">
		<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="closeAlert()"></button>
	      </div>
	      <div class="modal-body">
	        ...
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="closeAlert()">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
	<button ref="openBtn" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" style="display : none">
	Launch demo modal
	</button>
	</div>
</template>
<script lang="ts">
import { Component, Ref, Vue, Watch } from 'vue-facing-decorator';
@Component
export default class AlertComponent extends Vue {
	@Ref() openBtn: HTMLButtonElement;
	msg:string = '';
	isShow:boolean = false;
	
	created() {
		console.log('asdas');
	}
	
	mounted() {
		console.log(this);
	}
	
	@Watch('msg')
	onMsgChange(val: string, oldVal: string) {
		console.log(val);
	}

	@Watch("isShow")
	onIsShowChange(val:boolean, oldVal: boolean) {
		console.log(val);
		if (val) {
			setTimeout(() => {
				this.openBtn.click();
			},100);
		}
	}

	closeAlert() {
		console.log('asdad');
		this.isShow = false;
	}
	
}
</script>