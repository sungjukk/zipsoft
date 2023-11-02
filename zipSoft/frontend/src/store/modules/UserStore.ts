import { Module, ActionContext } from "vuex";
import { globals } from "@/main";
import {Rootstate} from '@/store/index';
import {callPostApi, callGetApi, HTTP_STATUS} from '@/utils/ApiClient';
import router, {RouteUrl} from '@/router/index';
import jwt_decode from "jwt-decode";

export interface USER {
	id : number;
	name : string;
	comment : string;
	thumbnail : string;
	token : string;
	deviceToken : string;
}

export const UserStore : Module<USER, Rootstate> = {
	namespaced: true,
	state: () => ({
		id : 0,
		name : '',
		token : '',
		comment : '',
		thumbnail : '',
		deviceToken: ''
	} as USER),
	getters: {
        getUserName: (state:USER) => state.name,
        getUserId: (state:USER) => state.id,
        isLogin: (state:USER) => {return state.id != 0}
    },
    mutations: {
        currentUser: (state:USER, payload:any) => {
            
           if (!payload) return false;
           /*
               여기서는 payload를 객체로 받습니다.
               payload를 객체로 받으면, mutation를 조금더 유연하게 사용할 수 있기는 합니다.
           */
		   const decode:any = jwt_decode(payload.accessToken);
           state.token = payload.accessToken;
           state.id = decode.sub;
           state.name = decode.name;
           sessionStorage.setItem('authorization', payload.accessToken);
        },
        removeUser: (state:USER) => {
			state.token = '';
			state.id = 0;
			state.name = '';
			sessionStorage.removeItem('authorization');
		}
    },
    actions: {
        login: async ({commit, state}, payload:any) => {
			const params = {
				...payload,
				deviceToken : state.deviceToken
			};
			const res = await callPostApi('/auth/login',params);
			if (res.result == HTTP_STATUS.OK) {
				commit('currentUser', res.data);
				router.push(RouteUrl.ABOUT);
			} else {
				globals.$alert(res.msg);
			}
		},
		loginCheck: async({commit, state}, payload:any) => {
			const accessToken = sessionStorage.getItem('authorization');
			if (!accessToken) return false;
			
			const res = await callGetApi('/auth/ping',payload);
			if (res.result == HTTP_STATUS.OK) {
				
				if (state.id == 0) {
					commit('currentUser', {accessToken});				
				}

				return true;
			} else {
				return false;
				//commit('removeUser');
			}
		},
		updateTokenInfo: async({commit}, payload:any) =>{
			
			const token = sessionStorage.getItem('authorization');
			
			if (!token) return false;
			
			const res = await callGetApi('/auth/updateToken',payload);
			if (res.result == HTTP_STATUS.OK) {
				commit('currentUser', res.data);
				console.log('commit');
			} else {
				//commit('removeUser');
				console.log('asdasda');
			} 
		},
		logout: async({commit}) => {
			await callGetApi('/auth/logout');
			commit('removeUser');
			location.href = RouteUrl.LOGIN;
		}
    }
}
