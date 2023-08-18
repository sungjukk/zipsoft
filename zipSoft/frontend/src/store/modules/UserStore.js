import {callPostApi} from '@/utils/ApiClient';

const UserStore = {
	namespaced: true,
	state: () => ({
		id : 0,
		userId : '',
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
            /*
                여기서는 payload를 객체로 받습니다.
                payload를 객체로 받으면, mutation를 조금더 유연하게 사용할 수 있기는 합니다.
            */
            state.token = payload.accessToken;
        }
    },
    actions: {
        login: async ({commit}, payload) => {
			const result = await callPostApi('/login',payload);
			if (result.header.resultCode == "OK") {
				commit('currentUser', result.body.data);
			} else {
				alert(result.header.codeName);
			}
		}
    }
}

export default UserStore;