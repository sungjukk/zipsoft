import { createStore } from 'vuex';

import UserStore from './modules/UserStore';

const store = new createStore({
	modules: {
		UserStore: UserStore
	}
});

export default store;