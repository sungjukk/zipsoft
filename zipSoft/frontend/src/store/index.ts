import { createStore } from 'vuex';
import { Store } from 'vuex'
import {UserStore, User} from './modules/UserStore';

export interface Rootstate {
	UserStore: User;
}

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $store: Store<Rootstate>
  }
}

export default createStore({
	modules: { UserStore }
});
