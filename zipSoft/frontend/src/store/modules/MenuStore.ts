import { Module, ActionContext } from "vuex";
import {Rootstate} from '@/store/index';

export interface Menu {
	isShow : boolean,
    title : string
}

export const MenuStore : Module<Menu, Rootstate> = {
    namespaced: true,
    state: () => ({
        isShow : true,
        title : ''
	} as Menu),
    mutations : {
        updateShow : (state:Menu, payload:boolean) => {
            state.isShow = payload;
        },
        updateTitle : (state:Menu, payload:string) => {
            state.title = payload;
        },
        updatePayload: (state:Menu, payload:Menu) => {
            state.isShow = payload.isShow;
            state.title = payload.title;
        },
        init: (state:Menu) => {
            state.isShow = true;
            state.title = '';
        }
    },
    actions: {

    }
}