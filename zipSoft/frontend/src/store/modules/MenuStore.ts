import { Module, ActionContext } from "vuex";
import {Rootstate} from '@/store/index';

export interface Menu {
	isShow : boolean
}

export const MenuStore : Module<Menu, Rootstate> = {
    namespaced: true,
    state: () => ({
        isShow : false
	} as Menu),
    mutations : {
        updateState : (state:Menu, payload:boolean) => {
            state.isShow = payload;
        }
    },
    actions: {

    }
}