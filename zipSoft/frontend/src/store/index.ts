import { createStore } from 'vuex';
import { Store } from 'vuex'
import {UserStore, USER} from './modules/UserStore';
import { MenuStore, Menu } from './modules/MenuStore';

export interface Rootstate {
	UserStore: USER;
  MenuStore: Menu;
}

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $store: Store<Rootstate>
  }
}

export default createStore({
	modules: { UserStore, MenuStore }
});
