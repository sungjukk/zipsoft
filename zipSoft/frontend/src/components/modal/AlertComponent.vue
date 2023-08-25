<template>
	<div v-if="isShow">
	<section class="custom-alert modal-section type-alert">
		<div class="enroll_box">
			<p class="menu_msg">{{msg}}</p>
		</div>
		<div class="enroll_btn">
			<button class="btn btn-primary" @click="closeAlert">확인</button>
		</div>
	</section>
	<div class="dimLayer"></div>
	</div>
</template>
<script lang="ts">
interface AlertData {
	msg : String;
	ele? : HTMLElement;
	isShow : boolean;
	callback?: Function;
}

import { defineComponent } from "vue";

export default defineComponent({
	name:'AlertComponent',
	data() {
		return {
			msg : '',
			ele : undefined,
			isShow : false,
			callback : undefined
		} as AlertData
	},
	methods: {
		closeAlert() {
			this.isShow = false;

			if (this.callback) {
				this.callback();
			}

			if (this.ele) {
				this.ele?.focus();
			}
		}
	}
})
</script>
<style>
.custom-alert {
        width: 320px;
    height: 260px;
    border-radius: 25px;
    padding: 32px;
    box-sizing: border-box;
    text-align: center;
	animation: fadein 1s;
  -moz-animation: fadein 1s; /* Firefox */
  -webkit-animation: fadein 1s; /* Safari and Chrome */
  -o-animation: fadein 1s; /* Opera */
}

.modal-section{background: #ffffff;box-sizing: border-box;position: absolute;top: 50%;left: 50%;-webkit-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-moz-transform: translate(-50%, -50%);-o-transform: translate(-50%, -50%);transform: translate(-50%, -50%);z-index: 9999;}
.enroll_box {
    margin: 45px 0 42px 0;
    display: inline-block;
    text-align: center;
}
.modal .btn {
    cursor: pointer;
    border: 1px solid #999999;
    text-align: center;
    border-radius: 5px;
    outline: none;
    font-weight: 500;
}
.enroll_btn {
    margin-top: 10px;
}
.enroll_btn > button {
    min-width: 120px;
    height: 56px;
    border-radius: 10px;
}
.dimLayer {
    display: block;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    position: fixed;
    left: 0;
    top: 0px;
    margin: 0;
    padding: 0;
    z-index: 9998;
}
@keyframes fadein {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}
@-moz-keyframes fadein { /* Firefox */
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}
@-webkit-keyframes fadein { /* Safari and Chrome */
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}
@-o-keyframes fadein { /* Opera */
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}
</style>