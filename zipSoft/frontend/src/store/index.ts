import { createStore } from 'vuex';

import {UserStore, User} from './modules/UserStore';

export interface Rootstate {
	UserStore: User;
}

export default createStore({
	modules: { UserStore }
});
