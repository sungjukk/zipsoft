import {callPostApi, callGetApi} from '@/utils/ApiClient';
import router, {RouteUrl} from '@/router/index.js';
import jwt_decode from "jwt-decode";

const UserStore = {
	namespaced: true,
	state: () => ({
		id : 0,
		name : '',
		token : ''
	}),
	getters: {
        getUserName: state => state.name,
        getUserId: state => state.id,
        isLogin: state => {return state.id != 0}
    },
    mutations: {
        currentUser: (state, payload) => {
            
           if (!payload) return false;
            console.log(payload.accessToken);
           /*
               여기서는 payload를 객체로 받습니다.
               payload를 객체로 받으면, mutation를 조금더 유연하게 사용할 수 있기는 합니다.
           */
		   const decode = jwt_decode(payload.accessToken);
           state.token = payload.accessToken;
           state.id = decode.sub;
           state.name = decode.name;
           sessionStorage.setItem('authorization', payload.accessToken);
        },
        removeUser: (state) => {
			state.token = '';
			state.id = 0;
			state.name = '';
			sessionStorage.removeItem('authorization');
		}
    },
    actions: {
        login: async ({commit}, payload) => {
			const res = await callPostApi('/auth/login',payload);
			if (res.result == 200) {
				commit('currentUser', res.data);
				router.push(RouteUrl.ABOUT);
			} else {
				alert(res.msg);
			}
		},
		loginCheck: async({commit, state}, payload) => {
			const accessToken = sessionStorage.getItem('authorization');
			if (!accessToken) return false;
			
			const res = await callGetApi('/auth/ping',payload);
			if (res && res.result == 200) {
				
				if (state.id == 0) {
					commit('currentUser', {accessToken});				
				}
				
			} else {
				//commit('removeUser');
			}
		},
		republishToken: async({commit}, payload) =>{
			
			const token = sessionStorage.getItem('authorization');
			
			if (!token) return false;
			
			const res = await callGetApi('/auth/republishToken',payload);
			console.log(res);
			if (res && res.result == 200) {
				commit('currentUser', res.data);
				console.log('commit');
			} else {
				//commit('removeUser');
			} 
		},
		logout: async({commit}) => {
			await callGetApi('/auth/logout');
			commit('removeUser');
			router.push(RouteUrl.ABOUT);
		}
    }
}

export default UserStore;