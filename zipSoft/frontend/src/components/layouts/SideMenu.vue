<template>
    <div class="offcanvas offcanvas-end text-bg-dark" style="left: 0; visibility: visible; transform: translateX(-100%)" ref="sideBar">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel">{{ title }}</h5>
        <button type="button" class="btn-close btn-close-white" @click="closeSidebar()"></button>
      </div>
      <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3" >
            <SideMenuList v-for="(row, idx) in menuArr" :key="idx" :menu="row" />
          <!--<li class="nav-item">
            <a class="nav-link active"  href="#">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Dropdown
            </a>
            <ul class="dropdown-menu dropdown-menu-dark">
              <li><a class="dropdown-item" href="#">Action</a></li>
              <li><a class="dropdown-item" href="#">Another action</a></li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
          </li>-->
        </ul>
        <form class="d-flex mt-3" role="search">
          <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-success" type="submit">Search</button>
        </form>
      </div>
    </div>
</template>
<script lang="ts">
import {defineComponent, ref, onMounted} from 'vue';
import { routes} from '@/router/index';

import SideMenuList from './SideMenuList.vue';

export default defineComponent({
    name : "SideMenu",
    components: {
        SideMenuList
    },
    props: {
        title : {type:String}
    },
    setup(context) {

        const sideBar = ref<HTMLDivElement>();
        const menuArr = ref(routes);

        onMounted(() => {
             
        })

        const openSidebar = () => {
            if (sideBar && sideBar.value) {
                sideBar.value.style.transform = 'none';
            }
        }

        const closeSidebar = () => {
            if (sideBar && sideBar.value) {
                sideBar.value.style.transform = 'translateX(-100%)';
            }
        }

        return {sideBar, menuArr, openSidebar, closeSidebar}

    }
});

</script>